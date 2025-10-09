// The Swift Programming Language
// https://docs.swift.org/swift-book

import Foundation

// Java_{package_with_underscores}_{ClassName}_{methodName}
// example Java_com_karlo_ceh_swiftonandroiddemo_MainActivity_helloFromSwift


func helloFromSwift() -> String {
    return "Hello from Swift"
}

@_cdecl("hello_from_swift")
public func hello_from_swift() -> UnsafePointer<CChar> {
    let message = helloFromSwift()
    guard let cString = strdup(message) else {
        return UnsafePointer(strdup(""))
    }
    return UnsafePointer(cString)
}

@_cdecl("free_string")
public func free_string(_ ptr: UnsafePointer<CChar>) {
    free(UnsafeMutablePointer(mutating: ptr))
}