package com.szysky.note.androiddevseek_15;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 方式一 通过设置visibility
                ((ViewStub)findViewById(R.id.stub_import)).setVisibility(View.VISIBLE);
                // 方式二 通过inflate加载显示
                //View inflate =  ((ViewStub) findViewById(R.id.stub_import)).inflate();

                // 通过inflatedId这个id可以得到加载进来的布局的根布局
                LinearLayout commLv = (LinearLayout) findViewById(R.id.stin_root);
            }
        },5000);

        // 以下代码是为了模拟一个ANR的场景来分析日志
        new Thread(new Runnable() {
            @Override
            public void run() {
                testANR();
            }
        });//.start();
        SystemClock.sleep(10);
        initView();
    }


    /**
     *  以下两个方法用来模拟出一个稍微不好发现的ANR
     */
    private synchronized void testANR(){
        SystemClock.sleep(3000 * 1000);
    }

    private synchronized void initView(){}
}
