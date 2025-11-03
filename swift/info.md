This folder contains Swift source code that can be used to build libraries used in Android app

Prerequisites:
* Swift toolchain
* [Swift Android SDK](https://www.swift.org/blog/nightly-swift-sdk-for-android/)
* Android NDK

Compile Swift lib:
1. Navigate do library folder (eg. HelloWorldLib)
2. Execute command in terminal:
`swiftly run swift build --swift-sdk aarch64-unknown-linux-android28 +6.2`
3. Find `.so` file inside `.build` folder. `.so` file can be then integrated into Android app.


If you want to debug lib use this command to build debuggable version:
`swiftly run swift build --configuration debug --swift-sdk aarch64-unknown-linux-android28 +6.2`


<!---
TODO: Add build script
-->