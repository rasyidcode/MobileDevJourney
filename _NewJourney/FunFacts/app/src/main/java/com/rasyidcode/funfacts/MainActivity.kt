package com.rasyidcode.funfacts

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rasyidcode.funfacts.ui.theme.FunFactsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            FunFactsTheme {
                FunFactsApp()
            }
        }
    }
}

@Composable
fun FunFactsApp() {
    val tag = "FunFactsApp"

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.USER_INPUT_SCREEN) {

        composable(Routes.USER_INPUT_SCREEN) {
            UserInputScreen(UserInputViewModel()) {
                Log.d(tag, "first: ${it.first}, ${it.second}")
                navController.navigate("${Routes.WELCOME_SCREEN}/${it.first}/${it.second}")
            }
        }


        composable("${Routes.WELCOME_SCREEN}/{${Routes.ARG_NAME}}/{${Routes.ARG_ANIMAL}}",
            arguments = listOf(
                navArgument(name = Routes.ARG_NAME) {
                    type = NavType.StringType
                },
                navArgument(name = Routes.ARG_ANIMAL) {
                    type = NavType.StringType
                }
            )) {
            val welcomeViewModel = WelcomeViewModel()
            val name = it.arguments?.getString(Routes.ARG_NAME)
            val animal = it.arguments?.getString(Routes.ARG_ANIMAL)
            val fact = if (animal == "cat") welcomeViewModel.getCatFacts()
                .random() else welcomeViewModel.getDogFacts().random()
            WelcomeScreen(name, animal, fact)
        }
    }
}