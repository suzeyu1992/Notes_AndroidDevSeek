package com.szysky.note.androiddevseek_14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("susu", "调用的值"+get() );
    }

    static{
        System.loadLibrary("jni-test");
    }

    public native String get();
    public native void set(String str);
}
