package com.example.tripproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tripproject.R
import com.example.tripproject.ui.theme.TealLight

@Composable
fun WelcomeScreen (modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = TealLight
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.van_background_welcome),
                contentDescription = "Van trip background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column (
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.userGreeting),
                modifier = Modifier.padding(12.dp)
            )

            Text(
                text = stringResource(id = R.string.welcome_message),
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
