package com.example.jobmatchai.Screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jobmatchai.Navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navHostController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(1000)) + slideInVertically(initialOffsetY = { it / 2 }),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBBDEFB))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium,
                textStyle = TextStyle(fontSize = 16.sp),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium,
                textStyle = TextStyle(fontSize = 16.sp),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(30.dp))

            var buttonPressed by remember { mutableStateOf(false) }

            AnimatedVisibility(
                visible = buttonPressed,
                enter = scaleIn(animationSpec = tween(300))
            ) {
                Text("Processing...", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
            }

            ElevatedButton(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please provide all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        buttonPressed = true
                        navHostController.navigate(Routes.BottomNav.routes) {
                            popUpTo(Routes.Login.routes) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 20.sp),
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                onClick = {
                    navHostController.navigate(Routes.Register.routes) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            ) {
                Text(
                    text = "New User? Create Your Account",
                    style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                )
            }
        }
    }
}