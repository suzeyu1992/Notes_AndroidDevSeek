package com.szysky.note.androiddevseek_02.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suzeyu on 16/8/2.
 * 普通的javabean对象
 */

public class Book implements Parcelable{
    public String mBookName;
    public int mBookId;
    public Book(String mBookName, int mBookId){
        this.mBookId = mBookId;
        this.mBookName = mBookName;
    }

    protected Book(Parcel in) {
        mBookName = in.readString();
        mBookId = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mBookName);
        parcel.writeInt(mBookId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "mBookName='" + mBookName + '\'' +
                ", mBookId=" + mBookId +
                '}';
    }
}
