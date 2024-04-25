package com.rasyidcode.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
//            LemonApp()
        }
    }
}

// failed attempt-1
@Composable
fun LemonadeItem(modifier: Modifier = Modifier) {
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }
    val currentImage = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val currentText = when (currentStep) {
        1 -> R.string.lemon_tree_text
        2 -> R.string.lemon_squeeze_text
        3 -> R.string.lemon_drink_text
        else -> R.string.lemon_restart_text
    }
    val currentImageDesc = when (currentStep) {
        1 -> R.string.lemon_tree_image_desc
        2 -> R.string.lemon_squeeze_image_desc
        3 -> R.string.lemon_drink_image_desc
        else -> R.string.lemon_restart_image_desc
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = currentImage),
            contentDescription = stringResource(id = currentImageDesc),
            modifier = Modifier
                .background(Color.Yellow)
                .clickable {
                    currentStep = when (currentStep) {
                        1 -> {
                            squeezeCount = (2..4).random()
                            2
                        }
                        2 -> {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                3
                            } else {
                                2
                            }
                        }
                        3 -> 4
                        else -> 1
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = currentText), fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeItem(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

// attempt-2
@Composable
fun LemonApp() {
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentStep) {
            1 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lemon_tree),
                        contentDescription = stringResource(
                            id = R.string.lemon_tree_image_desc
                        ),
                        modifier = Modifier.clickable {
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.lemon_tree_text))
                }
            }

            2 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lemon_squeeze),
                        contentDescription = stringResource(
                            id = R.string.lemon_squeeze_image_desc
                        ), modifier = Modifier.clickable {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.lemon_squeeze_text))
                }
            }

            3 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lemon_drink),
                        contentDescription = stringResource(
                            id = R.string.lemon_drink_image_desc
                        ), modifier = Modifier.clickable {
                            currentStep = 4
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.lemon_drink_text))
                }
            }

            4 -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.lemon_restart),
                        contentDescription = stringResource(
                            id = R.string.lemon_restart_image_desc
                        ), modifier = Modifier.clickable {
                            currentStep = 1
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = stringResource(id = R.string.lemon_restart_text))
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}

// solution
