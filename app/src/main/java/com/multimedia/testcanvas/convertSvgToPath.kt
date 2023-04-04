package com.multimedia.testcanvas

import androidx.compose.ui.graphics.Path
import androidx.core.graphics.PathParser
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory


fun convertSvgPathToPath(svgPath: String): Path {
    val path = Path()
    var x = 0f
    var y = 0f
    var startX = 0f
    var startY = 0f
    var endX = 0f
    var endY = 0f
    var controlX1 = 0f
    var controlY1 = 0f
    var controlX2 = 0f
    var controlY2 = 0f
    var eventType = 0
    var argIndex = 0
    var args = FloatArray(6)
    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()
    parser.setInput(svgPath.reader())
    while (eventType != XmlPullParser.END_DOCUMENT) {
        val tagName = parser.name
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (tagName == "path") {
                    val d = parser.getAttributeValue(null, "d")
                    val tokens = tokenizePathData(d)
                    var i = 0
                    while (i < tokens.size) {
                        val token = tokens[i++]
                        when (token) {
                            "M" -> {
                                x = args[argIndex++]
                                y = args[argIndex++]
                                path.moveTo(x, y)
                                startX = x
                                startY = y
                            }
                            "m" -> {
                                x += args[argIndex++]
                                y += args[argIndex++]
                                path.moveTo(x, y)
                                startX = x
                                startY = y
                            }
                            "L" -> {
                                x = args[argIndex++]
                                y = args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "l" -> {
                                x += args[argIndex++]
                                y += args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "H" -> {
                                x = args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "h" -> {
                                x += args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "V" -> {
                                y = args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "v" -> {
                                y += args[argIndex++]
                                path.lineTo(x, y)
                            }
                            "C" -> {
                                controlX1 = args[argIndex++]
                                controlY1 = args[argIndex++]
                                controlX2 = args[argIndex++]
                                controlY2 = args[argIndex++]
                                endX = args[argIndex++]
                                endY = args[argIndex++]
                                path.cubicTo(controlX1, controlY1, controlX2, controlY2, endX, endY)
                                x = endX
                                y = endY
                            }
                            "c" -> {
                                controlX1 += args[argIndex++]
                                controlY1 += args[argIndex++]
                                controlX2 += args[argIndex++]
                                controlY2 += args[argIndex++]
                                endX += args[argIndex++]
                                endY += args[argIndex++]
                                path.cubicTo(
                                    controlX1,
                                    controlY1,
                                    controlX2,
                                    controlY2,
                                    x + endX,
                                    y + endY
                                )
                                x += endX
                                y += endY
                            }
                            "S" -> {
                                controlX2 = args[argIndex++]
                                controlY2 = args[argIndex++]
                                endX = args[argIndex++]
                                endY = args[argIndex++]
                                val reflectionX = 2 * x - controlX1
                                val reflectionY = 2 * y - controlY1
                                path.cubicTo(
                                    reflectionX,
                                    reflectionY,
                                    controlX2,
                                    controlY2,
                                    endX,
                                    endY
                                )
                                controlX1 = reflectionX
                                controlY1 = reflectionY
                                x = endX
                                y = endY
                            }
                            "s" -> {
                                controlX2 = args[argIndex++]
                                controlY2 = args[argIndex++]
                                endX += args[argIndex++]
                                endY += args[argIndex++]
                                val reflectionX = 2 * x - controlX1
                                val reflectionY = 2 * y - controlY1
                                path.cubicTo(
                                    reflectionX,
                                    reflectionY,
                                    controlX2,
                                    controlY2,
                                    x + endX,
                                    y + endY
                                )
                                controlX1 = reflectionX
                                controlY1 = reflectionY
                                x += endX
                                y += endY
                            }
                            "Q" -> {
                                controlX1 = args[argIndex++]
                                controlY1 = args[argIndex++]
                                endX = args[argIndex++]
                                endY = args[argIndex++]
                                path.quadraticBezierTo(controlX1, controlY1, endX, endY)
                                x = endX
                                y = endY
                            }
                            "q" -> {
                                controlX1 += args[argIndex++]
                                controlY1 += args[argIndex++]
                                endX += args[argIndex++]
                                endY += args[argIndex++]
                                path.quadraticBezierTo(controlX1, controlY1, x + endX, y + endY)
                                x += endX
                                y += endY
                            }
                            "T" -> {
                                endX = args[argIndex++]
                                endY = args[argIndex++]
                                val reflectionX = 2 * x - controlX1
                                val reflectionY = 2 * y - controlY1
                                path.quadraticBezierTo(reflectionX, reflectionY, endX, endY)
                                controlX1 = reflectionX
                                controlY1 = reflectionY
                                x = endX
                                y = endY
                            }
                            "t" -> {
                                endX += args[argIndex++]
                                endY += args[argIndex++]
                                val reflectionX = 2 * x - controlX1
                                val reflectionY = 2 * y - controlY1
                                path.quadraticBezierTo(reflectionX, reflectionY, x + endX, y + endY)
                                controlX1 = reflectionX
                                controlY1 = reflectionY
                                x += endX
                                y += endY
                            }
                            "A" -> {
// not implemented
                            }
                            "a" -> {
// not implemented
                            }
                            else -> {
// process numeric arguments
                                argIndex = 0
                                while (argIndex < args.size && i < tokens.size) {
                                    args[argIndex++] = tokens[i++].toFloat()
                                }
                                i--
                            }
                        }
                    }
                }
            }
        }
        eventType = parser.next()
    }
    return path
}

fun tokenizePathData(d: String): List<String> {
    val tokens = mutableListOf<String>()
    var index = 0
    while (index < d.length) {
        val c = d[index]
        if (c.isDigit() || c == '.' || c == '-') {
            val tokenStart = index
            while (index < d.length && (d[index].isDigit() || d[index] == '.' || d[index] == '-' || d[index] == 'e' || d[index] == 'E')) {
                index++
            }
            tokens.add(d.substring(tokenStart, index))
        } else {
            tokens.add(c.toString())
            index++
        }
    }
    return tokens
}


//
//
//fun convertSvgToPath(svgString: String): Path {
//    val path = Path()
//    var lastX = 0f
//    var lastY = 0f
//    var lastCpX = 0f
//    var lastCpY = 0f
//    var command = ' '
//    var argIndex = 0
//    var args = FloatArray(3)
//
//    svgString.forEach {
//        if (it.isDigit()) {
//            args[argIndex] = it.toString().toFloat()
//        } else {
//            when (command) {
//                'M' -> {
//                    path.moveTo(args[0], args[1])
//                    lastX = args[0]
//                    lastY = args[1]
//                }
//                'm' -> {
//                    path.relativeMoveTo(args[0], args[1])
//                    lastX += args[0]
//                    lastY += args[1]
//                }
//                'L' -> {
//                    path.lineTo(args[0], args[1])
//                    lastX = args[0]
//                    lastY = args[1]
//                }
//                'l' -> {
//                    path.relativeLineTo(args[0], args[1])
//                    lastX += args[0]
//                    lastY += args[1]
//                }
//                'H' -> {
//                    path.lineTo(args[0], lastY)
//                    lastX = args[0]
//                }
//                'h' -> {
//                    path.relativeLineTo(args[0], 0f)
//                    lastX += args[0]
//                }
//                'V' -> {
//                    path.lineTo(lastX, args[0])
//                    lastY = args[0]
//                }
//                'v' -> {
//                    path.relativeLineTo(0f, args[0])
//                    lastY += args[0]
//                }
//                'C' -> {
//                    path.cubicTo(args[0], args[1], args[2], args[3], args[4], args[5])
//                    lastCpX = args[2]
//                    lastCpY = args[3]
//                    lastX = args[4]
//                    lastY = args[5]
//                }
//                'c' -> {
//                    path.relativeCubicTo(args[0], args[1], args[2], args[3], args[4], args[5])
//                    lastCpX += args[2]
//                    lastCpY += args[3]
//                    lastX += args[4]
//                    lastY += args[5]
//                }
//                'S' -> {
//                    val controlX = 2 * lastX - lastCpX
//                    val controlY = 2 * lastY - lastCpY
//                    path.cubicTo(controlX, controlY, args[0], args[1], args[2], args[3])
//                    lastCpX = args[0]
//                    lastCpY = args[1]
//                    lastX = args[2]
//                    lastY = args[3]
//                }
//                's' -> {
//                    val controlX = 2 * lastX - lastCpX
//                    val controlY = 2 * lastY - lastCpY
//                    path.relativeCubicTo(
//                        controlX - lastX,
//                        controlY - lastY,
//                        args[0],
//                        args[1],
//                        args[2],
//                        args[3]
//                    )
//                    lastCpX += args[0]
//                    lastCpY += args[1]
//                    lastX += args[2]
//                    lastY += args[3]
//                }
//                'Z', 'z' -> path.close()
//            }
//        }
//    }
//    return path
//}
//

//fun convertSvgPathToPath(svgPath: String){
//    val path = Path()
//    var currentX = 0f
//    var currentY = 0f
//    var startX = 0f
//    var startY = 0f
//    var previousControlX = 0f
//    var previousControlY = 0f
//
//    val factory = XmlPullParserFactory.newInstance()
//    factory.isNamespaceAware = true
//    val parser = factory.newPullParser()
//    parser.setInput(svgPath.reader())
//
//    while (parser.eventType != XmlPullParser.END_DOCUMENT) {
//        when (parser.eventType) {
//            XmlPullParser.START_TAG -> {
//                val tagName = parser.name
//                when (tagName) {
//                    "path" -> {
//                        val pathData = parser.getAttributeValue(null, "d")
//                        val tokens = pathData.split("\\s+".toRegex())
//                        var i = 0
//                        while (i < tokens.size) {
//                            val token = tokens[i]
//                            when (token) {
//                                "M" -> {
//                                    currentX = tokens[i + 1].toFloat()
//                                    currentY = tokens[i + 2].toFloat()
//                                    startX = currentX
//                                    startY = currentY
//                                    path.moveTo(currentX, currentY)
//                                    i += 3
//                                }
//                                "m" -> {
//                                    currentX += tokens[i + 1].toFloat()
//                                    currentY += tokens[i + 2].toFloat()
//                                    startX = currentX
//                                    startY = currentY
//                                    path.moveTo(currentX, currentY)
//                                    i += 3
//                                }
//                                "L" -> {
//                                    currentX = tokens[i + 1].toFloat()
//                                    currentY = tokens[i + 2].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 3
//                                }
//                                "l" -> {
//                                    currentX += tokens[i + 1].toFloat()
//                                    currentY += tokens[i + 2].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 3
//                                }
//                                "H" -> {
//                                    currentX = tokens[i + 1].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 2
//                                }
//                                "h" -> {
//                                    currentX += tokens[i + 1].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 2
//                                }
//                                "V" -> {
//                                    currentY = tokens[i + 1].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 2
//                                }
//                                "v" -> {
//                                    currentY += tokens[i + 1].toFloat()
//                                    path.lineTo(currentX, currentY)
//                                    i += 2
//                                }
//                                "C" -> {
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}