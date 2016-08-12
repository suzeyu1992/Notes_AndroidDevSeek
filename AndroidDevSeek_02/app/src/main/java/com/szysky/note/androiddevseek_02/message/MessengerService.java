package com.szysky.note.androiddevseek_02.message;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by suzeyu on 16/8/3.
 * 作为服务端的实现   主要演示通过Messenger来传递信息
 */

public class MessengerService extends Service {


    /**
     * 编写一个类继承Handler,并对客户端发来的消息进行处理操作进行添加
     */
    private static  class MessengerHandler extends Handler{
        private static final String TAG = "MessengerHandler";

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //客户端发来的信息标识
                case MessengerActivity.FROM_CLIENT:
                    Log.d(TAG, "handleMessage: receive msg form clinet-->" +msg.getData().getString("msg"));

                    //对客户端进行reply回答
                    // 1. 通过接收到的到客户端的Message对象获取到Messenger信使
                    Messenger client = msg.replyTo;
                    // 2. 创建一个信息Message对象,并把一些数据加入到这个对象中
                    Message replyMessage = Message.obtain(null, MessengerActivity.FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "我是服务端发送的消息,我已经接收到你的消息了,你应该在你的客户端可以看到");
                    replyMessage.setData(bundle);
                    // 3. 通过信使Messenger发送封装好的Message信息
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * 创建一个Messenger信使
     */
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
