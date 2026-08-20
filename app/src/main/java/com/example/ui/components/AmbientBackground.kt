package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.CoralBackground
import com.example.ui.theme.CoralPrimaryDark
import com.example.ui.theme.CoralPrimaryLight
import kotlin.math.cos
import kotlin.math.sin

/**
 * A non-static, fluid background that constantly moves with subtle animated waves,
 * floating soft glowing orbs, and drifting light specks.
 */
@Composable
fun AmbientBackground(
    modifier: Modifier = Modifier,
    baseColor: Color = CoralBackground,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_background")

    // Slow oscillating phases for orbs
    val phase1 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase1"
    )

    val phase2 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase2"
    )

    val waveOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "waveOffset"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Base background gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        baseColor,
                        CoralPrimaryDark.copy(alpha = 0.95f),
                        baseColor
                    )
                )
            )

            // Animated Floating Glow Orb 1 (Top Right)
            val rad1 = Math.toRadians(phase1.value.toDouble())
            val orb1X = width * 0.75f + (cos(rad1) * width * 0.12f).toFloat()
            val orb1Y = height * 0.22f + (sin(rad1) * height * 0.08f).toFloat()

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CoralPrimaryLight.copy(alpha = 0.35f),
                        Color.Transparent
                    ),
                    center = Offset(orb1X, orb1Y),
                    radius = width * 0.65f
                ),
                center = Offset(orb1X, orb1Y),
                radius = width * 0.65f
            )

            // Animated Floating Glow Orb 2 (Bottom Left)
            val rad2 = Math.toRadians(phase2.value.toDouble())
            val orb2X = width * 0.25f + (sin(rad2) * width * 0.14f).toFloat()
            val orb2Y = height * 0.78f + (cos(rad2) * height * 0.10f).toFloat()

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CoralPrimaryLight.copy(alpha = 0.28f),
                        Color.Transparent
                    ),
                    center = Offset(orb2X, orb2Y),
                    radius = width * 0.55f
                ),
                center = Offset(orb2X, orb2Y),
                radius = width * 0.55f
            )

            // Animated subtle particle dust
            val particleCount = 14
            for (i in 0 until particleCount) {
                val seed = (i * 97) % 100 / 100f
                val speed = 0.5f + (i % 3) * 0.3f
                val px = (width * ((seed + waveOffset.value * 0.2f * speed) % 1f))
                val py = (height * ((seed * 1.5f + (1f - waveOffset.value) * 0.15f * speed) % 1f))
                val pRadius = 2f + (i % 4) * 1.5f
                val pAlpha = 0.12f + (i % 3) * 0.08f

                drawCircle(
                    color = Color.White.copy(alpha = pAlpha),
                    radius = pRadius,
                    center = Offset(px, py)
                )
            }
        }

        content()
    }
}
