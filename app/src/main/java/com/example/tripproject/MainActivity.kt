package com.example.tripproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripproject.models.Moneda
import com.example.tripproject.ui.theme.TealLight
import com.example.tripproject.ui.theme.TripProjectTheme
import retrofit2.Response
import retrofit2.Callback
import retrofit2.Call

class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripProjectTheme {

                val navController = rememberNavController()

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

val robotoFontFamily = fontFamily(GoogleFont("Roboto"))

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
                        painter = painterResource(id = R.drawable.logo_app),
                        contentDescription = "Logo app",
                        modifier = Modifier.size(70.dp).weight(1f)
                    )
                    Text(
                        text = stringResource(id = R.string.topbar_title),
                        style = TextStyle(
                            fontFamily = robotoFontFamily
                        ),
                        modifier = Modifier
                            .weight(1f)
                            
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversorDivisasScreen (modifier: Modifier = Modifier) {
    // Variables para el select de la moneda
    var monedaSeleccionadaOrigen by remember { mutableStateOf<Moneda?>(null) }
    var monedaSeleccionadaDestino by remember { mutableStateOf<Moneda?>(null) }
    var expandidoOrigen by remember { mutableStateOf(false) }
    var expandidoDestino by remember { mutableStateOf(false) }

    // Sacamos todas las monedas almacenadas de la base de datos para ponerlas en el select
    val monedas = listOf(
        Moneda(1, "Euro", "EUR", "€"),
        Moneda(2, "Dólar", "USD", "$"),
        Moneda(3, "Peso Argentino", "ARS", "$")
    )

    // Variables para el input de la cantidad
    var cantidad by remember { mutableStateOf("") }

    // Variables de operaciones
    var cantidadConvertida by remember { mutableStateOf(0.0) }
    var tasaDeCambio by remember { mutableStateOf<Map<String, Float>>(emptyMap()) }

    // Función para obtener las tasas de cambio
    fun obtenerTasasDeCambio() {
        val apiKey = "8d20a8e45aa2f53e28213f28"
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(ExchangeRateApi::class.java)

        val monedaBase = monedaSeleccionadaOrigen?.codigo ?: return

        val call: Call<ExchangeRateResponse> = api.obtenerTasasDeCambio(apiKey = apiKey, monedaBase = monedaBase)

        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                Log.e("Holi", "$response")
                if (response.isSuccessful && response.body() != null) {
                    val rates = response.body()!!.rates
                    Log.e("Morreu", "${rates}")
                    tasaDeCambio = rates
                    Log.d("Tasas de cambio", "Tasas: $rates")
                } else {
                    Log.e("Tasas de cambio", "Error en la respuesta de la API")
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Log.e("Tasas de cambio", "Error en la conexión: ${t.message}")
            }
        })
    }

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

            // Input de moneda ORIGEN
            ExposedDropdownMenuBox(
                expanded = expandidoOrigen,
                onExpandedChange = { expandidoOrigen = it },
            ) {
                OutlinedTextField(
                    value = monedaSeleccionadaOrigen?.nombre ?: "Selecciona una moneda",
                    onValueChange = {},
                    label = { Text("Moneda origen") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    modifier = Modifier
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoOrigen,
                    onDismissRequest = { expandidoOrigen = false }
                ) {
                    monedas.forEach { moneda ->
                        DropdownMenuItem(
                            text = { Text(moneda.nombre) },
                            onClick = {
                                monedaSeleccionadaOrigen = moneda
                                expandidoOrigen = false
                                obtenerTasasDeCambio()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input de moneda DESTINO
            ExposedDropdownMenuBox(
                expanded = expandidoDestino,
                onExpandedChange = { expandidoDestino = it },
            ) {
                OutlinedTextField(
                    value = monedaSeleccionadaDestino?.nombre ?: "Selecciona una moneda",
                    onValueChange = {},
                    label = { Text("Moneda destino") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    modifier = Modifier
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandidoDestino,
                    onDismissRequest = { expandidoDestino = false }
                ) {
                    monedas.forEach { moneda ->
                        DropdownMenuItem(
                            text = { Text(moneda.nombre) },
                            onClick = {
                                monedaSeleccionadaDestino = moneda
                                expandidoDestino = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input para la cantidad a convertir
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

            // Botón de convertir
            Button(
                onClick = {
                    val cantidadNumerica = cantidad.toDoubleOrNull() ?: 0.0
                    if (monedaSeleccionadaOrigen != null &&
                        monedaSeleccionadaDestino != null &&
                        cantidadNumerica > 0
                        ) {
                        val tasa = tasaDeCambio[monedaSeleccionadaDestino?.codigo]
                        if (tasa != null) {
                            cantidadConvertida = cantidadNumerica * tasa
                        } else {
                            Log.e("Conversión", "Tasa de cambio no encontrada")
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0288D1)
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Convertir")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cantidadConvertida > 0) {
                Text(
                    text = "Resultado: $cantidadConvertida ${monedaSeleccionadaDestino?.simbolo}",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
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
fun MyApp (modifier: Modifier) {
    WelcomeScreen(modifier)
}


@Composable
fun MyTxt(text: String, modifier: Modifier) {
    Text(text = text)

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TripProjectTheme {
        TopBar()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    TripProjectTheme {
        val navController = rememberNavController()
        BottomBar(navController)
    }
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