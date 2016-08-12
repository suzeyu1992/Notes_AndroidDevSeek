package com.szysky.note.androiddevseek_02.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by suzeyu on 16/8/4.
 *
 * 创建一个数据库管理类
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";
    private static final int DB_VERSION = 1;
    /**
     * 创建书的数据库表的 sql 语句
     */
    private static final String CREATE_TABLE_BOOK = "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_NAME +"(_id INTEGER PRIMARY KEY, name TEXT)";

    /**
     * 创建用户的数据库表的 sql 语句
     */
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME +"(_id INTEGER PRIMARY KEY, name TEXT, sex INT)";



    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行建表语句
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
