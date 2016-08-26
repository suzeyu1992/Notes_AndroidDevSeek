package com.szysky.note.androiddevseek_13;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.os.Build.SUPPORTED_ABIS;

/**
 * Author :  suzeyu
 * Time   :  2016-08-08  下午5:37
 * Blog   :  http://szysky.com
 * GitHub :  https://github.com/suzeyu1992
 * <p>
 * ClassDescription :  对系统全局未捕获的异常进行处理
 */

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = MyCrashHandler.class.getSimpleName();

    /**
     * 定义异常文件要存储的路径
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/szyCrash/log/";

    private static final String FILE_NAME = "crash-";

    private static final String FILE_NAME_SUFFIX = ".trace";

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private Context mContext;

    /**
     * 单例模式三部曲    懒汉式
     */
    private static MyCrashHandler sInstance = new MyCrashHandler();

    private MyCrashHandler() {
    }

    public static MyCrashHandler getsInstance() {
        return sInstance;
    }


    /**
     * 设置本类为当前进行的默认未捕捉异常处理器
     */
    public void init(Context context) {
        mContext = context.getApplicationContext();
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            // 导出信息到sd卡上
            dumpExceptionToSDCard(ex);

            // 对本地的信息进行上传服务器处理
            uploadExceptionToServer();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 也把异常打印到控制台 防止系统没有默认异常处理器导致的没有崩溃信息
            ex.printStackTrace();

            // 如果系统有默认的异常处理器那么就给系统处理  否则自己关闭掉
            // 如果不交给系统 或者 自己手动杀掉, 那么应用就会进入假死, 点击会出现ANR
            if (mDefaultUncaughtExceptionHandler != null) {
                ex.printStackTrace();
                mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
            } else {
                Process.killProcess(Process.myPid());
            }
        }


    }

    /**
     * 上传发生未捕获的错误日志
     */
    private void uploadExceptionToServer() {
        // TODO: 16/8/25   看具体需求, 也可以在每次启动app后检测本地是否有异常日志
    }

    /**
     * 开始异常信息进行本地存储处理
     *
     * @param ex 程序发生的异常对象
     * @throws IOException 写入文件发生异常
     */
    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        // 首先判断sd卡是否被挂起
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.w(TAG, "sd卡不可用, 跳过 dump Exception");
            return;
        }

        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 获取日志记录时间
        long currentTimeMillis = System.currentTimeMillis();
        String formatTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis));

        // 创建存储异常的文件
        File file = new File(PATH + FILE_NAME + formatTimeStr + FILE_NAME_SUFFIX);


        try {
            // 创建写入流, 开始哗哗写东西
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(formatTimeStr);
            dumpPhoneInfo(pw);
            pw.println();
            pw.println("**********************异常信息**************************");
            ex.printStackTrace(pw);
            pw.close();


        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "存储未捕获异常失败了.");
        }


    }

    /**
     * 读取当前设备信息  并追加到异常日志中
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // 获得应用包管理者 并获取存储当前应用的信息对象
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);

        pw.println("**********************设备信息**************************");
        // 开始写入 应用信息
        pw.println("App Version ");
        pw.print("    VersionName: ");
        pw.println(pi.versionName);
        pw.print("    VersionCode: ");
        pw.println(pi.versionCode);

        // 开始写入 Android版本信息
        pw.println("OS Version ");
        pw.println("    SDK_NAME: " + Build.VERSION.RELEASE);
        pw.println("    SDK_INT: " + Build.VERSION.SDK_INT);


        // 开始写入 手机制造商
        pw.println("Vendor ");
        pw.println("    " + Build.MANUFACTURER);


        // 开始写入 手机型号
        pw.println("Model ");
        pw.println("    " + Build.MODEL);


        // 开始写入 CPU架构
        pw.println("CPU ABI ");
        String[] supportedAbis = SUPPORTED_ABIS;
        for (int i = 0; i < supportedAbis.length; i++) {
            pw.println("    " + supportedAbis[i]);
        }
        pw.println("*************************end***************************");


    }

}
