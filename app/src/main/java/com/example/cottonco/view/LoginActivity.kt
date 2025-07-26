package com.example.cottonco.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cottonco.R
import com.example.cottonco.repository.UserRepositoryImpl
import com.example.cottonco.viewModel.UserViewModel
import com.example.cottonco.view.RegistrationActivity
import com.example.cottonco.view.Homepage

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberme by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val activity = context as Activity

    val couroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }

    // Gradient background
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFADA), // Main cream color
            Color(0xFFFFF8E1), // Lighter cream
            Color(0xFFFFFEF7)  // Very light cream
        )
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo with enhanced styling
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF8B4513)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.cottoncologo),
                            contentDescription = "CottonCo Logo",
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Welcome text
                    Text(
                        text = "Welcome Back!",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue your journey",
                        fontSize = 16.sp,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email field with enhanced styling
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        placeholder = {
                            Text(
                                text = "Enter your email",
                                color = Color(0xFF999999)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFFFADA),
                            focusedIndicatorColor = Color(0xFFD2691E),
                            unfocusedContainerColor = Color(0xFFFFFADA),
                            unfocusedIndicatorColor = Color(0xFFD2B48C),
                            focusedTextColor = Color(0xFF8B4513),
                            unfocusedTextColor = Color(0xFF654321)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        prefix = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Color(0xFFD2691E),
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Password field with enhanced styling
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        placeholder = {
                            Text(
                                text = "Enter your password",
                                color = Color(0xFF999999)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFFFADA),
                            focusedIndicatorColor = Color(0xFFD2691E),
                            unfocusedContainerColor = Color(0xFFFFFADA),
                            unfocusedIndicatorColor = Color(0xFFD2B48C),
                            focusedTextColor = Color(0xFF8B4513),
                            unfocusedTextColor = Color(0xFF654321)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        prefix = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color(0xFFD2691E),
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        suffix = {
                            Icon(
                                painterResource(
                                    if (passwordVisibility) R.drawable.baseline_visibility_24 
                                    else R.drawable.baseline_visibility_off_24
                                ),
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { passwordVisibility = !passwordVisibility },
                                tint = Color(0xFFD2691E)
                            )
                        },
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Remember me and Forgot password row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFFD2691E),
                                    checkmarkColor = Color.White
                                ),
                                checked = rememberme,
                                onCheckedChange = { rememberme = it }
                            )
                            Text(
                                text = "Remember me",
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                        }

                        TextButton(
                            onClick = { /* Handle forgot password */ },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFD2691E)
                            )
                        ) {
                            Text(
                                text = "Forgot password?",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Login button with enhanced styling
                    Button(
                        onClick = {
                            userViewModel.login(email, password) { success, message ->
                                if (success) {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    val intent = Intent(context, Homepage::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                } else {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8B4513)
                        )
                    ) {
                        Text(
                            text = "Sign In",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color(0xFFE0E0E0))
                        )
                        Text(
                            text = "OR",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(0xFF999999),
                            fontSize = 14.sp
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                                .background(Color(0xFFE0E0E0))
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Register button
                    Button(
                        onClick = {
                            val intent = Intent(context, RegistrationActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFFADA)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFF8B4513),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Create New Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF8B4513)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Exit button (styled as text button)
                    TextButton(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF999999)
                        )
                    ) {
                        Text(
                            text = "Exit App",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Exit confirmation dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = "Exit Application",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B4513)
                        )
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to exit the application?",
                            color = Color(0xFF666666)
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDialog = false
                                activity.finish()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B4513)
                            )
                        ) {
                            Text("Exit")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFD2691E)
                            )
                        ) {
                            Text("Cancel")
                        }
                    },
                    containerColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    LoginBody()
}