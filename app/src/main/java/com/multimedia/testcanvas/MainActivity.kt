package com.multimedia.testcanvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import com.multimedia.testcanvas.ui.theme.TestCanvasTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCanvasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        ProgressBar(0.3f)
//                        ParabolicProgressBar(0.8f)
//                        LineChart(mutableListOf(2,4,6,7,5), listOf("1","df","dfd","sddsd","dewefd"),listOf("1","df","dfd",""))
//                        GraphDiagram(numbers = mutableListOf(2,4,6,7,5))

                        val currencies = listOf(
                            Pair("USD", 90.0),
                            Pair("EUR", 90.0),
                            Pair("GBP", 100.0)
                        )
                        CurrencyTable(currencies = currencies)
                    }
                }
            }
        }
    }
}

