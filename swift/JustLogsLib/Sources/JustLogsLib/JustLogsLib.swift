// The Swift Programming Language
// https://docs.swift.org/swift-book

import Foundation

public typealias LogCallback = @Sendable @convention(c) (UnsafePointer<CChar>) -> Void

final class LogStorage: @unchecked Sendable {
    var hook: LogCallback?
}

private let globalLog = LogStorage()

@_cdecl("register_swift_log_hook")
public func register_swift_log_hook(callback: @escaping LogCallback) {
    print("[Swift] Log hook is being registered.")
    globalLog.hook = callback
}

public func swiftLog(_ message: String) {
    guard let logger = globalLog.hook else {
        print("[Swift] Log hook not registered. Message: \(message)")
        return
    }

    message.withCString { cString in
        logger(cString)
    }
}

@_cdecl("swift_produce_logs")
public func swift_produce_logs() {
    swiftLog("Swift: Starting logging demo")
    swiftLog("Swift: Doing some work...")
    swiftLog("Swift: Finished successfully")
}

@_cdecl("swift_receive_int")
public func swift_receive_int(_ value: Int32) {
    swiftLog("Swift: Received integer from Kotlin \(value)")
}
