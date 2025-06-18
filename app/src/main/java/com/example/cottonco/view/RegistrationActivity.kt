package com.example.cottonco.view



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
// State and Composables
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable

// UI elements
import androidx.compose.material3.*

import androidx.compose.material3.RadioButton
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

// Styling
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.cottonco.model.UserModel
import com.example.cottonco.repository.UserRepositoryImpl
import com.example.cottonco.viewModel.UserViewModel


class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { padding ->
                RegisterBody(padding)
            }

        }
    }
}

@Composable
fun RegisterBody(paddingValues: PaddingValues) {

    val repo= remember { UserRepositoryImpl() }
    val userViewModel= remember { UserViewModel(repo) }

    val context= LocalContext.current
    val activity= context as? Activity

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedCountry by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var acceptedTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Register", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OutlinedTextField(
                value = firstName, onValueChange = { firstName = it },
                label = { Text("Firstname") },
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            )
            OutlinedTextField(
                value = lastName, onValueChange = { lastName = it },
                label = { Text("Lastname") },
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            )
        }

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        // Country Dropdown
        var expanded by remember { mutableStateOf(false) }
        val countries = listOf("Nepal", "India", "UK", "USA", "Other")

        Box {
            OutlinedTextField(
                value = selectedCountry,
                onValueChange = { selectedCountry = it },
                label = { Text("Select Country") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null,
                        Modifier.clickable { expanded = true })
                },
                readOnly = true
            )
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                countries.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = {
                        selectedCountry = it
                        expanded = false
                    })
                }
            }
        }

        OutlinedTextField(
            value = dob, onValueChange = { dob = it },
            label = { Text("DOB") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Date picker") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )


        Text("Gender")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
            Text("Male", modifier = Modifier.padding(end = 8.dp))

            RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
            Text("Female", modifier = Modifier.padding(end = 8.dp))

            RadioButton(selected = gender == "Other", onClick = { gender = "Other" })
            Text("Others")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = acceptedTerms, onCheckedChange = { acceptedTerms = it })
            Text("I accept terms and conditions", modifier = Modifier.padding(start = 4.dp))
        }

        Button(
            onClick = {
                userViewModel.register(email, password){
                        success,message,userId->
                    if(success){
                        var userModel= UserModel(
                            userId, email, firstName, lastName,
                            gender, "98000000",selectedCountry
                        )

                        userViewModel.addUsertoDatabase(userId,userModel){
                                success,message->
                            if(success){
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show()

                            }else{
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Register")
        }

        Text(text = "Already have an account? Sign In",
            modifier = Modifier.clickable {
                val intent = Intent(context,LoginActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            })


    }
}



@Preview(showBackground = true)
@Composable
fun PreviewRegister(){
    RegisterBody(paddingValues = PaddingValues(0.dp))
}
