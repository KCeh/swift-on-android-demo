#include <jni.h>
#include <string>
#include <android/log.h>
#include <unistd.h>
#include <stdio.h>
#include <thread>

#define LOG_TAG "SwiftBridge"
#define SWIFT_PRINT_TAG "SwiftPrint"
#define DLOGE(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Swift functions
extern "C" {
    const char *hello_from_swift();
    void free_string(const char *ptr);
    int32_t generate_exception(char *, int32_t);
    void register_swift_log_hook(void (*cb)(const char*));
    void swift_produce_logs();
}

extern "C" void android_log_callback(const char* message) {
    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "%s", message);
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

void redirectStdoutToLogcat() {
    // Disable buffering to flush immediately
    setvbuf(stdout, nullptr, _IONBF, 0);
    setvbuf(stderr, nullptr, _IONBF, 0);

    int pipefd[2];
    if (pipe(pipefd) != 0) return;

    dup2(pipefd[1], STDOUT_FILENO);
    dup2(pipefd[1], STDERR_FILENO);

    std::thread([readFd = pipefd[0]]() {
        char buffer[256];
        ssize_t count;
        while ((count = read(readFd, buffer, sizeof(buffer) - 1)) > 0) {
            buffer[count] = '\0';
            __android_log_write(ANDROID_LOG_DEBUG, SWIFT_PRINT_TAG, buffer);
        }
    }).detach();
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_getHelloFromSwift(
        JNIEnv *env,
        jobject /* this */) {

    const char *message = hello_from_swift();
    jstring result = env->NewStringUTF(message);
    free_string(message);

    return result;
}

extern "C" JNIEXPORT jint
JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_generateException(JNIEnv
*env, jobject) {
    return callSwiftAndPropagate(env, generate_exception);
}

extern "C" JNIEXPORT void JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_initializeLogging(JNIEnv *env, jobject /* this */) {
    DLOGE("Initializing logging, redirecting stdout to logcat...");
    redirectStdoutToLogcat();
    DLOGE("Initializing logger hook...");
    register_swift_log_hook(android_log_callback);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_swiftLogging(JNIEnv* env, jobject /* this */) {
    swift_produce_logs();
}