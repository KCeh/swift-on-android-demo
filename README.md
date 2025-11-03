# swift-on-android-demo

Demo app made for Droidcon Italy 2025 presentation: **Swift on Android - Kotlin dev impressions**

> [!WARNING]
> **Architecture Requirement:** To run this app, you need an `arm64-v8a` compatible device or emulator. The pre-compiled libraries are only provided for this architecture.

## Requirements:
* NDK 27.3.13750724 - you can experiment with different versions of NDK but compatibility hasn't been validated

### If you want to compile Swift code:
* Swift toolchain
* [Swift Android SDK](https://www.swift.org/blog/nightly-swift-sdk-for-android/) for
  compiling Swift for Android 

## Approach
To use Swift code on Android we are creating JNI bridge that bridges native to VM/ART code.

![native-to-vm.svg](resources/native-to-vm.svg)

**Current JNI bridge is written in C++.**
![JNI-bridge-cpp.svg](resources/JNI-bridge-cpp.svg)

It is also possible to write JNI bridge in Swift. Or to use supporting libraries like [swift-java](https://github.com/swiftlang/swift-java).

## Project Structure:

* `swift/` - Contains Swift packages source files with three libraries:
    * `HelloWorldLib/` - Basic string passing demo with file/function/line info
    * `GenerateExceptionLib/` - Swift error to Java exception mapping demo
    * `JustLogsLib/` - Swift logging integration with Android Logcat
* `app/` - Main Android application module
    * `src/main/cpp/` - C++ JNI bridge implementation (`swiftbridge.cpp`, `CMakeLists.txt`)
    * `src/main/jniLibs/` - Pre-compiled native libraries (`.so` files)
    * `src/main/java/` - Android app code with `SwiftBridge.kt` JNI interface

## Swift Libraries Features:

* **HelloWorldLib**: Returns formatted strings with Swift metadata (file name, function name, line
  number, timestamp)
* **GenerateExceptionLib**: Demonstrates Swift error handling with structured error bridging to Java
  exceptions
* **JustLogsLib**: Provides logging infrastructure that routes Swift logs to Android Logcat

## Building Swift Libraries:

1. Navigate to library folder (e.g., `swift/HelloWorldLib/`)
2. Build release version:
   ```bash
   swiftly run swift build --swift-sdk aarch64-unknown-linux-android28 +6.2
   ```
3. For debugging, build debug version:
   ```bash
   swiftly run swift build --configuration debug --swift-sdk aarch64-unknown-linux-android28 +6.2
   ```
4. Find `.so` file in `.build/` folder and copy to `app/src/main/jniLibs/arm64-v8a/`

## Demo
Currently, you can try out:
* Passing of string from Swift to Android. Including info about original source (file name, function name, line number and datetime)
* Mapping Swift error to Java exception
* Reading logs from Swift in Android Logcat

<br>
<img src="/resources/swift-on-android-demo.gif" alt="Demo app" width="320" height="640"/>