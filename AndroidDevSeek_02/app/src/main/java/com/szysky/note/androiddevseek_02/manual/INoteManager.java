package com.szysky.note.androiddevseek_02.manual;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * Created by suzeyu on 16/8/2.
 * 声明一个AIDL性质的接口, 只需要继承IInterface接口即可
 */

public interface INoteManager extends IInterface {
    /**
     * 声明一个Binder的描述符  值为本类的全限定路径
     */
    public static final String DESCRIPTOR = "com.szysky.note.androiddevseek_02.manual.INoteManager";


    /**
     * 定义两个需要操作的抽象方法
     */
    public List<Note> getNoteList() throws RemoteException;
    public void  addNote(Note note) throws RemoteException;

    /**
     * 给上面的两个方法声明唯一id,好让Binder在调用的时候知道具体调用哪一个方法
     * 如果还有其他方法,按照规则依次添加即可
     */
    static final int TRANSACTION_getNoteList = IBinder.FIRST_CALL_TRANSACTION + 0;
    static final int TRANSACTION_addNote = IBinder.FIRST_CALL_TRANSACTION + 1;
}
