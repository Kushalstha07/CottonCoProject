package com.example.cottonco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cottonco.ui.theme.CottonCoTheme

class SplashAcitivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashAcitivityBody();

        }
    }
}

@Composable
fun SplashAcitivityBody(){

    Scaffold {
        innerPadding->
        LazyColumn {  }
    }

}

@Preview
@Composable

fun PreviewSplashActivity(){
    SplashAcitivityBody();
}

