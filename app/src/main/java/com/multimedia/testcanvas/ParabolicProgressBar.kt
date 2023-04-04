package com.multimedia.testcanvas


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun ParabolicProgressBar(progress: Float) {
    val height = 300.dp
    val width = 300.dp
    val padding = 8.dp

    Canvas(
        modifier = Modifier
            .size(width, height)
            .padding(padding),
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2

        val parabolicProgress = progress.coerceIn(0f,1f)
        val parabolicY =
            (centerY - height.toPx() / 4) * (1 - 4 * (parabolicProgress - 0.5f) * (parabolicProgress - 0.5f))

        repeat(1000) {
            drawCircle(
                color = Color.Blue,
                radius = 24f,
                center =
                Offset(
                    centerX + (width - 2 * padding).toPx() * ((progress-(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f),
                    ((centerY - height.toPx() / 4) * (1 - 4 * ((progress-(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f) * ((progress-(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f))*4)
                )
            )
        }

        repeat(1000) {
            drawCircle(
                color = Color.Green,
                radius = 14f,
                center =
                Offset(
                    centerX + (width - 2 * padding).toPx() * ((progress+(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f),
                    ((centerY - height.toPx() / 4) * (1 - 4 * ((progress+(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f) * ((progress+(it.toFloat()/1000)).coerceIn(0f, 1f) - 0.5f))*4)
                )
            )
        }

        drawCircle(
            color = Color.Red,
            radius = 16f,
            center = Offset(
                centerX + (width - 2 * padding).toPx() * (parabolicProgress - 0.5f),
                parabolicY*4
            ),
        )
    }
}



