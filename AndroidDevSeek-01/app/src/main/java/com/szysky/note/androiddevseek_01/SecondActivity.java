package com.szysky.note.androiddevseek_01;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by suzeyu on 16/8/1.
 */

public class SecondActivity extends AppCompatActivity {
    public static final  String TAG = SecondActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate: SecondActivity");


    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: SecondActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: SecondActivity");
    }
}
