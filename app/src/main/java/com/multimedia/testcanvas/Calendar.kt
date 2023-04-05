import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun Calendar() {
    val textMeasurer = rememberTextMeasurer()

    val monthYear = Calendar.getInstance().apply { time = Date() }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onDraw = {
            val daysInMonth = monthYear.getActualMaximum(Calendar.DAY_OF_MONTH)
            val firstDayOfMonth =
                monthYear.apply { set(Calendar.DAY_OF_MONTH, 1) }.get(Calendar.DAY_OF_WEEK)
            val screenWidth = size.width
            val screenHeight = size.height
            val cellWidth = screenWidth / 7
            val cellHeight = (screenHeight - 32.dp.toPx()) / 6
            var x = 0f
            var y = 0f
            var day = 1


            // Рисуем заголовок
            drawIntoCanvas { canvas ->

                drawText(
                    textMeasurer.measure(
                        text = AnnotatedString(
                            " ${
                                monthYear.getDisplayName(
                                    Calendar.MONTH,
                                    Calendar.LONG,
                                    Locale.getDefault()
                                )
                            } ${monthYear.get(Calendar.YEAR)} "
                        ),
                        style = TextStyle(fontSize = 24.sp)
                    ), brush = Brush.verticalGradient(
                        colors = listOf(Color.Red, Color.Blue),
                        startY = 0f,
                        endY = 24.dp.toPx()
                    )
                )
            }

            // Рисуем дни недели
            drawIntoCanvas { canvas ->
//                drawText(
//                    textMeasurer.measure(
//                        text = AnnotatedString("Sun" ),
//                        style = TextStyle(fontSize = 18.sp)
//                    ), brush = Brush.verticalGradient(
//                        colors = listOf(Color.Red, Color.Blue),
//                        startY = x,
//                        endY = y + 48.dp.toPx()
//                    )
//
                canvas.nativeCanvas.drawText("Sun", x, y + 48.dp.toPx(), Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Mon",
                    x + cellWidth,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Tue",
                    x + cellWidth * 2,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Wed",
                    x + cellWidth * 3,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Thu",
                    x + cellWidth * 4,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Fri",
                    x + cellWidth * 5,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
                canvas.nativeCanvas.drawText(
                    "Sat",
                    x + cellWidth * 6,
                    y + 48.dp.toPx(),
                    Paint().apply { textSize = 18.sp.toPx() })
            }

            y += cellHeight

            // Рисуем календарь
            while (day <= daysInMonth) {
                if (day == 1) {
                    x = (firstDayOfMonth - 1) * cellWidth
                }
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        day.toString(),
                        x + 16.dp.toPx(), y + 32.dp.toPx(),
                        Paint().apply { textSize = 18.sp.toPx() })
                }
                x += cellWidth
                day++
                if (x >= screenWidth) {
                    x = 0f
                    y += cellHeight
                }
            }
        }
    )
}
