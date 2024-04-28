package com.rasyidcode.funfacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UserInputScreen(
    userInputViewModel: UserInputViewModel,
    onNavigateToWelcomeScreen: (Pair<String, String>) -> Unit
) {
    var nameInput by remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopBar("Hi there \uD83D\uDE0A")

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Let's learn about you!",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "This app will prepare a details page based on input provided by you!",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(text = "Name")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nameInput,
                onValueChange = {
                    nameInput = it
                    userInputViewModel.setName(it)
                },
                placeholder = { Text(text = "Enter your name") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    localFocusManager.clearFocus()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "What do you like")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PolygonImage(
                    imageId = R.drawable.cat,
                    imageDesc = "Cool cat",
                    modifier = Modifier.clickable {
                        userInputViewModel.setAnimal("cat")
                        localFocusManager.clearFocus()
                    },
                    selected = userInputViewModel.animal == "cat"
                )
                PolygonImage(
                    imageId = R.drawable.dog,
                    imageDesc = "Happy dog",
                    modifier = Modifier.clickable {
                        userInputViewModel.setAnimal("dog")
                        localFocusManager.clearFocus()
                    },
                    selected = userInputViewModel.animal == "dog"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (userInputViewModel.isValidFormInputs()) {
                Button(onClick = {
                    onNavigateToWelcomeScreen(
                        Pair(
                            userInputViewModel.name,
                            userInputViewModel.animal
                        )
                    )
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Go to Detail")
                }
            }

        }
    }
}

@Preview
@Composable
fun UserInputScreenPreview() {
    UserInputScreen(UserInputViewModel()) {}
}