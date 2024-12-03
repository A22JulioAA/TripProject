package com.example.tripproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripproject.screens.ConversorDivisasScreen
import com.example.tripproject.screens.GastosViajeScreen
import com.example.tripproject.screens.MapasYRutasScreen
import com.example.tripproject.screens.WelcomeScreen
import com.example.tripproject.ui.theme.TealLight
import com.example.tripproject.ui.theme.TripProjectTheme
import com.example.tripproject.viewModels.RutaViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripProjectTheme {

                val navController = rememberNavController()
                val rutaViewModel: RutaViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBar() },
                    bottomBar = { BottomBar(navController) },
                    content = { innerPadding ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                MyApp(modifier = Modifier.padding(innerPadding), rutaViewModel = rutaViewModel)
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

// Función para crear la top bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
        var expanded by remember { mutableStateOf(false) }

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF63A002),
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            title = {
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen del logo
                    Image(
                        painter = painterResource(id = R.drawable.logo_app),
                        contentDescription = stringResource(R.string.logo_alt),
                        modifier = Modifier.size(70.dp)
                    )

                    // Nombre de la aplicación
                    Text(
                        text = stringResource(id = R.string.topbar_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    // Imagen de usuario que será un desplegable con diferentes opciones
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.user_icon),
                            contentDescription = stringResource(R.string.user_icon_alt),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { expanded = !expanded }
                                .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                                .padding(4.dp)
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            DropdownMenuItem(
                                onClick = {},
                                text = {
                                    MyTxt(stringResource(R.string.seccion_perfil), Modifier.padding(15.dp))
                                }
                            )
                            HorizontalDivider()
                            DropdownMenuItem(
                                onClick = {
                                },
                                text = {
                                    MyTxt(stringResource(R.string.seccion_ayuda), Modifier.padding(15.dp))
                                }
                            )
                            HorizontalDivider()
                            DropdownMenuItem(
                                onClick = {},
                                text = {
                                    MyTxt(stringResource(R.string.seccion_salir), Modifier.padding(15.dp))
                                },
                                colors = MenuDefaults.itemColors(
                                    textColor = MaterialTheme.colorScheme.error
                                )
                            )
                        }
                    }
                }
            },
            modifier = Modifier.shadow(4.dp)
        )
}

// Función para crear la bottom bar
@Composable
fun BottomBar (navController: NavController) {
    NavigationBar (
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.home_logo_bottombar),
                    contentDescription = stringResource(R.string.home_alt),
                    modifier = Modifier.size(45.dp)
                )
            },
//            label = { Text(stringResource(R.string.home_alt)) },
            selected = navController.currentBackStackEntry?.destination?.route == stringResource(R.string.default_nav_controller_page),
            onClick = { navController.navigate("home") {
                launchSingleTop = true
                restoreState = true
            } }
        )

        VerticalDivider(
            modifier = Modifier
                .height(56.dp)
                .background(color = Color.Black)
        )

        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.car_logo_bottombar),
                    contentDescription = stringResource(R.string.gastos_viaje_alt),
                    modifier = Modifier.size(45.dp)
                )
            },
//            label = { Text(stringResource(R.string.gastos_viaje_alt)) },
            selected = navController.currentBackStackEntry?.destination?.route == "gastosViajeScreen",
            onClick = { navController.navigate("gastosViajeScreen") }
        )

        VerticalDivider(
            modifier = Modifier
                .height(56.dp)
                .background(color = Color.Black)
        )

        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.dollar_logo_bottombar),
                    contentDescription = stringResource(R.string.conversor_divisas_alt
                    ),
                    modifier = Modifier.size(45.dp)
                )
            },
//            label = { Text(stringResource(R.string.home_alt)) },
            selected = navController.currentBackStackEntry?.destination?.route == "conversorDivisasScreen",
            onClick = { navController.navigate("conversorDivisasScreen") }
        )

        VerticalDivider(
            modifier = Modifier
                .height(56.dp)
                .background(color = Color.Black)
        )

        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(R.drawable.mapa_logo_bottombar),
                    contentDescription = stringResource(R.string.planificador_rutas_alt),
                    modifier = Modifier.size(45.dp)
                )
            },
//            label = { Text(stringResource(R.string.home_alt)) },
            selected = navController.currentBackStackEntry?.destination?.route == "mapasYRutasScreen",
            onClick = { navController.navigate("mapasYRutasScreen") }
        )
    }
}

@Composable
fun MyApp (modifier: Modifier, rutaViewModel: RutaViewModel) {
    WelcomeScreen(rutaViewModel, modifier)
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
    val rutaViewModel: RutaViewModel = viewModel()
    TripProjectTheme {
        MyApp(modifier = Modifier.fillMaxSize(), rutaViewModel)
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