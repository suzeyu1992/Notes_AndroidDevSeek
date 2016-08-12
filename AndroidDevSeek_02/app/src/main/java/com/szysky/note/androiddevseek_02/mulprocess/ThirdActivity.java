package com.szysky.note.androiddevseek_02.mulprocess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by suzeyu on 16/8/2.
 */

public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = ThirdActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticTest.sTest ++;
        //打印一个静态属性值
        Log.d(TAG, " 修改后的静态值为:"+StaticTest.sTest);
    }
}
