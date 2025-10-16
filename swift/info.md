This folder contains Swift source code that can be used to build libraries used in Android app

Compile Swift lib:
1. Navigate do library folder (eg. HelloWorldLib)
2. Execute command in terminal:
`swiftly run swift build --swift-sdk aarch64-unknown-linux-android28 +6.2`
3. Find `.so` file inside `.build` folder


If you want to debug lib use this command to build debuggable version:
`swiftly run swift build --configuration debug --swift-sdk aarch64-unknown-linux-android28 +6.2`


## TODO: Build script