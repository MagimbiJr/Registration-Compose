package com.tana.registartioncompose.regUi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.tana.registartioncompose.ui.theme.RegistartionComposeTheme

@Composable
fun SignInScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val signinUser: () -> Unit = {
        val auth = FirebaseAuth.getInstance()
        if (email.isNotBlank() && password.isNotBlank()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    val togglePassword = {
        passwordVisibility = !passwordVisibility
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            SignInTitle()
            Spacer(modifier = modifier.padding(10.dp))
            SignInForm(
                email = email, onEmailChange = setEmail,
                password = password, onPasswordChange = setPassword,
                passwordVisibility = passwordVisibility,
                togglePassword = togglePassword
            )
            Spacer(modifier = modifier.padding(3.dp))
            ToPasswordRecovery(navController = navController)
        }
        SignInButton(onClick = signinUser)
        Spacer(modifier = modifier.padding(4.dp))
        ToRegister(navController = navController)
    }
}

@Composable
fun ToRegister(navController: NavHostController) {
    Text(
        text = "Register",
        modifier = Modifier.clickable { navController.navigate("register") },
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
    )
}

@Composable
fun SignInButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
    ) {
        Text(
            text = "Sign in",
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            style = TextStyle(
                fontSize = 19.sp
            )
        )
    }
}

@Composable
fun ToPasswordRecovery(navController: NavHostController, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Forgot Password?",
            modifier = modifier.clickable {
                navController.navigate("password recover")
            },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = Color(0xFFf3b544)
            )
        )
    }
}

@Composable
fun SignInForm(
    email: String, onEmailChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    passwordVisibility: Boolean,
    togglePassword: () -> Unit
) {
    OutlinedTextField(
        value = email, onValueChange = onEmailChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Email") },
        leadingIcon = { Icon(imageVector = Icons.Default.MailOutline, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
    Spacer(modifier = Modifier.padding(4.dp))
    OutlinedTextField(
        value = password, onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password") },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            if (passwordVisibility) IconButton(onClick = togglePassword) {
                Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
            } else IconButton(onClick = togglePassword) {
                Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun SignInTitle() {
    Text(
        text = "Sign in",
        style = TextStyle(
            fontSize = 38.sp,
            fontWeight = FontWeight.W900,
            textAlign = TextAlign.Center
        )
    )
}
