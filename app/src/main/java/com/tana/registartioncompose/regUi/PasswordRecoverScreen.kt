package com.tana.registartioncompose.regUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tana.registartioncompose.ui.theme.RegistartionComposeTheme


@Composable
fun PasswordRecoverScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 30.dp, bottom = 30.dp, start = 32.dp, end = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = modifier.weight(1f)) {
            Text(
                text = "Please enter your email so we can send you OTP",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            )
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedTextField(
                value = "", onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.MailOutline, contentDescription = null) }
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
        ) {
            Text(
                text = "Send OTP",
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                style = TextStyle(
                    fontSize = 19.sp
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    RegistartionComposeTheme {
        val navController = rememberNavController()
        PasswordRecoverScreen(navController = navController)
    }
}