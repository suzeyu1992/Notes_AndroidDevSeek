package com.szysky.note.androiddevseek_03;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.szysky.note.androiddevseek_03.cusview.MyHorizontalScrollView;
import com.szysky.note.androiddevseek_03.inside.InSideInterceptActivity;
import com.szysky.note.androiddevseek_03.outside.OutSideInterceptActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_outside).setOnClickListener(this);
        findViewById(R.id.btn_inside).setOnClickListener(this);
        final View tv_main = findViewById(R.id.btn_inside);


        int widthSpec = View.MeasureSpec.makeMeasureSpec(((1 << 30)-1), View.MeasureSpec.AT_MOST);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(((1 << 30)-1), View.MeasureSpec.AT_MOST);
        tv_main.measure(widthSpec, heightSpec);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_outside:

                startActivity(new Intent(getApplicationContext(), OutSideInterceptActivity.class));
            break;

            case R.id.btn_inside:

                startActivity(new Intent(getApplicationContext(), InSideInterceptActivity.class));
                break;
        }
    }
}
