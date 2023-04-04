package com.multimedia.testcanvas

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GraphDiagram(numbers: MutableList<Int>) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val xStep = size.width / (numbers.size - 1)
        val yStep = size.height / numbers.maxOrNull()!!

        val points = numbers.mapIndexed { index, number ->
            Offset(x = index * xStep, y = size.height - number * yStep)
        }

        drawLine(
            color = Color.Black,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = size.height),
            strokeWidth = 2f
        )

        drawLine(
            color = Color.Black,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = 0f, y = size.height),
            strokeWidth = 2f
        )

        points.forEachIndexed { index, point ->
            if (index < points.size - 1) {
                drawLine(
                    color = Color.Blue,
                    start = point,
                    end = points[index + 1],
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
