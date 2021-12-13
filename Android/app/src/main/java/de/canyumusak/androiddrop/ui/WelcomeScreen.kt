package de.canyumusak.androiddrop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WelcomeScreen() {
    Surface {
        CompassLayout(
            north = {
                Box(
                    Modifier
                        .background(Color.Blue)
                        .fillMaxSize()
                )
            },
            center = {
                Landing()
            },
            south = {
                Tipping()
            },
            east = {
                Legal()
            },
            west = {
                HowToUse()
            },
        )

    }
}
