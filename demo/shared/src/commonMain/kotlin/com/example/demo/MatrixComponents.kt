package com.example.demo

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.random.Random


@Composable
fun MatrixRain(
    modifier: Modifier = Modifier,
    color: Color = MATRIX_GREEN
) {
    val characters = remember { "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789$#%&@*+=<>/?" }
    val infiniteTransition = rememberInfiniteTransition(label = "MatrixRain")

    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        color = color,
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
    )

    // Ticker for flickering characters
    val ticker by infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = 100,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Ticker"
    )

    val dropCount = 15
    val animations = List(dropCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = -0.5f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = Random.nextInt(2500, 5000),
                    delayMillis = Random.nextInt(0, 3000),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "Drop$index"
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val columnWidth = canvasWidth / dropCount

        animations.forEachIndexed { index, animProgress ->
            val x = index * columnWidth + (columnWidth / 2)
            val currentY = animProgress.value * canvasHeight

            // Randomly pick a set of characters for this tick
            val charTrailCount = 15
            for (charIndex in 0 until charTrailCount) {
                val charY = currentY - (charIndex * 20.dp.toPx())
                if (charY in -20.dp.toPx()..canvasHeight) {
                    val alpha = (1f - (charIndex.toFloat() / charTrailCount)).coerceIn(0f, 1f)
                    val charColor = if (charIndex == 0) Color.White else color

                    // Scramble characters based on ticker to create flickering effect
                    val char = characters[(ticker + index + charIndex) % characters.length].toString()

                    drawText(
                        textMeasurer = textMeasurer,
                        text = char,
                        style = textStyle.copy(color = charColor.copy(alpha = alpha)),
                        topLeft = Offset(x, charY)
                    )
                }
            }
        }
    }
}


@Composable
fun GlitchTitle(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MATRIX_GREEN
) {
    val infiniteTransition = rememberInfiniteTransition(label = "GlitchTitle")
    val glitchPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "GlitchPhase"
    )

    val isGlitching = glitchPhase > 0.97f
    val autoSize = TextAutoSize.StepBased(
        minFontSize = 8.sp,
        maxFontSize = 32.sp,
        stepSize = 1.sp
    )

    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()) {
        if (isGlitching) {
            Text(
                text = "[ $text ]",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = Color.Magenta.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = (-3).dp, y = (Random.nextInt(-2, 2)).dp).fillMaxWidth(),
                maxLines = 1,
                autoSize = autoSize,
                softWrap = false
            )
            Text(
                text = "[ $text ]",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                ),
                color = Color.Cyan.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = 3.dp, y = (Random.nextInt(-2, 2)).dp).fillMaxWidth(),
                maxLines = 1,
                autoSize = autoSize,
                softWrap = false
            )
        }

        Text(
            text = "[ $text ]",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            ),
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            autoSize = autoSize,
            softWrap = false
        )

        Text(
            text = "[ $text ]",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            ),
            color = color.copy(alpha = 0.2f),
            textAlign = TextAlign.Center,
            modifier = Modifier.blur(6.dp).fillMaxWidth(),
            maxLines = 1,
            autoSize = autoSize,
            softWrap = false
        )
    }
}

@Composable
fun ScanlineOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val scanlineSpacing = 6.dp.toPx()
        val strokeWidth = 1.dp.toPx()
        var y = 0f
        while (y < size.height) {
            drawLine(
                color = Color.Black.copy(alpha = 0.15f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = strokeWidth
            )
            y += scanlineSpacing
        }
    }
}

@Composable
fun TerminalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MATRIX_GREEN
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color.copy(alpha = 0.1f),
            contentColor = color
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(fontFamily = FontFamily.Monospace)
        )
    }
}