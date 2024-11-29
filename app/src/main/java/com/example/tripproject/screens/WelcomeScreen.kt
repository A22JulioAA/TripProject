package com.example.tripproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.tripproject.R
import com.example.tripproject.ui.theme.TealLight
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue


data class Ruta(val nombre: String, val descripcion: String)

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    val rutas = remember { mutableStateListOf(
        Ruta("Ruta 1", "Descripción de la ruta 1."),
        Ruta("Ruta 2", "Descripción de la ruta 2."),
    ) }

    var isDialogOpen by remember { mutableStateOf(false) }
    var nombreRuta by remember { mutableStateOf("") }
    var descripcionRuta by remember { mutableStateOf("") }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        // Box para contener la imagen y el resto del contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Imagen como una franja en la parte superior
            Image(
                painter = painterResource(id = R.drawable.van_background_welcome),
                contentDescription = "Van trip background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Ajusta el tamaño de la franja de la imagen
                contentScale = ContentScale.Crop
            )

            // El contenido de la pantalla debajo de la imagen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tus rutas",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 8.dp) // Espaciado superior
                )

                // LazyColumn para las rutas
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    items(rutas.size) { index ->
                        val ruta = rutas[index]
                        RutaItem(ruta)
                    }
                }

                // Botón para crear nueva ruta
                Button(
                    onClick = { isDialogOpen = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF63A002),
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Crea una nueva ruta!"
                    )
                }

                if (isDialogOpen) {
                    FormDialog(
                        onDismiss = { isDialogOpen = false },
                        nombreRuta = nombreRuta,
                        onRouteDescriptionChange = { descripcionRuta = it },
                        descripcionRuta = descripcionRuta,
                        onRouteNameChange = { nombreRuta = it },
                        onRouteCreated = { nombre, descripcion ->
                            rutas.add(Ruta(nombre, descripcion))
                            nombreRuta = ""
                            descripcionRuta = ""
                            isDialogOpen = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RutaItem (ruta: Ruta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF63A002)
        )
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = ruta.nombre,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ruta.descripcion,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDialog (
    onDismiss: () -> Unit,
    nombreRuta: String,
    onRouteNameChange: (String) -> Unit,
    descripcionRuta: String,
    onRouteDescriptionChange: (String) -> Unit,
    onRouteCreated: (String, String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Completa los siguientes campos")
        },
        text = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nombre de la ruta")
                OutlinedTextField(
                    value = nombreRuta,
                    onValueChange = onRouteNameChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Nombre") }
                )

                Text("Descripción de la ruta")

                OutlinedTextField(
                    value = descripcionRuta,
                    onValueChange = onRouteDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Descripción") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    println("Ruta creada: Nombre ${nombreRuta}, Descripcion: ${descripcionRuta}")
                    onDismiss()
                }
            ) {
                Text("Crear Ruta")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}
