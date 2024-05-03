package com.rasyidcode.artspace

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var artImage by remember { mutableIntStateOf(R.drawable.art) }
    var artText by remember { mutableIntStateOf(R.string.art_1_text) }
    var artPerson by remember { mutableIntStateOf(R.string.art_1_by) }
    var artYear by remember { mutableIntStateOf(R.string.art_1_year) }
    var currentArt by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Surface(
            shadowElevation = 8.dp,
            modifier = Modifier.widthIn(0.dp, 320.dp)
        ) {
            Image(
                painter = painterResource(id = artImage),
                contentDescription = stringResource(
                    id = R.string.photo_by,
                    stringResource(id = artPerson)
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth()
                    .height(380.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .widthIn(0.dp, 320.dp)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = artText),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Light
                )
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = artPerson))
                }
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
                    append(stringResource(id = R.string.year_text, stringResource(id = artYear)))
                }
            })

        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            Button(onClick = {
                when (currentArt) {
                    1 -> Toast.makeText(
                        context,
                        "This is the first art",
                        Toast.LENGTH_SHORT
                    ).show()

                    2 -> {
                        currentArt = 1
                        artImage = R.drawable.art
                        artText = R.string.art_1_text
                        artPerson = R.string.art_1_by
                        artYear = R.string.art_1_year
                    }

                    3 -> {
                        currentArt = 2
                        artImage = R.drawable.art2
                        artText = R.string.art_2_text
                        artPerson = R.string.art_2_by
                        artYear = R.string.art_2_year
                    }
                }
            }, modifier = Modifier.width(128.dp)) {
                Text(text = "Previous")
            }
            Button(onClick = {
                when (currentArt) {
                    1 -> {
                        currentArt = 2
                        artImage = R.drawable.art2
                        artText = R.string.art_2_text
                        artPerson = R.string.art_2_by
                        artYear = R.string.art_2_year
                    }

                    2 -> {
                        currentArt = 3
                        artImage = R.drawable.art3
                        artText = R.string.art_3_text
                        artPerson = R.string.art_3_by
                        artYear = R.string.art_3_year
                    }

                    3 -> Toast.makeText(
                        context,
                        "This is the last art",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, modifier = Modifier.width(128.dp)) {
                Text(text = "Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ArtSpaceApp()
        }
    }
}