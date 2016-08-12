package com.szysky.note.androiddevseek_02.domain;

import java.io.Serializable;

/**
 * Created by suzeyu on 16/8/2.
 *
 * 创建一个javabean,并实现Serializable来支持序列化或者反序列化
 */

public class Student implements Serializable{
    public static final long serialVersionUID = 123456789L;

    public int id;
    public String name;

    public Student(String name , int id){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
