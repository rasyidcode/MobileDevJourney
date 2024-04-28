package com.rasyidcode.funfacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeScreen(
    name: String?,
    animal: String?,
    fact: String?
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar(text = "Welcome $name \uD83D\uDE0D")

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Thank You! for sharing your data",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (animal == "cat") {
                Text(
                    text = "You are a Cat Lover \uD83D\uDC31",
                    style = MaterialTheme.typography.displaySmall.copy(
                        shadow = Shadow(Utils.generateRandomColor(), Offset(x = 1f, y = 2f), 2f),
                        fontSize = 24.sp
                    )
                )
            } else {
                Text(
                    text = "You are a Dog Lover \uD83D\uDC36",
                    style = MaterialTheme.typography.displaySmall.copy(
                        shadow = Shadow(Utils.generateRandomColor(), Offset(x = 1f, y = 2f), 2f),
                        fontSize = 24.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    Modifier.padding(18.dp, 24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_quote),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (fact != null) {
                        Text(
                            text = fact,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.ic_quote),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen("John", "cat", "Cat is likeable")
}