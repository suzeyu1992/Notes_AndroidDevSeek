//
// Created by 苏泽钰 on 16/8/27.
// test.app

#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

 jstring  Java_com_szysky_note_androiddevseek_114_MainActivity_get(JNIEnv *env, jobject thiz){
    printf("执行在c++文件中 get方法\n");
    return env->NewStringUTF("Hello from JNI .");

}
 void  Java_com_szysky_note_androiddevseek_114_MainActivity_set(JNIEnv *env, jobject thiz, jstring string){
    printf("执行在c++文件中 set方法\n");
    char* str = (char*) env->GetStringUTFChars(string, NULL);
    printf("%s\n", str);

    env->ReleaseStringUTFChars(string, str);
}



// 定义调用java中的方法的函数
void callJavaMethod( JNIEnv *env, jobject thiz){
    // 先找到要调用的类
    jclass clazz = env -> FindClass("com/szysky/note/androiddevseek_14/MainActivity");

    if (clazz == NULL){
        printf("找不到要调用方法的所属类");
        return;
    }

    // 获取java方法id
    // 参数二是调用的方法名,  参数三是方法的签名
    jmethodID id = env -> GetStaticMethodID(clazz, "methodCalledByJni", "(Ljava/lang/String;)V");

    if (id == NULL){
        printf("找不到要调用方法");
        return;
    }

    jstring msg = env->NewStringUTF("我是在c中生成的字符串");

    // 开始调用java中的静态方法
    env -> CallStaticVoidMethod(clazz, id, msg);
}
void Java_com_szysky_note_androiddevseek_114_MainActivity_callJNIConvertJavaMethod(JNIEnv *env, jobject thiz){
    printf("调用c代码成功, 马上回调java中的代码");
    callJavaMethod(env, thiz);
}

#ifdef __cplusplus
}
#endif