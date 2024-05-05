package com.rasyidcode.stateandjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidcode.stateandjetpackcompose.ui.theme.StateAndJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateAndJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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
    StateAndJetpackComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello!",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Name") })
    }
}

@Preview(showBackground = true)
@Composable
fun HelloContentPreview() {
    HelloContent()
}

// State in an app is any value that can change over time.
/**
 * A few example of state in Android apps:
 * - A Snackbar that shows when a network connection can't be established.
 * - A blog post and associated comments.
 * - Ripple animations on buttons that play when a user clicks them.
 * - Stickers that a user can draw on top of an image.
 */

/**
 * Key terms:
 * - Composition -> a description of the UI built Jetpack Compose when it executes composables.
 * - Initial composition -> creation of a Composition by running composables the first time.
 * - Recomposition -> re-running composables to update the Composition when data changes.
 */