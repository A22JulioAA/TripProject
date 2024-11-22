package com.example.tripproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tripproject.ui.theme.TripProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary
                        ),
                        title = {
                            Text("Topbar")
                        }
                    )
                }) { innerPadding ->
                    MyApp (Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen (modifier: Modifier = Modifier) {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray) {
            Text(
                text = "Bienvenido a mi app",
                modifier = Modifier.padding(16.dp)
            )
    }
}

@Composable
fun  MyApp (modifier: Modifier) {
    WelcomeScreen(modifier)
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    TripProjectTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}