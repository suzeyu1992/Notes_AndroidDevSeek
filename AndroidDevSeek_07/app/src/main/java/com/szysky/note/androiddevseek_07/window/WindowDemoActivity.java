package com.szysky.note.androiddevseek_07.window;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.szysky.note.androiddevseek_07.R;

public class WindowDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_demo);

        //利用WindowManager添加一个Window
       addWindow();



       addSystemDialog();

    }

    /**
     * 添加一个系统级别的dialog
     */
    private void addSystemDialog() {
        Dialog dialog = new Dialog(getApplicationContext());
        TextView textView = new TextView(this);
        textView.setText("测试");

        dialog.setContentView(textView);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }

    /**
     * 利用WindowManager添加一个Window
     */
    public void addWindow(){
        Button button = new Button(getApplicationContext());
        button.setText("动态添加");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);

        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

        layoutParams.gravity = Gravity.LEFT ;

        layoutParams.width = 400;
        layoutParams.height = 300;

        getWindowManager().addView(button, layoutParams);

    }
}
