package com.seoulmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.seoulmate.ui.theme.SeoulMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@Serializable
object Splash
@Serializable
object Home

@Composable
fun SeoulMateNavHost(
    modifier: Modifier,
    navController: NavHostController,
) {

    NavHost(
        navController = navController,
        startDestination = Splash,
        modifier = modifier) {
        composable<Splash> {
//            val viewModel = hiltViewModel<MainViewModel>()
//            viewModel.onEvents(MainEvents.GetAddresses("태평로1가 31"))
            SplashScreen()
        }
        composable<Home> {
            HomeScreen()
        }
    }

}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SeoulMateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.error
                ) {
                    Mainbody()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    SeoulMateTheme {
        Mainbody()
    }
}

@Composable
fun Mainbody() {
    val navController = rememberNavController()
    Column {
        SeoulMateNavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    navController.navigate(route = Home)
                }
            ) {
                Text(text = "HOME")
            }
            Button(
                onClick = {
                    navController.navigate(route = Splash)
                }
            ) {
                Text(text = "SPLASH")
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Text(text = "Splash Screen")
    }
}

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(text = "Home Screen")
    }
}