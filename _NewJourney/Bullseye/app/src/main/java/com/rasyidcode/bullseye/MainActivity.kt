package com.rasyidcode.bullseye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidcode.bullseye.ui.theme.BullseyeTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BullseyeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Bullseye()
                }
            }
        }
    }
}

@Composable
fun Bullseye() {
    val randomNumber = remember {
        mutableIntStateOf((1..100).random())
    }
    val sliderPosition = remember {
        mutableFloatStateOf(50F)
    }
    val score = remember {
        mutableIntStateOf(0)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "\uD83C\uDFAF BULLSEYE \uD83C\uDFAF",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("My Score is ${score.intValue} points")
        Spacer(modifier = Modifier.weight(1F))
        Text("Match the slider as close as you can do the number:")
        Text("${randomNumber.intValue}")
        Slider(value = sliderPosition.floatValue, onValueChange = {
            sliderPosition.floatValue = it
        }, valueRange = 1F..100F, steps = 100)
        Row(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text("1")
            Spacer(modifier = Modifier.weight(1F))
            Text("100")
        }
        Button(onClick = {
            val difference = abs(randomNumber.intValue - sliderPosition.floatValue).toInt()

            if (difference == 0) {
                score.intValue += 10
            } else if (difference <= 5) {
                score.intValue += 5
            } else if (difference <= 10) {
                score.intValue += 1
            }

            randomNumber.intValue = (1..100).random()

            sliderPosition.floatValue = 50F
        }) {
            Text("TEST IT")
        }
        Spacer(modifier = Modifier.weight(1F))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BullseyeTheme {
        Bullseye()
    }
}