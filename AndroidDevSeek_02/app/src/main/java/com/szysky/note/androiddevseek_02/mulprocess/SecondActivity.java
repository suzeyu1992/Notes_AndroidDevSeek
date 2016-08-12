package com.szysky.note.androiddevseek_02.mulprocess;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szysky.note.androiddevseek_02.R;

/**
 * Created by suzeyu on 16/8/2.
 */

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = SecondActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //跳转新的activity
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ThirdActivity.class));
            }
        });

        //打印一个静态属性值
        Log.d(TAG, " 修改后的静态值为:"+StaticTest.sTest);
        StaticTest.sTest ++;    //在ThirdActivity中打印查看结果

    }
}
