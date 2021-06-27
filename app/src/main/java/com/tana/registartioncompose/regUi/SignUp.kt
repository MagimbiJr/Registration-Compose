package com.tana.registartioncompose.regUi


import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.tana.registartioncompose.R

@Composable
fun SignUpScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(MaterialTheme.colors.background)
    val (name, setName) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    val (visible, setVisibility) = remember { mutableStateOf(false) }
    val toggleIcon by remember { mutableStateOf(Icons.Default.VisibilityOff) }

    val registerUser: () -> Unit = {
        val auth = FirebaseAuth.getInstance()
        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "User registered", Toast.LENGTH_SHORT).show()
                        navController.navigate("sign in")
                        Log.d(TAG, "SignUpScreen: ${name.toString()} ${email.toString()} ${password.toString()} ${confirmPassword.toString()} ")
                    }
                }).addOnFailureListener(OnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "SignUpScreen: ${name.toString()} ${email.toString()} ${password.toString()} ${confirmPassword.toString()} ")
        }
    }

    val handlePasswordVisibility = {
        passwordVisibility = !passwordVisibility
    }

    val handleConfirmPasswordVisibility = {
        confirmPasswordVisibility = !confirmPasswordVisibility
    }

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            SignUpTitle()
            Spacer(modifier = modifier.padding(10.dp))
            RegForm(
                name = name, onNameChange = setName,
                email = email, onEmailChange = setEmail,
                password = password, onPasswordChange = setPassword,
                passwordVisibility = passwordVisibility,
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = setConfirmPassword,
                confirmPasswordVisibility = confirmPasswordVisibility,
                togglePasswordClick = handlePasswordVisibility,
                toggleConfirmPassword = handleConfirmPasswordVisibility
            )
        }
        RegButton(registerUser = registerUser)
        Spacer(modifier = modifier.padding(4.dp))
        ToSignIn(navController)
    }
}

@Composable
fun ToSignIn(navController: NavHostController) {
    Text(
        text = "Sign in",
        modifier = Modifier.clickable { navController.navigate("sign in") },
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
    )
}

@Composable
fun RegButton(registerUser: () -> Unit) {
    Button(
        onClick = registerUser,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
    ) {
        Text(
            text = "Register",
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            style = TextStyle(
                fontSize = 19.sp
            )
        )
    }
}

@Composable
fun RegForm(
    name: String, onNameChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    password: String, onPasswordChange: (String) -> Unit,
    passwordVisibility: Boolean,
    confirmPasswordVisibility: Boolean,
    confirmPassword: String, onConfirmPasswordChange: (String) -> Unit,
    togglePasswordClick: () -> Unit,
    toggleConfirmPassword: () -> Unit
) {
    OutlinedTextField(
        value = name, onValueChange = onNameChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Full Name") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
    )
    Spacer(modifier = Modifier.padding(4.dp))
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
            if (passwordVisibility)
                IconButton(onClick = togglePasswordClick) {
                    Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
                }
            else IconButton(onClick = togglePasswordClick) {
                Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
    Spacer(modifier = Modifier.padding(4.dp))
    OutlinedTextField(
        value = confirmPassword, onValueChange = onConfirmPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Confirm Password") },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            if (confirmPasswordVisibility) IconButton(onClick = toggleConfirmPassword) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null
                )
            }
            else IconButton(onClick = toggleConfirmPassword) {
                Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = null)
            }
        },
        visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(
            id = R.drawable.lion_logo
        ), contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun SignUpTitle() {
    Text(
        text = "Sign up",
        style = TextStyle(
            fontSize = 38.sp,
            fontWeight = FontWeight.W900,
            textAlign = TextAlign.Center
        )
    )
}