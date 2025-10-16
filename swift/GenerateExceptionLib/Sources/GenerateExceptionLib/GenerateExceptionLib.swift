// The Swift Programming Language
// https://docs.swift.org/swift-book

import Foundation

public struct SwiftErrorBridge {
    public static func perform(
        _ action: () throws -> Void,
        errBuf: UnsafeMutablePointer<CChar>?,
        bufLen: Int32
    ) -> Int32 {
        do {
            try action()
            return 0
        } catch {
            if let errBuf = errBuf, bufLen > 0 {
                let msg = "\(error)"
                msg.withCString { cstr in
                    let len = min(Int(bufLen) - 1, strlen(cstr))
                    strncpy(errBuf, cstr, len)
                    errBuf[len] = 0
                }
            }
            return -1
        }
    }
}

public enum DemoError: Error {
    case intentional(String)
}

fileprivate func willThrow() throws {
    throw DemoError.intentional("Intentional demo error from Swift")
}

@_cdecl("generate_exception")
public func generateException(errBuf: UnsafeMutablePointer<CChar>?, bufLen: Int32) -> Int32 {
    SwiftErrorBridge.perform(
        {
            try willThrow()
        }, errBuf: errBuf, bufLen: bufLen)
}