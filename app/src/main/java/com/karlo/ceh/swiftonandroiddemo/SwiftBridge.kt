package com.karlo.ceh.swiftonandroiddemo

class SwiftBridge {
    companion object {
        init {
//            System.loadLibrary("c++_shared")
//            System.loadLibrary("swiftSwiftOnoneSupport")
//            System.loadLibrary("swiftCore")
//            System.loadLibrary("swiftAndroid")
//            System.loadLibrary("swift_Concurrency")
//            System.loadLibrary("dispatch")
//            System.loadLibrary("BlocksRuntime")
//            System.loadLibrary("swift_StringProcessing")
//            System.loadLibrary("swift_RegexParser")
//            System.loadLibrary("swift_Builtin_float")
//            System.loadLibrary("swift_math")
//            System.loadLibrary("swiftDispatch")
//            System.loadLibrary("Foundation")
//            System.loadLibrary("FoundationEssentials")
//            System.loadLibrary("FoundationInternationalization")
//            System.loadLibrary("_FoundationICU")
//            System.loadLibrary("swiftSynchronization")
            System.loadLibrary("swiftbridge")
        }
    }

    external fun getHelloFromSwift(): String
}