package com.szysky.note.androiddevseek_02.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.szysky.note.androiddevseek_02.R;

/**
 * Created by suzeyu on 16/8/4.
 *
 * 练习内容提供者的代码
 */

public class ProviderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ProviderActivity.class.getName();

    private StringBuilder  mStringBuild= new StringBuilder();
    private int mBookId = 10;
    private int mUserId = 10;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
//        Uri uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider");
//        getContentResolver().query(uri, null, null, null, null);
        findViewById(R.id.btn_add_book).setOnClickListener(this);
        findViewById(R.id.btn_add_user).setOnClickListener(this);
        findViewById(R.id.btn_query_book).setOnClickListener(this);
        findViewById(R.id.btn_query_user).setOnClickListener(this);
        findViewById(R.id.btn_delete_book).setOnClickListener(this);
        findViewById(R.id.btn_delete_user).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_book:
                Uri book_insert_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/book");
                //创建插入的一列数据
                ContentValues contentValues_insert_book = new ContentValues();
                contentValues_insert_book.put("_id",(mBookId++));
                contentValues_insert_book.put("name","新增书籍"+(mBookId++));
                
                getContentResolver().insert(book_insert_uri, contentValues_insert_book);

                //友情提示
                Toast.makeText(getApplicationContext(), "插入ok",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "插入ok------------>\r\n");
                
                break;

            case R.id.btn_add_user:
                Uri user_insert_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/user");
                //创建插入的一列数据
                ContentValues contentValues_insert_user = new ContentValues();
                contentValues_insert_user.put("_id",(mUserId++));
                contentValues_insert_user.put("name","新增小人-"+(mUserId++));

                getContentResolver().insert(user_insert_uri, contentValues_insert_user);

                //友情提示
                Toast.makeText(getApplicationContext(), "插入ok",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "插入ok------------>\r\n");
                break;

            case R.id.btn_query_book:
                Uri book_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/book");
                Cursor query = getContentResolver().query(book_uri, new String[]{"_id", "name"}, null, null, null);
                while (query!=null && query.moveToNext()){
                    int id = query.getInt(0);
                    String name = query.getString(1);
                    mStringBuild.append("** 查询书的结果--> id:"+id+"  书名:" +name+"\r\n");
                }

                //友情提示
                Toast.makeText(getApplicationContext(), mStringBuild.toString(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "查询结果------------>\r\n"+mStringBuild.toString());
                break;

            case R.id.btn_query_user:
                Uri book_query_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/user");
                Cursor query_user = getContentResolver().query(book_query_uri, new String[]{"_id", "name"}, null, null, null);
                while (query_user!=null && query_user.moveToNext()){
                    int id = query_user.getInt(0);
                    String name = query_user.getString(1);
                    mStringBuild.append("** 查询人的结果--> id:"+id+"  姓名:" +name+"\r\n");
                }

                //友情提示
                Toast.makeText(getApplicationContext(), mStringBuild.toString(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "查询结果------------>\r\n"+mStringBuild.toString());
                break;

            case R.id.btn_delete_book:
                Uri user_delete_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/book");

                getContentResolver().delete(user_delete_uri, "_id > 0", null);

                //友情提示
                Toast.makeText(getApplicationContext(), "清空成功",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "清空成功------------>\r\n"+mStringBuild.toString());
                break;

            case R.id.btn_delete_user:
                Uri book_delete_uri = Uri.parse("content://com.szysky.note.androiddevseek_02.provider/user");

                getContentResolver().delete(book_delete_uri, "_id > 0", null);

                //友情提示
                Toast.makeText(getApplicationContext(), "清空成功",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "清空成功------------>\r\n"+mStringBuild.toString());

                break;
        }
        mStringBuild.delete(0,mStringBuild.length());

    }
}
