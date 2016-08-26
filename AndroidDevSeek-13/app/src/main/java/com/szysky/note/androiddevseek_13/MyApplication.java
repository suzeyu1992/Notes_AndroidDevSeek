package com.szysky.note.androiddevseek_13;

import android.app.Application;

/**
 * Author :  suzeyu
 * Time   :  2016-08-08  下午6:23
 * Blog   :  http://szysky.com
 * GitHub :  https://github.com/suzeyu1992
 * ClassDescription : 自定义Application
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyCrashHandler.getsInstance().init(getApplicationContext());
    }
}
