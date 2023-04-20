package com.example.mykmp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DemeritPointsCalculator()
                }
            }
        }
    }
}


@Composable
fun DemeritPointsCalculator() {
    var speed by remember { mutableStateOf("") }
    var speedLimit by remember { mutableStateOf("") }
    var isHoliday by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = speed,
            onValueChange = { speed = it },
            label = { Text("Your speed (km/h)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = speedLimit,
            onValueChange = { speedLimit = it },
            label = { Text("Speed limit (km/h)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        Row(
            Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isHoliday,
                onCheckedChange = { isHoliday = it }
            )

            Text("Holiday")
        }

        Button(
            onClick = {
                val points = calculatePoints(speed.toInt(), speedLimit.toInt(), isHoliday)
                result = "Demerit Points: $points"
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Calculate")
        }

        if (result.isNotEmpty()) {
            Text(result, modifier = Modifier.padding(top = 16.dp))
        }
    }
}

private fun calculatePoints(speed: Int, speedLimit: Int, isHoliday: Boolean): Int {
    var points = 0
    var speedDifference = speed - speedLimit

    if (speed <= speedLimit) {
        return 0
    } else {
        speedDifference = speed - speedLimit
        points = speedDifference // 5
    }
    if (isHoliday) {
        points *= 2
    }
    return points
}


@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        DemeritPointsCalculator()
    }
}
