package com.example.tripproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tripproject.R
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.extension.compose.style.StyleImage
import com.mapbox.maps.extension.compose.style.rememberStyleImage
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions

// Función para crear la pantalla de mapas y rutas
@Composable
fun MapasYRutasScreen (modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val mapView = rememberMapViewportState() {
        setCameraOptions {
            zoom(7.0)
            center(Point.fromLngLat( -8.7049, 42.8756))
            pitch(0.0)
            bearing(0.0)
        }
    }

    val marker = rememberStyleImage(imageId = "image-id", resourceId = R.drawable.red_marker)

    Surface (
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MapboxMap(
            Modifier.fillMaxSize(),
            mapViewportState = mapView,
//            style = {
//                MapStyle(style = Style.SATELLITE)
//            },
        ) {
//            ViewAnnotation(
//                options = viewAnnotationOptions {
//                    geometry(Point.fromLngLat(18.06, 59.31))
//                    selected(true)
//                    allowOverlap(true)
//                }
//            ) {
//                ViewAnnotationContent()
//            }
        }
    }
}

@Composable
public fun ViewAnnotationContent() {
    Text(
        text = "Hello world",
        modifier = Modifier
            .padding(3.dp)
            .width(100.dp)
            .height(60.dp)
            .background(
                Color.White
            ),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}