package com.example.tripproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripProjectTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { topBar() },
                    bottomBar = {
                        BottomAppBar (
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Home, contentDescription = "Hola")
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Hola")
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Check, contentDescription = "Hola")
                                }
                            },
                            floatingActionButton = {
                                FloatingActionButton(
                                    onClick = {},
                                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                                ) {
                                    Icon(Icons.Filled.Add, "Add supoño")
                                }
                            }
                        )
                    },
                    ) { innerPadding ->
                        MyApp (modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBar(){
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Icon(
                    Icons.Filled.Home, contentDescription = "Hola"
                )
            }
        )

}

@Composable
fun WelcomeScreen (modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.LightGray) {
            Text(
                text = "Bienvenido a mi app",
                modifier = Modifier.padding(100.dp)
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