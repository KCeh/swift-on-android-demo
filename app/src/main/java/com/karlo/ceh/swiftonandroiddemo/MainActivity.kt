package com.karlo.ceh.swiftonandroiddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.karlo.ceh.swiftonandroiddemo.ui.theme.SwiftOnAndroidDemoTheme

class MainActivity : ComponentActivity() {
    companion object {
        init {
            System.loadLibrary("c++_shared")
            System.loadLibrary("swiftSwiftOnoneSupport")
            System.loadLibrary("swiftCore")
            System.loadLibrary("swiftAndroid")
            System.loadLibrary("swift_Concurrency")
            System.loadLibrary("dispatch")
            System.loadLibrary("BlocksRuntime")
            System.loadLibrary("swift_StringProcessing")
            System.loadLibrary("swift_RegexParser")
            System.loadLibrary("HelloWorld")
        }
    }

    private external fun printHelloFromSwift()

    // todo improve jni folder naming
    // todo add buttons for demo scenarios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        printHelloFromSwift()
        setContent {
            SwiftOnAndroidDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwiftOnAndroidDemoTheme {
        Greeting("Android")
    }
}