package com.example.moviebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.moviebrowser.navigation.AppNavGraph
import com.example.moviebrowser.ui.theme.MovieBrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MovieBrowserTheme {
                val navController = rememberNavController()
                AppNavGraph(navController)
            }
        }
    }
}
