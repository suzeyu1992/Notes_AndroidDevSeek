// IBookManager.aidl
package com.szysky.note.androiddevseek_02.aidl;

// Declare any non-default types here with import statements

import com.szysky.note.androiddevseek_02.aidl.Book;
import com.szysky.note.androiddevseek_02.aidl.INewBookArrivedListener;

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
   void registerListener(INewBookArrivedListener listener);
   void unregisterListener(INewBookArrivedListener listener);

}
