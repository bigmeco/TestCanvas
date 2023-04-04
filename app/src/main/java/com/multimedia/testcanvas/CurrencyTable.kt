package com.multimedia.testcanvas

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun CurrencyTable(currencies: List<Pair<String,Double>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Заголовок таблицы
        item {
            LazyRow {
                item {
                    Text(
                        text = "Валюта",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .width(120.dp)
                            .padding(end = 16.dp)
                    )
                }
                item {
                    Text(
                        text = "Курс",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }

        // Содержимое таблицы
        itemsIndexed(currencies) {index, currency ->
            LazyRow {
                item {
                    Text(
                        text = currency.first,
                        modifier = Modifier
                            .width(120.dp)
                            .padding(end = 16.dp)
                    )
                }
                item {
                    Text(
                        text = currency.second.toString(),
                        modifier = Modifier.width(80.dp)
                    )
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}
