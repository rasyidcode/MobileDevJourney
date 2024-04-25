package com.rasyidcode.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasyidcode.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BusinessCardApp()
                }
            }
        }
    }
}

@Composable
fun BusinessCardApp() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color(0xFFDBAFA0))
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.android_logo),
                contentDescription = null,
                modifier = Modifier
                    .background(Color(0xFF49243E))
                    .width(90.dp)
                    .height(90.dp),
            )
            Text(
                text = "Ahmad Jamil Al Rasyid",
                fontSize = 32.sp,
                letterSpacing = 4.sp,
                lineHeight = 32.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Android Developer",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF49243E),
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(Modifier.padding(vertical = 32.dp)) {
            Row(Modifier.padding(bottom = 8.dp)) {
                Icon(Icons.Rounded.Phone, contentDescription = null, tint = Color(0xFF49243E))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "+62 812 2683 6303")
            }
            Row(Modifier.padding(bottom = 8.dp)) {
                Icon(Icons.Rounded.Share, contentDescription = null, tint = Color(0xFF49243E))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "@rasyidcode")
            }
            Row {
                Icon(Icons.Rounded.Email, contentDescription = null, tint = Color(0xFF49243E))
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "rasyidcode@gmail.com")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardAppPreview() {
    BusinessCardTheme {
        BusinessCardApp()
    }
}