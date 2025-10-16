cmake_minimum_required(VERSION 3.22.1)
project("swiftbridge")

set(PREBUILT_LIBS_DIR ${CMAKE_SOURCE_DIR}/../jniLibs)

add_library(swiftbridge SHARED swiftbridge.cpp)

target_link_directories(swiftbridge
        PRIVATE
        "${PREBUILT_LIBS_DIR}/${ANDROID_ABI}"
)

target_link_libraries(swiftbridge
        PRIVATE
        android
        log
        HelloWorld  # Link by name
)

target_link_libraries(swiftbridge
        PRIVATE
        android
        log
        GenerateException  # Link by name
)