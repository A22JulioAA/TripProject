package com.example.tripproject.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tripproject.R

// Función para crear la pantalla de gastos de viaje
@Composable
fun GastosViajeScreen (modifier: Modifier = Modifier) {
    Surface (
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF8F918D)
    ) {
        Column () {
            Text(
                text = stringResource(R.string.presentacion_calculadora_gastos),
            )
        }
    }
}