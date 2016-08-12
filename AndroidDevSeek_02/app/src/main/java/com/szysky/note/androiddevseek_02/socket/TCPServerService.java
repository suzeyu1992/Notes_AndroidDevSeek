package com.szysky.note.androiddevseek_02.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by suzeyu on 16/8/5.
 * <p>
 * 使用TCP监听端口 , 来实现跨进程通信
 */

public class TCPServerService extends Service {

    private static final String TAG = TCPServerService.class.getName();
    private boolean mIsServiceDestoryed = false;
    private String[] mDefMessages = new String[]{
            "感觉身体被掏空."
            , "你好, 现在比较忙一会再说"
            , "夏天真是太热了"
            , "你叫什么名字"
            , "你是不是傻"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 开启线程, 对某一个端口进行监听, 如果有新的客户端接入,  那就传入连接流处理方法中
     */
    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听一个端口
                serverSocket = new ServerSocket(3333);
            } catch (IOException e) {
                e.printStackTrace();
                return;     //端口被占用 , 那么直接返回后面的调用没有任何意义
            }

            while (!mIsServiceDestoryed) {
                try {
                    final Socket accept = serverSocket.accept();
                    Log.e(TAG, "run: 发现新的 socket");

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(accept);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } catch (IOException e) {

                }
            }
        }
    }


    /**
     * 接收一个连接流, 并对其一直进行检测
     *
     * @throws IOException
     */
    private void responseClient(Socket client) throws IOException {
        //j接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //发送到客户端
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);

        out.println("欢迎来到直播间");

        //判断服务标志是否销毁, 没有销毁那么就一直监听此链接的Socket流
        while (!mIsServiceDestoryed) {
            String str = in.readLine();
            Log.v(TAG, "responseClient: 读取到客户端到来的消息-->" + str);

            //判断如果取出来的是null,那么就说明连接已经断开
            if (str == null)
                break;

            String sendMessage = mDefMessages[new Random().nextInt(mDefMessages.length)];
            out.println(sendMessage);
            Log.v(TAG, "responseClient: 顺便回复一条-->" + sendMessage);

        }

        //准备关闭 开启的流
        Log.e(TAG, "responseClient: 准备退出!!!!!" + client.getPort());
        in.close();
        out.close();
        client.close();

    }

}
