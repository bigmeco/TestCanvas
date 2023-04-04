package com.multimedia.testcanvas

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@SuppressLint("ResourceAsColor")
@Composable
fun LineChart(data: MutableList<Int>, xLabels: List<String>, yLabels: List<String>) {
    val maxDataValue = data.maxOrNull() ?: 0
    val chartHeight = 300.dp
    val chartWidth = 300.dp
    val axisPaint = Paint().apply {
        textSize = 44f
        isAntiAlias = true
        strokeWidth = 4f
        color = Color(0xFF000000).hashCode()
    }
    val dataPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 8f
        color = Color(0xFF66A6FE).hashCode()
    }

    Canvas(modifier = Modifier.size(chartWidth, chartHeight)) {
        val xStep = size.width / (data.size - 1)
        val yStep = chartHeight / maxDataValue

        // Draw x-axis and labels
        drawLine(
            color = Color.Gray,
            Offset(0f, size.height),
            Offset(size.width, size.height)
        )

        // Draw y-axis and labels
        drawLine(
            color = Color.Gray,
            Offset(0f, 0f),
            Offset(0f, size.height)
        )

        xLabels.forEachIndexed { index, label ->
            drawLine(
                color = Color.Gray,
                Offset(index * xStep, size.height),
                Offset(index * xStep, size.height + 16.dp.toPx())
            )
            drawIntoCanvas {
                val textWidth = axisPaint.measureText(label)
                it.nativeCanvas.drawText(
                    label,
                    index * xStep - textWidth / 2,
                    size.height + 32.dp.toPx(),
                    axisPaint
                )
            }
        }

        yLabels.forEachIndexed { index, label ->
            drawLine(
                color = Color.Gray,
                Offset(-16.dp.toPx(), index * chartHeight.toPx() / (yLabels.size - 1)),
                Offset(0f, index * chartHeight.toPx() / (yLabels.size - 1))
            )
            drawIntoCanvas {
                val textWidth = axisPaint.measureText(label)
                it.nativeCanvas.drawText(
                    label,
                    -32.dp.toPx() - textWidth,
                    index * chartHeight.toPx() / (yLabels.size - 1) + axisPaint.textSize / 2,
                    axisPaint
                )
            }
        }

        // Draw data points and lines
        data.forEachIndexed { index, value ->
            val point = Offset(index * xStep, size.height - value * yStep.toPx())
            if (index > 0) {
                val previousPoint =
                    Offset((index - 1) * xStep, size.height - data[index - 1] * yStep.toPx())
                drawLine(
                    Color(dataPaint.color), previousPoint, point,
                    strokeWidth = 11f,
                    cap = StrokeCap.Round
                )
            }
            drawCircle(Color(dataPaint.color), 12f, point)
        }
    }
}
