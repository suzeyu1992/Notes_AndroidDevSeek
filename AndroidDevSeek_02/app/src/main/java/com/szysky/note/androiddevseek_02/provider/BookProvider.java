package com.szysky.note.androiddevseek_02.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by suzeyu on 16/8/4.
 * 创建一个内容提供者
 */

public class BookProvider extends ContentProvider {
    private static final String TAG = BookProvider.class.getName();

    /**
     * 声明此Provider的唯一标识字符串
     */
    private static final String AUTHORITY = "com.szysky.note.androiddevseek_02.provider";

    /**
     * 指定两个操作的Uri
     */
    private static final Uri BOOK_CONTENT_URI = Uri.parse("content://" +AUTHORITY + "/book");
    private static final Uri USER_CONTENT_URI = Uri.parse("content://" +AUTHORITY + "/user");

    /**
     * 创建Uri对应的Uri_Code
     */
    private static final int BOOK_URI_CODE = 1;
    private static final int USER_URI_CODE = 2;

    /**
     * 创建一个管理Uri和Uri_Code的对象
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //进行关联
        sUriMatcher.addURI(AUTHORITY, "book",BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user",USER_URI_CODE);
    }

    private SQLiteDatabase mDb;


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        //对数据库的初始化, 这里要注意尽量不要在UI线程做操作数据库这种耗时的操作.
        initProviderDB();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: ");

        //获取表名
        String tableName = getTableName(uri);
        if (tableName == null)
            throw new IllegalArgumentException("不被支持的Uri参数-->"+uri );
        return mDb.query(tableName, projection, selection, selectionArgs, null, null, sortOrder,null);

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: ");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: ");
        //获取表名
        String tableName = getTableName(uri);
        if (tableName == null)
            throw new IllegalArgumentException("不被支持的Uri参数-->"+uri );

        //插入数据库
        long insert = mDb.insert(tableName, null, values);
        //告诉内容提供者有数据库有变化, 需要刷新
        getContext().getContentResolver().notifyChange(uri,null);

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: ");

        //获取表名
        String tableName = getTableName(uri);
        if (tableName == null)
            throw new IllegalArgumentException("不被支持的Uri参数-->"+uri );

        //删除数据库一条数据
        int count = mDb.delete(tableName, selection, selectionArgs);
        if (count > 0){
            //告诉内容提供者有数据库有变化, 需要刷新
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: ");
        //获取表名
        String tableName = getTableName(uri);
        if (tableName == null)
            throw new IllegalArgumentException("不被支持的Uri参数-->"+uri );

        int row = mDb.update(tableName, values, selection, selectionArgs);

        if (row > 0){
            //告诉内容提供者有数据库有变化, 需要刷新
            getContext().getContentResolver().notifyChange(uri,null);
        }

        return row;
    }

    private void initProviderDB(){
        mDb = new DbHelper(getContext()).getWritableDatabase();
        //删除旧表内容, 重新添加信息
        mDb.execSQL(" delete from " + DbHelper.USER_TABLE_NAME);
        mDb.execSQL(" delete from " + DbHelper.BOOK_TABLE_NAME);
        mDb.execSQL("insert into book values(1,'android')");
        mDb.execSQL("insert into book values(2,'node.js')");
        mDb.execSQL("insert into book values(3,'java')");
        mDb.execSQL("insert into user values(1,'张三',1)");
        mDb.execSQL("insert into user values(2,'李四',0)");
    }

    /**
     * 通过自动以的Uri来判断对应的数据库表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tableName = DbHelper.BOOK_TABLE_NAME;
                break;

            case USER_URI_CODE:
                tableName = DbHelper.USER_TABLE_NAME;
                break;
            default:break;
        }
        return tableName;
    }
}
