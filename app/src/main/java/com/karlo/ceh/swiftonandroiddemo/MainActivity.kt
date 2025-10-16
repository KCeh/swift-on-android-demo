package com.karlo.ceh.swiftonandroiddemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.karlo.ceh.swiftonandroiddemo.ui.theme.SwiftOnAndroidDemoTheme
import kotlinx.coroutines.launch

/**
 * Keep in mind this is just demo code
 */
class MainActivity : ComponentActivity() {

    private companion object {
        const val TAG = "MainActivity"
    }

    private val swiftBridge = SwiftBridge()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            SwiftOnAndroidDemoTheme {
                GreetingScreen(
                    snackbarHostState = snackbarHostState,
                    onGetHelloFromSwiftClick = {
                        Log.d(TAG, "Button 'Get hello from Swift' clicked")
                        val message = swiftBridge.getHelloFromSwift()
                        Log.d(TAG, "Message from Swift: $message")

                        scope.launch {
                            snackbarHostState.showSnackbar(message = message)
                        }
                    },
                    onGenerateExceptionFromSwiftClick = {
                        Log.d(TAG, "Button 'Generate exception from Swift' clicked")
                        try {
                            swiftBridge.generateException()
                        } catch (ex: Exception) {
                            val message = "Exception from Swift: ${ex.message}"
                            Log.d(TAG, message)
                            scope.launch {
                                snackbarHostState.showSnackbar(message = message)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun GreetingScreen(
    snackbarHostState: SnackbarHostState,
    onGetHelloFromSwiftClick: () -> Unit,
    onGenerateExceptionFromSwiftClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onGetHelloFromSwiftClick) {
                    Text(text = "Get hello from Swift")
                }

                Button(onClick = onGenerateExceptionFromSwiftClick) {
                    Text(text = "Generate exception from Swift")
                }

                Button(onClick = { }) {
                    Text(text = "Placeholder")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingScreenPreview() {
    SwiftOnAndroidDemoTheme {
        GreetingScreen(
            snackbarHostState = remember { SnackbarHostState() },
            onGetHelloFromSwiftClick = { },
            onGenerateExceptionFromSwiftClick = { },
        )
    }
}