package com.example.cottonco.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cottonco.view.LoginActivity
import com.example.cottonco.view.Homepage
import com.example.cottonco.R
import kotlinx.coroutines.delay

class CottonCo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CottonCoBody()
        }
    }
}

@Composable
fun CottonCoBody(){
    val context= LocalContext.current
    val activity= context as Activity

//    val sharedPreferences= context.getSharedPreferences("User", Context.MODE_PRIVATE)
//
//    val localEmail=sharedPreferences.getString("email","").toString()

    LaunchedEffect(Unit) {
        delay(3000)

   //     if (localEmail.isEmpty()){
            val intent=Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
            activity.finish()
        //}
//        else{
//            val intent= Intent(context,Homepage::class.java)
//            context.startActivity(intent)
//            activity.finish()
//        }
    }

    Scaffold { padding->
        Column(
            modifier = Modifier.padding(padding).background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.cottoncologo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun PreviewCottonCoBody(){
    CottonCoBody()
}
