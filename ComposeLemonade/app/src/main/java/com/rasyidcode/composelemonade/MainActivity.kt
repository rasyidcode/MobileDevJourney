package com.rasyidcode.composelemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.composelemonade.model.LemonadeItem
import com.rasyidcode.composelemonade.ui.theme.ComposeLemonadeTheme
import com.rasyidcode.composelemonade.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLemonadeTheme {
//                ComposeLemonadeApp()
//                ComposeLemonadeAppSolution()
                ComposeLemonadeAppSolution2()
            }
        }
    }
}

// My Solution
@Composable
fun LemonadeItemCard(
    modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount = 0

    val stepInst: Int
    val stepImage: Int
    val stepDesc: Int

    when (currentStep) {
        1 -> {
            stepInst = R.string.tree_text
            stepImage = R.drawable.lemon_tree
            stepDesc = R.string.img_desc_tree
        }

        2 -> {
            stepInst = R.string.squeeze_text
            stepImage = R.drawable.lemon_squeeze
            stepDesc = R.string.img_desc_squeeze
        }

        3 -> {
            stepInst = R.string.drink_text
            stepImage = R.drawable.lemon_drink
            stepDesc = R.string.img_desc_drink
        }

        else -> {
            stepInst = R.string.restart_text
            stepImage = R.drawable.lemon_restart
            stepDesc = R.string.img_desc_restart
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(250.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Purple80)
            .wrapContentSize(align = Alignment.Center)
            .clickable {
                if (currentStep < 4) {
                    if (currentStep == 2 && squeezeCount < (1..4).random()) {
                        squeezeCount++
                    } else {
                        currentStep++
                    }
                } else {
                    currentStep = 1
                }
            }
        ) {
            Image(
                painter = painterResource(id = stepImage),
                contentDescription = stringResource(id = stepDesc),
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = stringResource(id = stepInst),
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun AppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Purple80)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLemonadeApp() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            AppBar()

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LemonadeItemCard()
            }
        }
    }
}

// Solution 1
@Preview(showBackground = true)
@Composable
fun ComposeLemonadeAppSolution() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var currentStep by remember { mutableStateOf(1) }

        when(currentStep) {
            1 -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.tree_text))
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lemon_tree),
                        contentDescription = stringResource(id = R.string.img_desc_tree),
                        modifier = Modifier.clickable {
                            currentStep = 2
                        }
                    )
                }
            }
            2 -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.squeeze_text))
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lemon_squeeze),
                        contentDescription = stringResource(id = R.string.img_desc_squeeze),
                        modifier = Modifier.clickable {
                            currentStep = 3
                        }
                    )
                }
            }
            3 -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.drink_text))
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lemon_drink),
                        contentDescription = stringResource(id = R.string.img_desc_drink),
                        modifier = Modifier.clickable {
                            currentStep = 4
                        }
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.restart_text))
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lemon_restart),
                        contentDescription = stringResource(id = R.string.img_desc_restart),
                        modifier = Modifier.clickable {
                            currentStep = 1
                        }
                    )
                }
            }
        }
    }
}

// Solution 2
@Composable
fun LemonTextAndImage(
    textRes: Int,
    imageRes: Int,
    imageDescRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = textRes))
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(id = imageDescRes),
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLemonadeAppSolution2() {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var currentStep by remember { mutableStateOf(1) }
        var squeezeCount = 0

        when(currentStep) {
            1 -> {
                LemonTextAndImage(
                    textRes = R.string.tree_text,
                    imageRes = R.drawable.lemon_tree,
                    imageDescRes = R.string.img_desc_tree
                ) {
                    currentStep = 2
                }
            }
            2 -> {
                LemonTextAndImage(
                    textRes = R.string.squeeze_text,
                    imageRes = R.drawable.lemon_squeeze,
                    imageDescRes = R.string.img_desc_squeeze
                ) {
                    if (squeezeCount < (2..4).random()) {
                        squeezeCount++
                    } else {
                        currentStep = 3
                    }
                }
            }
            3 -> {
                LemonTextAndImage(
                    textRes = R.string.drink_text,
                    imageRes = R.drawable.lemon_drink,
                    imageDescRes = R.string.img_desc_drink
                ) {
                    currentStep = 4
                }
            }
            else -> {
                LemonTextAndImage(
                    textRes = R.string.restart_text,
                    imageRes = R.drawable.lemon_restart,
                    imageDescRes = R.string.img_desc_restart
                ) {
                    currentStep = 1
                }
            }
        }
    }
}