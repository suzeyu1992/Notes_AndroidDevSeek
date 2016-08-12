package com.szysky.note.androiddevseek_01;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.szysky.note.androiddevseek_01.launchmode.ModeSingleInstanceAct;
import com.szysky.note.androiddevseek_01.launchmode.ModeSingleTaskAct;
import com.szysky.note.androiddevseek_01.launchmode.ModeSingleTopAct;
import com.szysky.note.androiddevseek_01.launchmode.ModeStandardAct;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final  String TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");
        Log.d(TAG, "Create:"+(savedInstanceState == null));
        //打开新界面
        findViewById(R.id.tv_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_standard).setOnClickListener(this);
        findViewById(R.id.btn_singletop).setOnClickListener(this);
        findViewById(R.id.btn_singletask).setOnClickListener(this);
        findViewById(R.id.btn_singleinstance).setOnClickListener(this);
        findViewById(R.id.btn_filter).setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: "+newConfig.orientation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState: ");
        Log.d(TAG, "onRestoreInstanceState:"+(savedInstanceState == null));


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_standard:
                Intent intent1 = new Intent(getApplicationContext(), ModeStandardAct.class);
                startActivity(intent1);
                break;
            case R.id.btn_singletop:
                Intent intent2 = new Intent(getApplicationContext(), ModeSingleTopAct.class);
                startActivity(intent2);
                break;
            case R.id.btn_singletask:
                Intent intent3 = new Intent(getApplicationContext(), ModeSingleTaskAct.class);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent3);
                break;
            case R.id.btn_singleinstance:
                Intent intent4 = new Intent(getApplicationContext(), ModeSingleInstanceAct.class);
                startActivity(intent4);
                break;

            //隐式打开
            case R.id.btn_filter:

                Intent intent = new Intent();
                intent.setAction("com.test1");
                intent.addCategory("com.category1");
//              intent.setDataAndType(Uri.parse("content://"),"audio/plain");//如果过滤规则中没有声明URI的属性,那么会有默认值content和file的属性
                intent.setDataAndType(Uri.parse("http://"),"audio/plain");

                ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                startActivity(intent);

                break;
        }
    }
}
