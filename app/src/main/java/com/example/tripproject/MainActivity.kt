package com.example.tripproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripproject.ui.theme.TealLight
import com.example.tripproject.ui.theme.TripProjectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripProjectTheme {

                val  navController = rememberNavController()

                lifecycleScope.launch {
                    val database = DatabaseProvider.getDatabase(applicationContext)
                    val monedaDao = database.monedaDao()

                    val monedas = monedaDao.getMonedas()

                    monedas.forEach {
                        println("Moneda: ${it.nombre}")
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() },
                    bottomBar = { BottomBar(navController) },
                    content = { innerPadding ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                MyApp(modifier = Modifier.padding(innerPadding))
                            }
                            composable("conversorDivisasScreen") {
                                ConversorDivisasScreen(modifier = Modifier.padding(innerPadding))
                            }
                            composable("gastosViajeScreen") {
                                GastosViajeScreen(modifier = Modifier.padding(innerPadding))
                            }
                            composable("mapasYRutasScreen") {
                                MapasYRutasScreen(modifier = Modifier.padding(innerPadding))
                            }
                        }
                    }
                    )
            }
        }
    }
}

// Función para crear la top bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
        var expanded by remember { mutableStateOf(false) }

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Row (
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo app",
                        modifier = Modifier.size(48.dp).weight(1f)
                    )
                    Text(
                        text = stringResource(id = R.string.topbar_title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.user_icon),
                        contentDescription = "Imagen usuario",
                        modifier = Modifier
                            .size(32.dp)
                            .weight(1f)
                            .clickable { expanded = !expanded }
                    )

                }

            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {} , text = { MyTxt("1", Modifier.padding(15.dp)) })
            DropdownMenuItem(onClick = {} , text = { MyTxt("2", Modifier.padding(15.dp)) })
            DropdownMenuItem(onClick = {} , text = { MyTxt("3", Modifier.padding(15.dp)) })

        }
}

// Función para crear la bottom bar
@Composable
fun BottomBar (navController: NavController) {
    BottomAppBar (
    ) {
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.home_logo_bottombar),
                    contentDescription = "Home logo",
                    modifier = Modifier.size(64.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("gastosViajeScreen")},
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.car_logo_bottombar),
                    contentDescription = "Home logo",
                    modifier = Modifier.size(64.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("conversorDivisasScreen") },
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.dollar_logo_bottombar),
                    contentDescription = "Home logo",
                    modifier = Modifier.size(64.dp)
                )
            }
            IconButton(
                onClick = { navController.navigate("mapasYRutasScreen")},
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.mapa_logo_bottombar),
                    contentDescription = "Home logo",
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

// Función para crear la pantalla de inicio
@Composable
fun WelcomeScreen (modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = TealLight
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.van_background_welcome),
                contentDescription = "Van trip background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column (
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.userGreeting),
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text = stringResource(id = R.string.welcome_message),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// Función para crear la pantalla de conversión de divisas
@Composable
fun ConversorDivisasScreen (modifier: Modifier = Modifier) {
    // Variables para el select de la moneda
    val monedaSeleccionada by remember { mutableStateOf("") }
    val expandido by remember { mutableStateOf(false) }

    // Variables para el input de la cantidad
    var cantidad by remember { mutableStateOf("") }

    var cantidadConvertida by remember { mutableStateOf(0.0) }
    val ratioDeConversion = 1.2


    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Conversor de divisas",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )



            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    nuevaCantidad -> cantidad = nuevaCantidad
                },
                label = { Text("Cantidad a convertir") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Cantidad") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "€")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}

// Función para crear la pantalla de gastos de viaje
@Composable
fun GastosViajeScreen (modifier: Modifier = Modifier) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Hola gastos de viaje",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Función para crear la pantalla de mapas y rutas
@Composable
fun MapasYRutasScreen (modifier: Modifier = Modifier) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Hola mapas y rutas",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun  MyApp (modifier: Modifier) {
    WelcomeScreen(modifier)
}


@Composable
fun MyTxt(text: String, modifier: Modifier) {
    Text(text = text)

}
@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    TripProjectTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun ConversorDivisasPreview() {
    TripProjectTheme {
        ConversorDivisasScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GastosViajePreview() {
    TripProjectTheme {
        GastosViajeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MapasYRutasPreview() {
    TripProjectTheme {
        MapasYRutasScreen()
    }
}