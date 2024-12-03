package com.example.tripproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
import com.example.tripproject.monedas

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
    val monedas = monedas

    // Variables para el input de la cantidad
    var cantidad by remember { mutableStateOf("") }

    // Variables de operaciones
    var cantidadConvertida by remember { mutableDoubleStateOf(0.0) }

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
                } else {
                    cantidadConvertida = 0.0
                    val errorMessage = when(response.code()) {
                        400 -> "Solicitud inválida. Verifica los datos."
                        401 -> "No autorizado. Verifica tus credenciales."
                        404 -> "El servicio de tasas de cambio no está disponible."
                        500 -> "Error interno del servidor. Inténtalo más tarde."
                        else -> "Ocurrió un error inesperado. Intenta nuevamente."
                    }
                    Toast.makeText(null, errorMessage, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Toast.makeText(null, R.string.common_error, Toast.LENGTH_LONG).show()
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
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.conversor_divisas_alt),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                // Input de moneda ORIGEN
                ExposedDropdownMenuBox(
                    expanded = expandidoOrigen,
                    onExpandedChange = { expandidoOrigen = it },
                ) {
                    OutlinedTextField(
                        value = monedaSeleccionadaOrigen?.nombre ?: stringResource(R.string.selecciona_moneda),
                        onValueChange = {},
                        label = { Text(stringResource(R.string.moneda_origen_input)) },
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
                        value = monedaSeleccionadaDestino?.nombre ?: stringResource(R.string.selecciona_moneda),
                        onValueChange = {},
                        label = { Text(stringResource(R.string.moneda_destino_input)) },
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
                    label = { Text(stringResource(R.string.cantidad_a_convertir)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.cantidad)) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "€")
                    },
                    singleLine = true
                )

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
                    Text(stringResource(R.string.boton_calcular))
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (cantidadConvertida > 0) {
                    val cantidadConvertidaFormateada = String.format("%.2f", cantidadConvertida)

                    Text(
                        text = stringResource(R.string.mensaje_convierte),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${cantidad} ${monedaSeleccionadaOrigen?.simbolo} son",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "$cantidadConvertidaFormateada ${monedaSeleccionadaDestino?.simbolo}",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}