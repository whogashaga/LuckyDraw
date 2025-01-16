package com.example.luckydraw

import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.luckydraw.ui.theme.LuckyDrawTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        setContent {
            LuckyDrawTheme(darkTheme = false) {
                LuckyDrawScreen("The Winner is ")
            }
        }
    }
}

@Composable
fun LuckyDrawScreen(prefix: String = "") {
    val items = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    val selectedIndex = remember { mutableStateOf(-1) }
    val onSpinEnd = remember { mutableStateOf(false) }

    LuckyDrawWheel(items = items, prefix = prefix) { resultIndex, onSpinFinished ->
        selectedIndex.value = resultIndex
        onSpinEnd.value = onSpinFinished
    }

    if (onSpinEnd.value) {
        Toast.makeText(
            LocalContext.current,
            "Congratulations!\n${items[selectedIndex.value]}",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun LuckyDrawWheel(items: List<String>, prefix: String = "", onSpinEnd: (Int, Boolean) -> Unit) {
    val rotation = remember { Animatable(0f) }
    val itemAngle = 360f / items.size
    val coroutineScope = rememberCoroutineScope()
    val animationFinished = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(-1) }
    val colorList = mutableListOf(
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.White,
        Color.Red,
        Color.Green,
        Color.LightGray,
        Color.Yellow
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .rotate(rotation.value)) {
                items.forEachIndexed { index, text ->
                    val startAngle = index * itemAngle
                    drawArc(
                        color = colorList[index % colorList.size],
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
                            text,
                            textPosition.x,
                            textPosition.y,
                            Paint().apply {
                                textSize = 50f
                                textAlign = Paint.Align.CENTER
                                color = android.graphics.Color.BLACK
                            }
                        )
                    }
                }
            }
            /* The Pointer at the top */
            Box(
                Modifier
                    .size(24.dp)
                    .align(Alignment.TopCenter)
                    .rotate(180f)
                    .background(Color.Black, shape = GenericShape { size, _ -> // TriangleShape
                        moveTo(size.width / 2, 0f)
                        lineTo(0f, size.height)
                        lineTo(size.width, size.height)
                        close()
                    })
            )
        }

        // Each item pair to each angle range
        val ranges: List<ClosedFloatingPointRange<Float>> = items.mapIndexed { index, _ ->
            val start = index * itemAngle
            val end = if (index == items.size - 1) 360f else start + itemAngle
            start..end
        }

        Button(
            onClick = {
                animationFinished.value = false
                coroutineScope.launch {
                    val randomAngle = (1 until 360).random().toFloat()
                    val randomLoops = (1 until 6).random()
                    val randomDuration = (3000 until 6000).random()
                    val targetRotation = (rotation.targetValue) + (randomLoops * 360f) + randomAngle
                    rotation.animateTo(
                        targetValue = targetRotation,
                        animationSpec = tween(
                            durationMillis = randomDuration,
                            easing = FastOutSlowInEasing
                        )
                    )
                    var selectedAngle = 270f - (targetRotation % 360)
                    selectedAngle = if (selectedAngle >= 0) selectedAngle else (selectedAngle+360f)
                    selectedIndex.value = ranges.indexOfFirst { selectedAngle in it }
                    animationFinished.value = true
                    onSpinEnd(selectedIndex.value, animationFinished.value)
                }
            }) {
            Text("Spin")
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            onClick = {
                coroutineScope.launch {
                    animationFinished.value = false
                    rotation.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = 800, easing = LinearEasing)
                    )

                }
            }
        ) {
            Text("Reset")
        }

        if (animationFinished.value) {
            val text = if (prefix.isEmpty()) "We Got " else prefix
            Text(
                text = "$text${items[selectedIndex.value]}",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


@Composable
fun ShowToast(btnTxt: String = "Show Toast", text: String) {
    val context = LocalContext.current

    Button(onClick = {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }) {
        Text(text = btnTxt)
    }
}

