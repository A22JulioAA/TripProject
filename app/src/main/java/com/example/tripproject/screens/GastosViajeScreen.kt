package com.example.tripproject.screens

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.tripproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GastosViajeScreen(modifier: Modifier = Modifier) {
    // Variables de los campos del formulario
    var kilometros by remember { mutableStateOf("") }
    var tipoCombustible by remember { mutableStateOf("Seleccionar tipo combustible") }
    var expandedCombustible by remember { mutableStateOf(false) }
    var precioCombustible by remember { mutableStateOf("") }
    var consumoCombustible by remember { mutableStateOf("Seleccionar consumo de combustible") }
    var expandedConsumo by remember { mutableStateOf(false) }

    // Variable para el gasto total
    var gastoTotal by remember { mutableStateOf("") }

    // Lista de opciones para los seleccionables
    val tiposCombustible: List<String>  = listOf("Gasolina", "Gasóleo A", "Gasóleo B", "Gasóleo A+")
    val consumos: List<String> = List(191) { (it + 21) / 10.0 }.map { "%.1f l/100km".format(it) }

    fun calcularGastoTotal () {
        try {
            val km = kilometros.toDoubleOrNull()
            val consumo = consumoCombustible.split(" ")[0].toDoubleOrNull()
            val precio = precioCombustible.toDoubleOrNull()

            if (km != null && consumo != null && precio != null) {
                val total = (km / 100) * consumo * precio
                gastoTotal = "Gasto Total: €${DecimalFormat("#.##").format(total)}"
            } else {
                gastoTotal = "Por favor, introduce todos los valores correctamente"
            }
        } catch (e: Exception) {
            gastoTotal = "Hubo un error al calcular el gasto."
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen como una franja en la parte superior
            Image(
                painter = painterResource(id = R.drawable.gasolinera),
                contentDescription = "Gasolinera",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column (
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.presentacion_calculadora_gastos),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Campos de kilómetros a recorrer
                OutlinedTextField(
                    value = kilometros,
                    onValueChange = { nuevaCantidad ->
                        if (nuevaCantidad.all { it.isDigit() } || nuevaCantidad.isEmpty()) {
                            kilometros = nuevaCantidad
                        }
                    },
                    label = { Text("Kilómetros a recorrer") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Introduce los kilómetros") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos de tipo de combustible con ExposedDropdownMenuBox
                ExposedDropdownMenuBox(
                    expanded = expandedCombustible,
                    onExpandedChange = { expandedCombustible = !expandedCombustible },
                ) {
                    OutlinedTextField(
                        value = tipoCombustible,
                        onValueChange = {},
                        label = { Text("Tipo de combustible") },
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCombustible,
                        onDismissRequest = { expandedCombustible = false }
                    ) {
                        tiposCombustible.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    tipoCombustible = it
                                    expandedCombustible = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Campos para el precio de combustible
                OutlinedTextField(
                    value = precioCombustible,
                    onValueChange = { nuevoValor ->
                        if (nuevoValor.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                            precioCombustible = nuevoValor
                        }
                    },
                    label = { Text("Precio del combustible / L (€)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Introduce el precio") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campos para el consumo del combustible con ExposedDropdownMenuBox
                ExposedDropdownMenuBox(
                    expanded = expandedConsumo,
                    onExpandedChange = { expandedConsumo = it },
                ) {
                    OutlinedTextField(
                        value = consumoCombustible,
                        onValueChange = {},
                        label = { Text("Consumo de combustible") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Icon"
                            )
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedConsumo,
                        onDismissRequest = { expandedConsumo = false }
                    ) {
                        consumos.forEach { tipo ->
                            DropdownMenuItem(
                                text = { Text(tipo) },
                                onClick = {
                                    consumoCombustible = tipo
                                    expandedConsumo = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { calcularGastoTotal() },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF63A002),
                        contentColor = Color.White
                    )
                ) {
                    Text("Calcular")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = gastoTotal,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )
            }

        }
    }
}
