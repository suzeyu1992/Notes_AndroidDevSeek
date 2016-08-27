//
// Created by 苏泽钰 on 16/8/27.
// test.c

#include "com_szysky_note_androiddevseek_14_JNITest.h"
#include <stdio.h>

JNIEXPORT jstring JNICALL Java_com_szysky_note_androiddevseek_114_JNITest_get(JNIEnv *env, jobject thiz){
    printf("执行在c文件中 get方法\n");
    return (*env)->NewStringUTF(env, "Hello from JNI .");
}

JNIEXPORT void JNICALL Java_com_szysky_note_androiddevseek_114_JNITest_set(JNIEnv *env, jobject thiz, jstring string){
    printf("执行在c文件中 set方法\n");
    char* str = (char*) (*env)->GetStringUTFChars(env, string, NULL);
    printf("%s\n", str);
    (*env)->ReleaseStringUTFChars(env, string, str);
}

