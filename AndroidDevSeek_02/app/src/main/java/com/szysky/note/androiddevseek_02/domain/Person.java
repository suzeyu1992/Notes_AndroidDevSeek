package com.szysky.note.androiddevseek_02.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suzeyu on 16/8/2.
 *
 * 创建一个javabean,并实现Parcelable来支持序列化或者反序列化
 */

public class Person implements Parcelable{

    public int id;
    public String name;

    public Person(String name , int id){
        this.id = id;
        this.name = name;
    }

    protected Person(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
