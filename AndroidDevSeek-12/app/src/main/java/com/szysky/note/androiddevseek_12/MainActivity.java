package com.szysky.note.androiddevseek_12;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.szysky.note.androiddevseek_12.util.MyBitmapLoadUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageView iv_main;
    private byte[] bytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load_normal).setOnClickListener(this);
        findViewById(R.id.btn_load_efficient).setOnClickListener(this);
        iv_main = (ImageView) findViewById(R.id.iv_main);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_normal:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big_pic);
                iv_main.setImageBitmap(bitmap);

                break;
            case R.id.btn_load_efficient:
                // 检测自定义加载图片工具类是否好使
                Bitmap bitmap2 = MyBitmapLoadUtil.decodeFixedSizeForResource(getResources(), R.mipmap.ic_big_pic, 600, 300);
                iv_main.setImageBitmap(bitmap2);
                break;
        }
    }


}
