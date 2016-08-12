package com.szysky.note.androiddevseek_02.socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.szysky.note.androiddevseek_02.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by suzeyu on 16/8/5.
 *
 *  测试Socket进行跨进程间通信
 */
public class SocketActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SocketActivity.class.getName();
    /**
     *  连接服务端的Socket流
     */
    private Socket mClientSocket;

    /**
     *  当服务端发送过来消息是的标记
     */
    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    /**
     *  给流发送数据的对象
     */
    private PrintWriter mPrintWrite;
    private EditText et_message;
    private TextView tv_client;
    private TextView tv_server;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:
                    String messageStr = (String) msg.obj;
                    tv_server.append(messageStr);
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_socket);
        //先开启TCP服务端
        Intent intent = new Intent(getApplicationContext(), TCPServerService.class);
        startService(intent);

        // 开启连接服务
        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();

        et_message = (EditText) findViewById(R.id.et_message);
        tv_client = (TextView) findViewById(R.id.tv_client);
        tv_server = (TextView) findViewById(R.id.tv_server);
        findViewById(R.id.btn_send).setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    /**
     * 连接服务端方法 核心代码
     */
    private void connectTCPServer(){
        Socket socket = null;

        // 试图连接服务器, 如果失败休眠一秒重试
        while(socket == null){
            try {
                socket = new Socket("localhost", 3333);
                mClientSocket = socket;
                mPrintWrite = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream())),true );
                Log.e(TAG, "connectTCPServer: 连接 服务端 成功" );
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.e(TAG, "connect tcp server failed, retry........." );
                e.printStackTrace();
//                break;
            }
        }


        //准备接收服务器的消息.
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            //获得了socket流的读入段  需要一直循环读
            while(!SocketActivity.this.isFinishing()){
                String strLine = in.readLine();
                if (strLine != null){
                    Log.d(TAG, "客户端: 接收-->"+strLine);
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, strLine+"\r\n    "+formatTime(System.currentTimeMillis())+"\r\n\r\n").sendToTarget();
                }
            }

            Log.e(TAG, "客户端准备退出" );
            in.close();
            mPrintWrite.close();
            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  接收一个时间戳 并转换成 24:00:00 的格式
     */
    public String formatTime(long time){
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.btn_send:

                //获取输入框文字发送到服务端 并处理界面
                final String s = et_message.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPrintWrite.println(s);

                    }
                }).start();
                tv_client.append(s+"\r\n    "+formatTime(System.currentTimeMillis())+"\r\n\r\n");
                et_message.setText("");

                break;
        }
    }
}
