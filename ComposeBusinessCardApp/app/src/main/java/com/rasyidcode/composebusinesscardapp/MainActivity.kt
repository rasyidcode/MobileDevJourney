package com.rasyidcode.composebusinesscardapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.composebusinesscardapp.ui.theme.ComposeBusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBusinessCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCardView()
                }
            }
        }
    }
}

@Composable
fun BusinessCardView() {
    Column {
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth(1f), verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.me_photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .clip(CircleShape)
                    .border(border = BorderStroke(2.dp, Color.DarkGray), shape = CircleShape)
            )
            Text(
                stringResource(R.string.text_name),
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
            Text(
                stringResource(R.string.text_title),
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth(1f)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            ContactInfoBox(
                icon = painterResource(id = R.drawable.ic_phone),
                text = stringResource(id = R.string.text_phone),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            ContactInfoBox(
                icon = painterResource(id = R.drawable.ic_social),
                text = stringResource(id = R.string.text_social),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            ContactInfoBox(
                icon = painterResource(id = R.drawable.ic_email),
                text = stringResource(id = R.string.text_email)
            )
        }
    }
}

@Composable
fun ContactInfoBox(icon: Painter, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth(1f),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .weight(0.5f)
        )
        Text(text, Modifier.weight(1f))
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
    ComposeBusinessCardAppTheme {
        BusinessCardView()
    }
}