// INewBookArrivedListener.aidl.aidl
package com.szysky.note.androiddevseek_02.aidl;

import com.szysky.note.androiddevseek_02.aidl.Book;

// 定义一个给客户端添加服务端新书到达的监听
interface INewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
