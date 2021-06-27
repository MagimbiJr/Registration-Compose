package com.tana.registartioncompose.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.White)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ComposeAuth") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                },
                backgroundColor = Color.White,
                actions = {
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate("sign in")
                            }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome back",
                style = MaterialTheme.typography.h4
            )
        }
    }
}