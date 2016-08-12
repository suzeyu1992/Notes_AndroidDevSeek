package com.szysky.note.androiddevseek_02.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by suzeyu on 16/8/3.
 * 创建服务端  使用AIDL实现跨进程
 */

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";

    /**
     * 管理所有书籍的集合
     */
    private CopyOnWriteArrayList<Book> mBooklist = new CopyOnWriteArrayList<>();

    /**
     * 保存所有注册新书到来的客户端集合
     */
    private RemoteCallbackList<INewBookArrivedListener> mListeners = new RemoteCallbackList<>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooklist;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooklist.add(book);
        }

        @Override
        public void registerListener(INewBookArrivedListener listener) throws RemoteException {
            //判断新增监听者是否存在
            mListeners.register(listener);
            Log.e(TAG, "服务端检查对象:"+listener.toString());

            Log.i(TAG, "服务端注册成功: current listener size:"+mListeners.getRegisteredCallbackCount());
        }

        @Override
        public void unregisterListener(INewBookArrivedListener listener) throws RemoteException {
            mListeners.unregister(listener);

            Log.i(TAG, "服务端解注册成功: current listener size:"+mListeners.getRegisteredCallbackCount());
        }

        /**
         *  这里也可以判断远程调用权限   这判断一个包名
         *
         */
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packagesForUid = getPackageManager().getPackagesForUid(getCallingUid());

            if (packagesForUid != null && packagesForUid.length >0){
                packageName = packagesForUid[0];
            }
            Log.e(TAG, "onTransact: -----------------------------" + packageName);
            if (!packageName.startsWith("com.szysky")){
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBooklist.add(new Book("十万个为什么",0));
        mBooklist.add(new Book("吸血鬼的故事",1));

        new Thread(new ServiceWorker()).start();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //做一下权限的验证  在清单文件中声明了一个,  并添加了使用权限
        int check = checkCallingOrSelfPermission("com.szysky.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED){
            return  null;
        }
        return mBinder;
    }



    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    /**
     * 创建服务端新线程的Runnable接口, 每隔5秒就新增一本书
     */
    private class ServiceWorker implements Runnable{
        @Override
        public void run() {

            while (! mIsServiceDestoryed.get()){
                SystemClock.sleep(5000);
                int newBookId= mBooklist.size() + 1;
                Book newBook = new Book("new Book-" + newBookId, newBookId);

                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 有新书了就调用注册监听的客户端的回调方法
     */
    private void onNewBookArrived(Book newBook) throws RemoteException {
        mBooklist.add(newBook);
        Log.d(TAG, "onNewBookArrived: 服务端有新书来了");

        int N = mListeners.beginBroadcast();

        for (int i = 0; i<N; i++){
            INewBookArrivedListener listener = mListeners.getBroadcastItem(i);
            if (listener != null){
                listener.onNewBookArrived(newBook);
            }
        }

        mListeners.finishBroadcast();
    }

}
