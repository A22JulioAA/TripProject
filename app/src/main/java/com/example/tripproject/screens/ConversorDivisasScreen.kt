package com.example.tripproject.screens

import androidx.compose.foundation.shape.CircleShape
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripproject.ExchangeRateResponse
import com.example.tripproject.R
import com.example.tripproject.RetrofitClient
import com.example.tripproject.models.Moneda
import com.example.tripproject.retrofit.ExchangeRateApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        Moneda(2, "Dólar Estadounidense", "USD", "$"),
        Moneda(3, "Peso Argentino", "ARS", "$"),
        Moneda(4, "Libra Esterlina", "GBP", "£"),
        Moneda(5, "Yen Japonés", "JPY", "¥"),
        Moneda(6, "Dólar Canadiense", "CAD", "$"),
        Moneda(7, "Franco Suizo", "CHF", "CHF"),
        Moneda(8, "Dólar Australiano", "AUD", "$"),
        Moneda(9, "Real Brasileño", "BRL", "R$"),
        Moneda(10, "Peso Mexicano", "MXN", "$"),
        Moneda(11, "Yuan Chino", "CNY", "¥"),
        Moneda(12, "Won Surcoreano", "KRW", "₩"),
        Moneda(13, "Rupia India", "INR", "₹"),
        Moneda(14, "Rublo Ruso", "RUB", "₽"),
        Moneda(15, "Dólar de Nueva Zelanda", "NZD", "$"),
        Moneda(16, "Dólar Singapurense", "SGD", "$"),
        Moneda(17, "Rand Sudafricano", "ZAR", "R"),
        Moneda(18, "Lira Turca", "TRY", "₺"),
        Moneda(19, "Shekel Israelí", "ILS", "₪"),
        Moneda(20, "Peso Chileno", "CLP", "$"),
        Moneda(21, "Peso Colombiano", "COP", "$"),
        Moneda(22, "Córdoba Nicaragüense", "NIO", "C$"),
        Moneda(23, "Quetzal Guatemalteco", "GTQ", "Q")
    )

    // Variables para el input de la cantidad
    var cantidad by remember { mutableStateOf("") }

    // Variables de operaciones
    var cantidadConvertida by remember { mutableStateOf(0.0) }
    var tasaDeCambio by remember { mutableStateOf<Map<String, Float>>(emptyMap()) }

    // Función para obtener las tasas de cambio
    fun obtenerTasasDeCambio(monedaOrigen: String, monedaDestino: String, cantidad: Double) {
        val retrofit = RetrofitClient.getRetrofitInstance()
        val api = retrofit.create(ExchangeRateApi::class.java)

        val call: Call<ExchangeRateResponse> = api.obtenerTasaDeCambio(
            monedaOrigen,
            monedaDestino,
            cantidad
        )

        call.enqueue(object : Callback<ExchangeRateResponse> {
            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val resultado = response.body()!!.result
                    cantidadConvertida = resultado
                    Log.d("Tasa de cambio", "Resultado: $resultado")
                } else {
                    Log.e("Tasa de cambio", "Error en la respuesta")
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
                .verticalScroll(rememberScrollState()),
        ) {
            // Imagen como una franja en la parte superior
            Image(
                painter = painterResource(id = R.drawable.cambio_divisas),
                contentDescription = "Cambio divisas",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
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
                    onValueChange = { nuevaCantidad ->
                        cantidad = nuevaCantidad
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
                            obtenerTasasDeCambio(
                                monedaSeleccionadaOrigen?.codigo ?: "",
                                monedaSeleccionadaDestino?.codigo ?: "",
                                cantidadNumerica
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF63A002)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Convertir")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (cantidadConvertida > 0) {
                    val cantidadConvertidaFormateada = String.format("%.2f", cantidadConvertida)
                    Text(
                        text = "Resultado: $cantidadConvertidaFormateada ${monedaSeleccionadaDestino?.simbolo}",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(Color(0xFF63A002))
                            .padding(20.dp)
                            .border(2.dp, Color(0xFF63A002))
                            .clickable { },
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    )
                }
            }
        }
    }
}