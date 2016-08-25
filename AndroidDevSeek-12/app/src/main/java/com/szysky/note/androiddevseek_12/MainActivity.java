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

import com.szysky.note.androiddevseek_12.load.ImageLoader;
import com.szysky.note.androiddevseek_12.util.MyBitmapLoadUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ImageView iv_main;
    private byte[] bytes;
    private ImageLoader mImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load_normal).setOnClickListener(this);
        findViewById(R.id.btn_load_efficient).setOnClickListener(this);
        iv_main = (ImageView) findViewById(R.id.iv_main);






        mImageLoader = ImageLoader.getInstance(getApplicationContext());

    }

    /**
     *  接收一个url地址, 对其转换成md5值并返回
     *   转成一个32md5值
     */
    public String keyFormUrl(String url){
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_normal:
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_big_pic);
//                iv_main.setImageBitmap(bitmap);
                final String url = "http://img9.dzdwl.com/img/11543935W-1.jpg";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = mImageLoader.loadBitmap(url, 300, 300);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                iv_main.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();


                break;
            case R.id.btn_load_efficient:
                // 检测自定义加载图片工具类是否好使
                Bitmap bitmap2 = MyBitmapLoadUtil.decodeFixedSizeForResource(getResources(), R.mipmap.ic_big_pic, 600, 300);
                iv_main.setImageBitmap(bitmap2);
                break;
        }
    }


}
