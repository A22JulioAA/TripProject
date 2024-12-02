package com.example.tripproject.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.StyleImage
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom

// Función para crear la pantalla de mapas y rutas
@Composable
fun MapasYRutasScreen (modifier: Modifier = Modifier) {
    val mapView = rememberMapViewportState() {
        setCameraOptions {
            zoom(2.0)
            center(Point.fromLngLat( -8.7049, 42.8756))
            pitch(0.0)
            bearing(0.0)
        }
    }

    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(
            text = "Hola mapas y rutas",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        MapboxMap(
//            Modifier.fillMaxSize(),
            mapViewportState = mapView,
            style = {
                MapStyle(style = Style.SATELLITE)
            }
        )
    }
}