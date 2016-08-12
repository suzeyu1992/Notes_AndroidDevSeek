package com.szysky.note.androiddevseek_02.message;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szysky.note.androiddevseek_02.R;

/**
 * Created by suzeyu on 16/8/3.
 *
 * 对于使用Messenger跨进程通信, 的客户端实现
 */

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    public static final int FROM_CLIENT = 99;
    public static final int FROM_SERVICE = 98;

    /**
     * 声明一个本进程的信使 用来监听并处理服务端传入的消息
     */
    private Messenger mGetReplyMessenger =  new Messenger(new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FROM_SERVICE:
                    Log.d(TAG, "handleMessage: 这里是客户端:::"+msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    });

    /**
     * 具有服务端的IBinder和handler的Messenger信使, 用来给服务端发送. 服务端并能通过handler接收到消息.
     */
    private Messenger mService;

    /**
     * 创建一个服务监听连接对象 并在成功的时候给服务器发送一条消息
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        //绑定成功回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 利用服务端返回的binder对象创建Messenger并使用此对象想服务端发送消息
            mService = new Messenger(service);
            Message obtain = Message.obtain(null, FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "你好啊,  我是从客户端来, 连接成功");
            obtain.setData(bundle);
            // 需要把接收服务端回复的Messenger通过Message的replyTo传递给服务端
            obtain.replyTo = mGetReplyMessenger;
            try {
                mService.send(obtain);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        //打开服务
        Intent intent = new Intent(MessengerActivity.this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        //按钮点击就发送给服务器一条消息
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message obtain = Message.obtain(null, FROM_CLIENT);
                Bundle bundle = new Bundle();
                bundle.putString("msg", "你好啊,  我是从客户端来,我是点击触发的发送消息");
                obtain.setData(bundle);
                // 需要把接收服务端回复的Messenger通过Message的replyTo传递给服务端
                obtain.replyTo = mGetReplyMessenger;
                try {
                    mService.send(obtain);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
