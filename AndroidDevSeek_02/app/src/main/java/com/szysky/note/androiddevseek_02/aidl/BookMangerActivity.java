package com.szysky.note.androiddevseek_02.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.szysky.note.androiddevseek_02.R;

import java.util.List;

/**
 * Created by suzeyu on 16/8/3.
 * 充当客户端    使用跨进程的通信方式为AIDL
 */

public class BookMangerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = BookMangerActivity.class.getName();

    public  static final int NEW_BOOK_ARRIVED = 90;

    /**
     * 根据服务端返回的IBinder对象创建的AIDL接口来给客户端暴露方法
     */
    private IBookManager bookManager;

    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEW_BOOK_ARRIVED:
                    Log.d(TAG, "客户端:  服务端有新书到来-->"+msg.obj.toString());
                    Toast.makeText(getApplicationContext(),"客户端:  服务端有新书到来-->"+msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    };

    /**
     * 创建一个监听的对象
     */
    private INewBookArrivedListener mNewBookListener = new INewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            // 如果有新书 那么此方法会被回调,  并且由于调用处服务端的Binder线程池, 所以给主线程的Handler发送消息,以切换线程
            mhandler.obtainMessage(NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };
    /**
     * 创建服务连接对象
     */
    private ServiceConnection mConnection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //把服务端返回的Binder对象转换成aidl接口定义的类型
            bookManager = IBookManager.Stub.asInterface(service);

            //注册监听
            try {
                service.linkToDeath(mDeat,0);
                Log.e(TAG, "客户端检查对象:"+mNewBookListener.toString());
                bookManager.registerListener(mNewBookListener);
            } catch (RemoteException e) {

            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected: -->"+Thread.currentThread().getName());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        //连接服务端
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);

        //添加点击事件
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_list).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        //解绑监听
        if (bookManager != null && bookManager.asBinder().isBinderAlive()){
            Log.e(TAG, "onDestroy: 监听解绑成功" );
            try {
                bookManager.unregisterListener(mNewBookListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


        //解绑服务
        unbindService(mConnection);
        super.onDestroy();

    }

    IBinder.DeathRecipient mDeat = new IBinder.DeathRecipient() {
        // 当Binder死亡的时候,系统会回调binderDied()方法
        @Override
        public void binderDied() {
            if (bookManager == null)
                return ;
            //清除掉已经无用的Binder连接
//            bookManager.asBinder().unlinkToDeath(mDeat,0);
//            bookManager = null;
            //TODO  进行重新绑定远程服务

            Log.e(TAG, "binderDied: -->"+Thread.currentThread().getName() );
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                try {
                    bookManager.addBook(new Book("新增书籍",3));
                    Toast.makeText(BookMangerActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_list:
                try {
                    List<Book> bookList = bookManager.getBookList();
                    Log.d(TAG, "onServiceConnected: 查看list的类型--> " + bookList.getClass().getCanonicalName());
                    Log.d(TAG, "onServiceConnected: 查看list中的列表-->  "+ bookList.toString());
                    Toast.makeText(BookMangerActivity.this, bookList.toString(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
