package com.multimedia.testcanvas


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(progress: Float) {
    Canvas(modifier = Modifier.size(200.dp)) {
        val strokeWidth = 10.dp.toPx()
        val halfStroke = strokeWidth / 2
        val sweep = progress * 360
        val size = size.width - strokeWidth

        // Рисуем фон круга
        drawArc(
            color = Color.LightGray,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(strokeWidth)
        )

        // Рисуем сегмент прогресса
        drawArc(
            color = Color.Blue,
            startAngle = -90f,
            sweepAngle = sweep,
            useCenter = false,
            style = Stroke(strokeWidth)
        )

        // Рисуем текст с процентами
//        drawContext.canvas.nativeCanvas.drawText(
//            "${(progress * 100).toInt()}%",
//            size / 2 - halfStroke,
//            size / 2 + halfStroke,
//            Paint().apply {
//                textAlign = Paint.Align.CENTER
//                textSize = 20.dp.toPx()
//            }
//        )
    }
}

