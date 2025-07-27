package com.example.cottonco.view

import android.app.Activity
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cottonco.R
import com.example.cottonco.model.UserModel
import com.example.cottonco.repository.UserRepositoryImpl
import com.example.cottonco.viewModel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterBody()
        }
    }
}

@Composable
fun RegisterBody() {
    // Registration screen with user data collection
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    // Gradient background matching login page
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFADA), // Main cream color
            Color(0xFFFFF8E1), // Lighter cream
            Color(0xFFFFFEF7)  // Very light cream
        )
    )

    Scaffold { padding ->
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
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo with enhanced styling
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF8B4513)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.cottoncologo),
                            contentDescription = "CottonCo Logo",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Welcome text
                    Text(
                        text = "Create Account",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Join us and start your journey",
                        fontSize = 16.sp,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Name fields row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(
                                    text = "First Name",
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
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFFD2691E),
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(
                                    text = "Last Name",
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
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFFD2691E),
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email field
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
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
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Country Dropdown
                    var expanded by remember { mutableStateOf(false) }
                    val countries = listOf("Nepal", "India", "UK", "USA", "Other")

                    Box {
                        OutlinedTextField(
                            value = selectedCountry,
                            onValueChange = { selectedCountry = it },
                            placeholder = {
                                Text(
                                    text = "Select Country",
                                    color = Color(0xFF999999)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
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
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color(0xFFD2691E),
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Country",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { expanded = true },
                                    tint = Color(0xFFD2691E)
                                )
                            },
                            readOnly = true,
                            singleLine = true
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color.White)
                        ) {
                            countries.forEach { country ->
                                DropdownMenuItem(
                                    text = { Text(country) },
                                    onClick = {
                                        selectedCountry = country
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Date of Birth field
                    OutlinedTextField(
                        value = dob,
                        onValueChange = { dob = it },
                        placeholder = {
                            Text(
                                text = "Date of Birth (DD/MM/YYYY)",
                                color = Color(0xFF999999)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
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
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Color(0xFFD2691E),
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = {
                            Text(
                                text = "Create password",
                                color = Color(0xFF999999)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
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
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Gender selection
                    Text(
                        text = "Gender",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF8B4513),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = gender == "Male",
                                onClick = { gender = "Male" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFD2691E),
                                    unselectedColor = Color(0xFFD2B48C)
                                )
                            )
                            Text(
                                "Male",
                                color = Color(0xFF654321),
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = gender == "Female",
                                onClick = { gender = "Female" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFD2691E),
                                    unselectedColor = Color(0xFFD2B48C)
                                )
                            )
                            Text(
                                "Female",
                                color = Color(0xFF654321),
                                fontSize = 14.sp
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = gender == "Other",
                                onClick = { gender = "Other" },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFD2691E),
                                    unselectedColor = Color(0xFFD2B48C)
                                )
                            )
                            Text(
                                "Other",
                                color = Color(0xFF654321),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Terms and conditions
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = acceptedTerms,
                            onCheckedChange = { acceptedTerms = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFD2691E),
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            "I accept terms and conditions",
                            color = Color(0xFF654321),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Register button
                    Button(
                        onClick = {
                            userViewModel.register(email, password) { success, message, userId ->
                                if (success) {
                                    var userModel = UserModel(
                                        userId, email, firstName, lastName,
                                        gender, "98000000", selectedCountry
                                    )

                                    userViewModel.addUsertoDatabase(userId, userModel) { success, message ->
                                        if (success) {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                        }
                                    }
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
                            text = "Create Account",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign in link
                    TextButton(
                        onClick = {
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFFD2691E)
                        )
                    ) {
                        Text(
                            text = "Already have an account? Sign In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegister() {
    RegisterBody()
}
