package com.szysky.note.androiddevseek_14;

/**
 * Author :  suzeyu
 * Time   :  2016-08-08  下午12:18
 * Blog   :  http://szysky.com
 * GitHub :  https://github.com/suzeyu1992
 * <p>
 * ClassDescription : 通过java类进行JNI开发流程的练习
 */

public class JNITest {

    /**
     * 使用说明:
     *  1. 如果没有JNITest.class那么就使用javac命令生成在此文件的同级
     *  2. 在包的根路径使用java命令执行 --> java -Djava.library.path=jni  com.szysky.note.androiddevseek_14.JNITest
     *     jni包下的.jnilib提供给mac系统使用,   .so文件是提供给其他系统
     */

    static {
        System.loadLibrary("jni-test");
    }

    public native String get();
    public native void set(String str);


    // 主函数
    public static void main(String arg[]) {
        JNITest jniTest = new JNITest();
        System.out.println(jniTest.get());
        jniTest.set("java写入数据");
    }

}
