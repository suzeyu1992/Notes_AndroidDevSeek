package com.szysky.note.androiddevseek_14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("susu", "调用的值"+get() );

        findViewById(R.id.btn_jni2java).setOnClickListener(this);
    }

    static{
        System.loadLibrary("jni-test");
    }

    public native String get();
    public native void set(String str);


    /**
     * 定义一个静态方法 , 提供给JNI调用
     */
    public static void methodCalledByJni(String fromJni){
        Log.e("susu", "我是从JNI被调用的消息,  JNI返回的值是:"+fromJni );
    }

    // 定义调用本地方法, 好让本地方法回调java中的方法
    public native void callJNIConvertJavaMethod();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_jni2java:
                // 调用JNI的方法
                callJNIConvertJavaMethod();
                break;
        }
    }
}
