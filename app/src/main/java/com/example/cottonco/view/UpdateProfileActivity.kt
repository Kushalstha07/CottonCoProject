package com.example.cottonco.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cottonco.repository.UserRepositoryImpl
import com.example.cottonco.viewModel.UserViewModel

class UpdateProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpdateProfileBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileBody() {
    val context = LocalContext.current
    val activity = context as? Activity
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }
    val firebaseUser = userViewModel.getCurrentUser()
    val userLive = userViewModel.users.observeAsState(initial = null)
    var isLoading by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }

    // Editable fields
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedOptionText by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Fetch user info on launch
    LaunchedEffect(firebaseUser) {
        firebaseUser?.uid?.let { userViewModel.getUserById(it) }
    }
    // Set fields when userLive updates
    LaunchedEffect(userLive.value) {
        userLive.value?.let { user ->
            firstName = user.firstName
            lastName = user.lastName
            gender = user.gender
            address = user.address
            selectedOptionText = user.selectedOptionText
            email = user.email
            isLoading = false
        }
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFFADA),
            Color(0xFFFFF8E1),
            Color(0xFFFFFEF7)
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
                        color = Color(0xFF8B4513),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFADA)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .widthIn(max = 340.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                if (isLoading) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color(0xFF8B4513))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading profile...", color = Color(0xFF8B4513))
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Email (not editable)
                        OutlinedTextField(
                            value = email,
                            onValueChange = {},
                            label = { Text("Email") },
                            enabled = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color(0xFF8B4513),
                                disabledBorderColor = Color(0xFFD2691E),
                                disabledLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // First Name
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("First Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Last Name
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Gender
                        OutlinedTextField(
                            value = gender,
                            onValueChange = { gender = it },
                            label = { Text("Gender") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // Address
                        OutlinedTextField(
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // selectedOptionText
                        OutlinedTextField(
                            value = selectedOptionText,
                            onValueChange = { selectedOptionText = it },
                            label = { Text("Other Info") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B4513),
                                unfocusedBorderColor = Color(0xFFD2691E),
                                focusedLabelColor = Color(0xFF8B4513),
                                unfocusedLabelColor = Color(0xFFD2691E)
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        // Save Button
                        Button(
                            onClick = {
                                isSaving = true
                                val data = mutableMapOf<String, Any?>()
                                data["firstName"] = firstName
                                data["lastName"] = lastName
                                data["gender"] = gender
                                data["address"] = address
                                data["selectedOptionText"] = selectedOptionText
                                firebaseUser?.uid?.let { uid ->
                                    userViewModel.updataProfile(uid, data) { success, message ->
                                        isSaving = false
                                        if (success) {
                                            Toast.makeText(context, "Profile updated!", Toast.LENGTH_SHORT).show()
                                            activity?.finish()
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B4513)
                            ),
                            enabled = !isSaving
                        ) {
                            if (isSaving) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    text = "Save Changes",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}