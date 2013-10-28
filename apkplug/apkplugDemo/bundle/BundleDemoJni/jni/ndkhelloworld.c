#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <assert.h>
//修改日志tag中的值
#define LOG_TAG "logfromc"
//日志显示的等级
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
jstring stringFromJNI(JNIEnv* env, jobject javaThis)
{
  
    return (*env)->NewStringUTF(env, "helloworld 我是插件的Jni");
}

static JNINativeMethod gMethods[] = {
        {"stringFromJNI", "()Ljava/lang/String;",(void*)stringFromJNI},
       
};

#define JNIT_CLASS "com/apkplug/bundle/example/bundledemojni/MainActivity"//指定要注册的类
JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    JNIEnv *env = NULL;
    if ((*jvm)->GetEnv(jvm, (void**)&env, JNI_VERSION_1_4)){
        return JNI_ERR;
    }

    jclass cls = (*env)->FindClass(env, JNIT_CLASS);
    if (cls == NULL)
    {
        return JNI_ERR;
    }
    jint nRes = (*env)->RegisterNatives(env, cls, gMethods, sizeof(gMethods)/sizeof(gMethods[0]));
    if (nRes < 0)
    {
        return JNI_ERR;
    }
    return JNI_VERSION_1_4;
}
JNI_OnUnLoad(JavaVM *jvm, void *reserved)
{
    JNIEnv *env = NULL;
    if ((*jvm)->GetEnv(jvm, (void**)&env, JNI_VERSION_1_4)){
        return;
    }
    jclass cls = (*env)->FindClass(env, JNIT_CLASS);
    if (cls == NULL)
    {
        return;
    }
    jint nRes = (*env)->UnregisterNatives(env, cls);
    return;
}
