package com.szysky.note.androiddevseek_02.manual;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by suzeyu on 16/8/2.
 * 手动实现Stub类和Stub类中的Proxy代理类
 */

public class NoteManagerImpl extends Binder implements INoteManager {

    /**
     * 给当前Binder生成唯一标识
     */
    public NoteManagerImpl(){
        this.attachInterface(this, DESCRIPTOR);
    }

    /**
     *  将服务器的Binder对象转换成客户端所需要的AIDL接口类型对象
     */
    public static INoteManager asInterface(android.os.IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        if (((iin != null) && (iin instanceof INoteManager))) {
            return ((INoteManager) iin);
        }
        return new Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     *  当客户端发起请求的时候,服务端运行此方法,并把结果返回
     */
    @Override
    public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getNoteList: {
                data.enforceInterface(DESCRIPTOR);
                java.util.List<Note> _result = this.getNoteList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addNote: {
                data.enforceInterface(DESCRIPTOR);
                Note _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Note.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addNote(_arg0);
                reply.writeNoException();
                return true;
            }
        }
        return super.onTransact(code, data, reply, flags);
    }


    @Override
    public List<Note> getNoteList() throws RemoteException {
        return null;
    }

    @Override
    public void addNote(Note note) throws RemoteException {

    }

    private static class Proxy implements INoteManager{
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
        public java.util.List<Note> getNoteList() throws android.os.RemoteException {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<Note> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getNoteList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Note.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        @Override
        public void addNote(Note book) throws android.os.RemoteException {
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
                mRemote.transact(TRANSACTION_addNote, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

    }



}
