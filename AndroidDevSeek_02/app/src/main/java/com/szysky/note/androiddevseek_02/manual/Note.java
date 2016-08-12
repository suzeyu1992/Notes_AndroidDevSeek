package com.szysky.note.androiddevseek_02.manual;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suzeyu on 16/8/2.
 * 创建一个javabean对象实现Parcelable
 *  用于之后手动生成Binder对象
 */

public class Note implements Parcelable{
    public String name;
    public int id;

    public Note(String name, int id){
        this.name = name;
        this.id = id;
    }

    protected Note(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
    }
}
