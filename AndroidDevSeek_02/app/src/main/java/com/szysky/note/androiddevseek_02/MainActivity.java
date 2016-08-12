package com.szysky.note.androiddevseek_02;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.szysky.note.androiddevseek_02.aidl.BookMangerActivity;
import com.szysky.note.androiddevseek_02.domain.Student;
import com.szysky.note.androiddevseek_02.message.MessengerActivity;
import com.szysky.note.androiddevseek_02.mulprocess.SecondActivity;
import com.szysky.note.androiddevseek_02.mulprocess.StaticTest;
import com.szysky.note.androiddevseek_02.provider.ProviderActivity;
import com.szysky.note.androiddevseek_02.socket.SocketActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    /**
     * 是否已经序列化
     */
    private boolean mIsSerializable;
    private boolean mIsParcelable;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BookMangerActivity.NEW_BOOK_ARRIVED:
                    Log.e(TAG, "我糙  能收到不??" );
                    break;
                default:
                    super.handleMessage(msg);


            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_serializable).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_parcelable).setOnClickListener(this);
        findViewById(R.id.btn_message).setOnClickListener(this);
        findViewById(R.id.btn_aidl).setOnClickListener(this);
        findViewById(R.id.btn_provider).setOnClickListener(this);
        findViewById(R.id.btn_socket).setOnClickListener(this);

        //打印一个静态属性值
        Log.d(TAG, " 静态值为:"+(StaticTest.sTest));

        StaticTest.sTest++;  //对静态变量进行自增


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //测试Serializable序列化
            case R.id.btn_serializable:
                serial();
                break;

//            //测试Parcelable序列化
//            case R.id.btn_parcelable:
//                parcel();
//                break;

            //测试多线程创建
            case R.id.btn_add:
                startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                break;

            //测试使用 Messenger 进行进程间通信
            case R.id.btn_message:
                startActivity(new Intent(getApplicationContext(),MessengerActivity.class));
                break;

            //测试使用 aidl 进行进程间通信
            case R.id.btn_aidl:
                startActivity(new Intent(getApplicationContext(),BookMangerActivity.class));
                break;

            //测试使用 provider 进行进程间通信
            case R.id.btn_provider:
                startActivity(new Intent(getApplicationContext(),ProviderActivity.class));
                break;

            //测试使用 provider 进行进程间通信
            case R.id.btn_socket:
                startActivity(new Intent(getApplicationContext(),SocketActivity.class));
                break;
        }
    }

    /**
     * 测试Serializable序列化
     */
    private void serial(){
        Student stu = new Student("苏泽钰", 22);
        File absoluteFile = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File file = new File(absoluteFile + "/stu.txt");
        if (!mIsSerializable){
            //序列化的过程
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(stu);
                out.close();
                Log.d(TAG, "serial: 序列化成功");
                Toast.makeText(getApplicationContext(),"Serializable序列化成功",Toast.LENGTH_SHORT).show();
                mIsSerializable = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            //反序列化过程
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                Student newStu = (Student) in.readObject();
                Log.d(TAG, "serial: 反序列化成功:"+newStu.toString());
                Toast.makeText(getApplicationContext(),"Serializable反序列化成功"+newStu.toString(),Toast.LENGTH_SHORT).show();

                mIsSerializable = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
