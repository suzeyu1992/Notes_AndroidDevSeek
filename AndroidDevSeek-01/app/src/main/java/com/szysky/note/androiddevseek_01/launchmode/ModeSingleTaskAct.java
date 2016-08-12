package com.szysky.note.androiddevseek_01.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szysky.note.androiddevseek_01.MainActivity;
import com.szysky.note.androiddevseek_01.R;

/**
 * Created by suzeyu on 16/8/1.
 */

public class ModeSingleTaskAct extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ModeSingleTaskAct.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById(R.id.btn_main).setOnClickListener(this);
        findViewById(R.id.btn_self).setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.btn_self:
                Intent intent = new Intent(getApplicationContext(), ModeSingleTaskAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
