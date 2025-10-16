#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "SwiftBridge"
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Declare Swift functions
extern "C" {
const char *hello_from_swift();
void free_string(const char *ptr);
int32_t generate_exception(char *, int32_t);
}


template<typename SwiftFunc>
jint callSwiftAndPropagate(JNIEnv *env, SwiftFunc swiftFunc) {
    char errBuf[512];
    memset(errBuf, 0, sizeof(errBuf));
    int32_t res = swiftFunc(errBuf, sizeof(errBuf));

    if (res != 0) {
        const char *msg = errBuf[0] ? errBuf : "Unknown Swift error";
        ALOGE("Swift threw: %s", msg);
        jclass exc = env->FindClass("java/lang/RuntimeException");
        if (exc) env->ThrowNew(exc, msg);
    }
    return res;
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_getHelloFromSwift(
        JNIEnv *env,
        jobject /* this */) {
    // Call the Swift function to get the message.
    const char *message = hello_from_swift();

    // Convert the C-style string (char*) to a Java string (jstring).
    jstring result = env->NewStringUTF(message);

    // Free the memory that was allocated on the Swift side to prevent a memory leak.
    free_string(message);

    // Return the Java string to the caller (your Kotlin/Java code).
    return result;
}

extern "C" JNIEXPORT jint
JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_generateException(JNIEnv
*env, jobject) {
    return callSwiftAndPropagate(env, generate_exception);
}