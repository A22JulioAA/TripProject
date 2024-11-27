package com.example.tripproject.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tripproject.ExchangeRateApi
import com.example.tripproject.ExchangeRateResponse
import com.example.tripproject.RetrofitClient
import com.example.tripproject.models.Moneda
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