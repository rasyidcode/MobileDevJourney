package com.rasyidcode.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidcode.composequadrant.ui.theme.ComposeQuadrantTheme
import com.rasyidcode.composequadrant.ui.theme.PurpleA
import com.rasyidcode.composequadrant.ui.theme.PurpleB
import com.rasyidcode.composequadrant.ui.theme.PurpleC
import com.rasyidcode.composequadrant.ui.theme.PurpleD

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QuadrantView2()
                }
            }
        }
    }
}

@Composable
fun QuadrantBox(title: String, desc: String, bgColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(bgColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(desc, textAlign = TextAlign.Justify)
    }
}

@Composable
fun QuadrantBox2(title: String, desc: String, bgColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(desc, textAlign = TextAlign.Justify)
    }
}

@Composable
fun QuadrantView() {
    Column {
        Row(Modifier.fillMaxHeight(0.5f)) {
            QuadrantBox(
                title = stringResource(R.string.text_composable),
                desc = stringResource(R.string.text_composable_desc),
                bgColor = PurpleA,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.5f)
            )
            QuadrantBox(
                title = stringResource(R.string.image_composable),
                desc = stringResource(R.string.image_composable_desc),
                bgColor = PurpleB,
                modifier = Modifier.fillMaxHeight(1f)
            )
        }
        Row(
            Modifier
                .fillMaxHeight()
                .background(Color.Green)
        ) {
            QuadrantBox(
                title = stringResource(R.string.row_composable),
                desc = stringResource(R.string.row_composable_desc),
                bgColor = PurpleC,
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(0.5f)
            )
            QuadrantBox(
                title = stringResource(R.string.column_composable),
                desc = stringResource(R.string.column_composable_desc),
                bgColor = PurpleD,
                modifier = Modifier.fillMaxHeight(1f)
            )
        }
    }
}

@Composable
fun QuadrantView2() {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            QuadrantBox2(
                title = stringResource(R.string.text_composable),
                desc = stringResource(R.string.text_composable_desc),
                bgColor = PurpleA,
                modifier = Modifier.weight(1f)
            )
            QuadrantBox2(
                title = stringResource(R.string.image_composable),
                desc = stringResource(R.string.image_composable_desc),
                bgColor = PurpleB,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            Modifier
                .weight(1f)
        ) {
            QuadrantBox2(
                title = stringResource(R.string.row_composable),
                desc = stringResource(R.string.row_composable_desc),
                bgColor = PurpleC,
                modifier = Modifier
                    .weight(1f)
            )
            QuadrantBox2(
                title = stringResource(R.string.column_composable),
                desc = stringResource(R.string.column_composable_desc),
                bgColor = PurpleD,
                modifier = Modifier.weight(1f)
            )
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        QuadrantView2()
    }
}