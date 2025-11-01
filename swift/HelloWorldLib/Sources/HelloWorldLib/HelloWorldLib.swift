// The Swift Programming Language
// https://docs.swift.org/swift-book

import Foundation

func helloFromSwift() -> String {
    return "Hello from Swift 👋 \(#file) \(#function) \(#line) \(Date())"
}

public func toCString(_ string: String) -> UnsafePointer<CChar> {
    guard let cString = strdup(string) else {
        return UnsafePointer(strdup(""))
    }
    return UnsafePointer(cString)
}

@_cdecl("hello_from_swift")
public func hello_from_swift() -> UnsafePointer<CChar> {
    toCString(helloFromSwift())
}

@_cdecl("free_string")
public func free_string(_ ptr: UnsafePointer<CChar>) {
    free(UnsafeMutablePointer(mutating: ptr))
}