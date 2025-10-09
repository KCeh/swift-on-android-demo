#include <jni.h>
#include <string>

// Declare Swift functions
extern "C" {
const char* hello_from_swift();
void free_string(const char* ptr);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_karlo_ceh_swiftonandroiddemo_SwiftBridge_getHelloFromSwift(
        JNIEnv* env,
        jobject /* this */)
{
    // Call the Swift function to get the message.
    const char* message = hello_from_swift();

    // Convert the C-style string (char*) to a Java string (jstring).
    jstring result = env->NewStringUTF(message);

    // Free the memory that was allocated on the Swift side to prevent a memory leak.
    free_string(message);

    // Return the Java string to the caller (your Kotlin/Java code).
    return result;
}