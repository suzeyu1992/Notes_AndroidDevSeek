package com.szysky.note.androiddevseek_11;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_serial).setOnClickListener(this);
        findViewById(R.id.btn_concurrent).setOnClickListener(this);
        findViewById(R.id.btn_send_intentserivice).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_intentserivice:
                //测试IntentService
                testIntentService();
                break;

            case R.id.btn_serial:
                // 串行执行
                checkSerial();
                break;

            case R.id.btn_concurrent:
                //以并行的方式打开
                checkConcurrent();
                break;
        }
    }

    /**
     * 检测开启多个IntentService的Log出现效果
     */
    private void testIntentService() {
        Intent service = new Intent(this, LocalIntentService.class);
        service.putExtra("task", "hi, 我是数据1");
        startService(service);

        service.putExtra("task", "hi, 我是数据2");
        startService(service);

        service.putExtra("task", "hi, 我是数据3");
        startService(service);
    }

    /**
     * 检测默认是否是串行
     */
    private void checkSerial() {
        new MyAsync("任务_1").execute("");
        new MyAsync("任务_2").execute("");
        new MyAsync("任务_3").execute("");
        new MyAsync("任务_4").execute("");
        new MyAsync("任务_5").execute("");
    }

    /**
     * 在版本3.0以上使用并行的方式开启
     */
    private void checkConcurrent() {
        new MyAsync("任务_1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsync("任务_2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsync("任务_3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsync("任务_4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
        new MyAsync("任务_5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"");
    }


    public static class LocalIntentService extends IntentService {

        private static final String TAG = LocalIntentService.class.getSimpleName();

        public LocalIntentService() {
            super(TAG);
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String task = intent.getStringExtra("task");
            Log.d(TAG, "receiver task :"+task);
            SystemClock.sleep(2000);
        }

        @Override
        public void onDestroy() {
            Log.w(TAG, "onDestroy: 准备关闭" );
            super.onDestroy();
        }
    }


    /**
     * 创建一个AsyncTask任务类
     */
    private static class MyAsync extends AsyncTask<String, Integer, String>{
        private static final String TAG = MyAsync.class.getSimpleName();
        private final String mTaskName;

        public MyAsync(String taskName){
            mTaskName = taskName;
        }

        @Override
        protected String doInBackground(String... params) {
            SystemClock.sleep(3000);
            return mTaskName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Log.e(TAG, s+" onPostExecute finish time: " +df.format(new Date()));
        }
    }
}
