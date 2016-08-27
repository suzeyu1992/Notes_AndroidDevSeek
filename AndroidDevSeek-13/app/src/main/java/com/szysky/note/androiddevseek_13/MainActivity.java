package com.szysky.note.androiddevseek_13;

import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "lalala";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_uncaught).setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_uncaught:
                int errNum = 1/0;
                break;
        }
    }

//    {
//        // 创建目录
//        String dirPath = Environment.getExternalStorageDirectory().getPath() + "//suzeyu//";
//        File dirfile = new File(dirPath);
//        if (!dirfile.exists()){
//            dirfile.mkdirs();
//        }
//        // 创建文件
//        String format = new SimpleDateFormat("MM-dd HH:mm:ss").format(new java.util.Date());
//        File file1 = new File(LOG_PATH + format);
//        if (!file1.exists()){
//            try {
//                file1.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        try {
//            BufferedWriter outBuf = new BufferedWriter(new FileWriter(file1, true));
//            for (int i = 0; i < 10000; i++) {outBuf.append(i+"\r\n");}
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
