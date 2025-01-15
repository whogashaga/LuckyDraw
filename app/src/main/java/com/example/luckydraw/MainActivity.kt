package com.example.luckydraw

import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckydraw.ui.theme.LuckyDrawTheme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LuckyDrawTheme(darkTheme = false) {
                LuckyDrawScreen()
            }
        }
    }
}

@Composable
fun LuckyDrawScreen() {
    val items = listOf("Prize 1", "Prize 2", "Prize 3", "Prize 4", "Prize 5")
    val selectedIndex = remember { mutableStateOf(-1) }

    LuckyDrawWheel(items = items) { resultIndex ->
        selectedIndex.value = resultIndex
    }
}


@Composable
fun LuckyDrawWheel(items: List<String>, onSpinEnd: (Int) -> Unit) {
    val rotation = remember { Animatable(0f) }
    val itemAngle = 360f / items.size
    val coroutineScope = rememberCoroutineScope()
    val animationFinished: MutableState<Boolean> = remember { mutableStateOf(false) }
    val colorList = mutableListOf(Color.Cyan, Color.Magenta, Color.Gray, Color.White, Color.Red, Color.Green, Color.Blue, Color.Yellow)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(300.dp)) {
            items.forEachIndexed { index, item ->
                val startAngle = index * itemAngle
                drawArc(
                    color = colorList[index%colorList.size],
                    startAngle = startAngle,
                    sweepAngle = itemAngle,
                    useCenter = true
                )
                val textAngle = startAngle + itemAngle / 2
                val textRadius = size.minDimension / 3
                val textPosition = Offset(
                    (center.x + textRadius * cos(Math.toRadians(textAngle.toDouble()))).toFloat(),
                    (center.y + textRadius * sin(Math.toRadians(textAngle.toDouble()))).toFloat()
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        item,
                        textPosition.x,
                        textPosition.y,
                        Paint().apply {
                            textSize = 30f
                            textAlign = Paint.Align.CENTER
                            color = android.graphics.Color.BLACK
                        }
                    )
                }
            }
        }

        val selectedIndex = (0 until items.size).random()
        Button(
            onClick = {
            coroutineScope.launch {
                animationFinished.value = false
                val targetRotation = (360f * 5) + (360f - (selectedIndex * itemAngle))
                rotation.animateTo(
                    targetValue = targetRotation,
                    animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
                ) {
                    if (value == targetRotation) {
                        animationFinished.value = true
                        onSpinEnd(selectedIndex)
                    }
                }
            }
        }) {
            Text("Spin")
        }

        if (animationFinished.value) {
            Text(
                text = "We Got ${items[selectedIndex]}!",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
//            Toast.makeText(LocalContext.current, "Congratulations!\n${items[selectedIndex]}", Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
fun ShowToastButton(btn: String = "Show Toast", text: String) {
    val context = LocalContext.current

    Button(onClick = {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }) {
        Text("Show Toast")
    }
}

