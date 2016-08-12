/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/suzeyu/Documents/workspace/notes/AndroidDevSeek/notes_02/AndroidDevSeek_02/app/src/main/aidl/com/szysky/note/androiddevseek_02/aidl/IBookManager.aidl
 */
package com.szysky.note.androiddevseek_02.aidl;

/**
 * 自动生成的类继承了IInterface
 */
public interface IBookManager extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     * 内部类Stub就是一个Binder类,当客户端和服务端都位于同一个进程时,方法调用不会走跨进程的transact过程
     *  而当两者位于不同进程时,方法调用需要走transact过程, 这个逻辑由Stub的内部代理类Proxy来完成
     */
    public static abstract class Stub extends android.os.Binder implements com.szysky.note.androiddevseek_02.aidl.IBookManager {
        //Binder的唯一标识, 一般使用当前Binder的全限定名来表示
        private static final java.lang.String DESCRIPTOR = "com.szysky.note.androiddevseek_02.aidl.IBookManager";


        /**
         * 用于将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象,这种转换过程是区分进程的,如果客户端与服务端是同一进程
         * 那么此方法返回的就是服务端的Stub对象本身,否则返回的是系统封装后的Stub.proxy对象
         */
        public static com.szysky.note.androiddevseek_02.aidl.IBookManager asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.szysky.note.androiddevseek_02.aidl.IBookManager))) {
                return ((com.szysky.note.androiddevseek_02.aidl.IBookManager) iin);
            }
            return new com.szysky.note.androiddevseek_02.aidl.IBookManager.Stub.Proxy(obj);
        }

        /**
         * 返回当前Binder对象
         */
        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        /**
         *  此方法运行在服务端中Binder线程池中,当客户端发起跨进程请求时, 远程请求会通过系统底层封装后交由此方法来处理.
         *  @param code 服务端通过code可以确定客户端所请求的目标方法是什么
         *  @param data 从data中取出目标方法所需的参数(如果目标方法有形参的话),然后会执行目标方法
         *  @param reply 当方法执行完毕后,就向reply中写入返回值,如果有返回值的话.
         *  @return  如果为返回false那么客户端的返回会失败,可以通过这个特性来做权限验证
         */
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.szysky.note.androiddevseek_02.aidl.Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    com.szysky.note.androiddevseek_02.aidl.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.szysky.note.androiddevseek_02.aidl.Book.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    this.addBook(_arg0);
                    reply.writeNoException();
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        /**
         * 这个类的 getBookList和addBook 都是运行在客户端,当客户端调用此方法.1首先创建该方法所需的输入型Parcel对象_data,输出型Parcel对象_reply和返回值对象(如果有).
         * 2.然后把该方法的参数信息写入到_data中(如果有), 接着调用transact方法来发起RPC远程调用请求,同时当前线程挂起,然后服务端的onTransact()会被调用,直到RPC返回,
         * 当前线程继续执行,并从_reply中取出RPC过程的返回结果,最后返回的_reply的结果
         */
        private static class Proxy implements com.szysky.note.androiddevseek_02.aidl.IBookManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public java.util.List<com.szysky.note.androiddevseek_02.aidl.Book> getBookList() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<com.szysky.note.androiddevseek_02.aidl.Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(com.szysky.note.androiddevseek_02.aidl.Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public void addBook(com.szysky.note.androiddevseek_02.aidl.Book book) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((book != null)) {
                        _data.writeInt(1);
                        book.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        //声明了两个整形id来表示这两个方法, 用于在transact处理过程中确定客户端到底请求是哪个方法
        static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);


        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }
    }

    /**
     *声明了在IBookManager中的两个抽象方法
     */
    public java.util.List<com.szysky.note.androiddevseek_02.aidl.Book> getBookList() throws android.os.RemoteException;

    public void addBook(com.szysky.note.androiddevseek_02.aidl.Book book) throws android.os.RemoteException;
}
