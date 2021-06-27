package com.tana.registartioncompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.tana.registartioncompose.regUi.PasswordRecoverScreen
import com.tana.registartioncompose.regUi.SignInScreen
import com.tana.registartioncompose.regUi.SignUpScreen
import com.tana.registartioncompose.screens.HomeScreen
import com.tana.registartioncompose.ui.theme.RegistartionComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            RegistartionComposeTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App(navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
        NavHost(navController = navController, startDestination = "register") {
            composable("register") {
                SignUpScreen(navController = navController)
            }
            composable("sign in") {
                SignInScreen(navController = navController)
            }
            composable("password recover") {
                PasswordRecoverScreen(navController = navController)
            }
            composable("home") {
                HomeScreen(navController = navController)
            }
        }
    } else {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController = navController)
            }
            composable("sign in") {
                SignInScreen(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RegistartionComposeTheme {
        val navController = rememberNavController()
        App(navController)
    }
}