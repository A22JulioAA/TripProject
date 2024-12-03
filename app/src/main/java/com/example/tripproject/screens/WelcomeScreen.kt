package com.example.tripproject.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.tripproject.models.Ruta
import com.example.tripproject.viewModels.RutaViewModel

@Composable
fun WelcomeScreen(rutaViewModel: RutaViewModel, modifier: Modifier = Modifier) {
    val rutas by rutaViewModel.rutas.collectAsState(emptyList())

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
                    .height(200.dp),
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
                    text = stringResource(R.string.presentacion_welcome_rutas),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(16.dp)
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
                        text = stringResource(R.string.crear_nueva_ruta_button)
                    )
                }

                if (isDialogOpen) {
                    FormDialog(
                        isVisible = isDialogOpen,
                        onDismiss = { isDialogOpen = false },
                        nombreRuta = nombreRuta,
                        onRouteDescriptionChange = { descripcionRuta = it },
                        descripcionRuta = descripcionRuta,
                        onRouteNameChange = { nombreRuta = it },
                        onRouteCreated = { nombre, descripcion ->
                            val nuevaRuta = Ruta(0, nombre, descripcion)
                            rutaViewModel.insertRuta(nuevaRuta)

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
fun RutaItem (ruta: com.example.tripproject.models.Ruta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(1.dp, Color.LightGray, MaterialTheme.shapes.medium),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Row (
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ruta),
                contentDescription = "Ruta",
                tint = Color(0xFF63A002),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column (
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = ruta.nombre,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(
                text = ruta.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDialog (
    isVisible: Boolean,
    onDismiss: () -> Unit,
    nombreRuta: String,
    onRouteNameChange: (String) -> Unit,
    descripcionRuta: String,
    onRouteDescriptionChange: (String) -> Unit,
    onRouteCreated: (String, String) -> Unit
) {
    AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn() + slideInVertically(initialOffsetY = {fullHeight -> fullHeight / 2 }),
    exit = fadeOut() + slideOutVertically(targetOffsetY = { fullHeight -> fullHeight / 2 })
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    stringResource(R.string.nueva_ruta_formulario_titulo),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF63A002)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombreRuta,
                        onValueChange = onRouteNameChange,
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.campo_nombre_formulario)) },
                        placeholder = { Text(stringResource(R.string.campo_nombre_formulario_placeholder)) }
                    )

                    OutlinedTextField(
                        value = descripcionRuta,
                        onValueChange = onRouteDescriptionChange,
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.campo_descripcion_formulario)) },
                        placeholder = { Text(stringResource(R.string.campo_descripcion_formulario_placeholder)) }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRouteCreated(nombreRuta, descripcionRuta)
                        onDismiss()
                    }
                ) {
                    Text(stringResource(R.string.boton_aceptar_nueva_ruta))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(
                        stringResource(R.string.boton_cancelar),
                        color = Color.Gray
                    )
                }
            }
        )
    }
}
