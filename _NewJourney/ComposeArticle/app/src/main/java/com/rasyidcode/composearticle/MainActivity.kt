package com.rasyidcode.composearticle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.composearticle.ui.theme.ComposeArticleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeArticleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ArticleView()
                    ComposeArticleApp()
                }
            }
        }
    }
}

// My code
@Composable
fun ArticleView(modifier: Modifier = Modifier) {
    Column(modifier) {
        Image(
            painter = painterResource(id = R.drawable.bg_compose_background),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.title_text),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = stringResource(R.string.body1_text),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(R.string.body2_text),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeArticleTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ArticleView()
        }
    }
}
// -- End My code

// Solution code
@Composable
fun ComposeArticleApp() {
    ArticleCard(
        title = stringResource(id = R.string.title_text),
        shortDescription = stringResource(id = R.string.body1_text),
        longDescription = stringResource(id = R.string.body2_text),
        imagePainter = painterResource(id = R.drawable.bg_compose_background)
    )
}

@Composable
fun ArticleCard(
    title: String,
    shortDescription: String,
    longDescription: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null
        )
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = shortDescription,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = longDescription,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeArticleAppPreview() {
    ComposeArticleTheme {
        ComposeArticleApp()
    }
}
// -- End Solution code

