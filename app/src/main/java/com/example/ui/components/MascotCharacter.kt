package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * High-fidelity animated Focus Companion (Tomato Mascot) matching the provided design.
 * Features:
 * - Tomato-shaped head with stem leaves
 * - Over-ear headphones
 * - Desk, laptop, mouse, and animated typing hands
 * - Floating pulsing lightbulb / idea bubble
 * - Subtle breathing and focus state animations
 * - Strictly drawn with vector Canvas primitives (NO emojis).
 */
@Composable
fun MascotCharacter(
    modifier: Modifier = Modifier,
    size: Dp = 190.dp,
    isRunning: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mascot_anim")

    // Breathing / bobbing animation
    val bobbing = infiniteTransition.animateFloat(
        initialValue = -2.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mascot_bobbing"
    )

    // Typing hand oscillation
    val typing = infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isRunning) 280 else 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mascot_typing"
    )

    // Lightbulb pulse glow
    val bulbPulse = infiniteTransition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bulb_pulse"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val canvasW = this.size.width
            val canvasH = this.size.height

            val scale = canvasW / 200f
            val offsetY = bobbing.value * scale

            val creamFill = Color(0xFFFBE4E2)
            val creamDark = Color(0xFFF1CECB)
            val outlineColor = Color(0xFF5A2E2E)
            val white = Color.White
            val bulbGlow = Color(0xFFFFD54F)

            // --- 1. Desk Base ---
            val deskPath = Path().apply {
                moveTo(20f * scale, 175f * scale)
                lineTo(180f * scale, 175f * scale)
                lineTo(190f * scale, 195f * scale)
                lineTo(10f * scale, 195f * scale)
                close()
            }
            drawPath(deskPath, color = creamDark, style = Fill)
            drawPath(deskPath, color = outlineColor.copy(alpha = 0.3f), style = Stroke(width = 2.5f * scale))

            // --- 2. Mascot Body & Arms ---
            // Shoulders/Body
            drawOval(
                color = creamFill,
                topLeft = Offset(52f * scale, (108f + offsetY) * scale),
                size = Size(96f * scale, 68f * scale)
            )
            drawOval(
                color = outlineColor,
                topLeft = Offset(52f * scale, (108f + offsetY) * scale),
                size = Size(96f * scale, 68f * scale),
                style = Stroke(width = 2.5f * scale)
            )

            // --- 3. Tomato Head ---
            val headCenter = Offset(100f * scale, (84f + offsetY) * scale)
            val headRadius = 42f * scale

            // Head fill
            drawCircle(
                color = creamFill,
                radius = headRadius,
                center = headCenter
            )
            // Head outline
            drawCircle(
                color = outlineColor,
                radius = headRadius,
                center = headCenter,
                style = Stroke(width = 3.2f * scale)
            )

            // Stem / Leaves on top of head
            val stemPath = Path().apply {
                val topY = (42f + offsetY) * scale
                moveTo(100f * scale, topY)
                // Center leaf
                lineTo(100f * scale, (30f + offsetY) * scale)
                // Left leaf
                quadraticTo(
                    90f * scale, (36f + offsetY) * scale,
                    82f * scale, (38f + offsetY) * scale
                )
                quadraticTo(
                    92f * scale, (44f + offsetY) * scale,
                    97f * scale, topY
                )
                // Right leaf
                quadraticTo(
                    108f * scale, (36f + offsetY) * scale,
                    118f * scale, (38f + offsetY) * scale
                )
                quadraticTo(
                    108f * scale, (44f + offsetY) * scale,
                    103f * scale, topY
                )
            }
            drawPath(stemPath, color = creamDark, style = Fill)
            drawPath(stemPath, color = outlineColor, style = Stroke(width = 2.5f * scale, cap = StrokeCap.Round))

            // Eyes (Cute focused dots)
            val leftEyeX = 86f * scale
            val rightEyeX = 114f * scale
            val eyesY = (82f + offsetY) * scale

            drawCircle(color = outlineColor, radius = 3.2f * scale, center = Offset(leftEyeX, eyesY))
            drawCircle(color = outlineColor, radius = 3.2f * scale, center = Offset(rightEyeX, eyesY))

            // Cheeks (Soft blush)
            drawCircle(color = Color(0xFFE57373).copy(alpha = 0.45f), radius = 5.5f * scale, center = Offset(76f * scale, (89f + offsetY) * scale))
            drawCircle(color = Color(0xFFE57373).copy(alpha = 0.45f), radius = 5.5f * scale, center = Offset(124f * scale, (89f + offsetY) * scale))

            // Mouth (Subtle happy focus arc)
            val mouthPath = Path().apply {
                moveTo(96f * scale, (92f + offsetY) * scale)
                quadraticTo(
                    100f * scale, (96f + offsetY) * scale,
                    104f * scale, (92f + offsetY) * scale
                )
            }
            drawPath(mouthPath, color = outlineColor, style = Stroke(width = 2.2f * scale, cap = StrokeCap.Round))

            // --- 4. Over-Ear Headphones ---
            // Headband
            val headbandPath = Path().apply {
                moveTo(58f * scale, (82f + offsetY) * scale)
                quadraticTo(
                    100f * scale, (38f + offsetY) * scale,
                    142f * scale, (82f + offsetY) * scale
                )
            }
            drawPath(headbandPath, color = outlineColor, style = Stroke(width = 3.5f * scale, cap = StrokeCap.Round))

            // Left Earpad
            drawRoundRect(
                color = creamDark,
                topLeft = Offset(52f * scale, (68f + offsetY) * scale),
                size = Size(14f * scale, 30f * scale),
                cornerRadius = CornerRadius(6f * scale)
            )
            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(52f * scale, (68f + offsetY) * scale),
                size = Size(14f * scale, 30f * scale),
                cornerRadius = CornerRadius(6f * scale),
                style = Stroke(width = 2.5f * scale)
            )

            // Right Earpad
            drawRoundRect(
                color = creamDark,
                topLeft = Offset(134f * scale, (68f + offsetY) * scale),
                size = Size(14f * scale, 30f * scale),
                cornerRadius = CornerRadius(6f * scale)
            )
            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(134f * scale, (68f + offsetY) * scale),
                size = Size(14f * scale, 30f * scale),
                cornerRadius = CornerRadius(6f * scale),
                style = Stroke(width = 2.5f * scale)
            )

            // Headphone cord down to laptop
            val cordPath = Path().apply {
                moveTo(59f * scale, (98f + offsetY) * scale)
                quadraticTo(
                    65f * scale, (132f + offsetY) * scale,
                    80f * scale, 142f * scale
                )
            }
            drawPath(cordPath, color = outlineColor.copy(alpha = 0.5f), style = Stroke(width = 1.8f * scale, cap = StrokeCap.Round))

            // --- 5. Laptop & Desk Accessories ---
            // Laptop Screen Back (facing user)
            val laptopW = 56f * scale
            val laptopH = 38f * scale
            val laptopX = (100f * scale) - (laptopW / 2)
            val laptopY = 126f * scale

            drawRoundRect(
                color = white,
                topLeft = Offset(laptopX, laptopY),
                size = Size(laptopW, laptopH),
                cornerRadius = CornerRadius(4f * scale)
            )
            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(laptopX, laptopY),
                size = Size(laptopW, laptopH),
                cornerRadius = CornerRadius(4f * scale),
                style = Stroke(width = 2.5f * scale)
            )

            // Logo on back of laptop (Small circle/apple style)
            drawCircle(
                color = outlineColor.copy(alpha = 0.85f),
                radius = 3.8f * scale,
                center = Offset(100f * scale, laptopY + laptopH / 2)
            )

            // Laptop Base / Keyboard deck
            val baseLaptop = Path().apply {
                moveTo(66f * scale, 164f * scale)
                lineTo(134f * scale, 164f * scale)
                lineTo(138f * scale, 169f * scale)
                lineTo(62f * scale, 169f * scale)
                close()
            }
            drawPath(baseLaptop, color = creamDark, style = Fill)
            drawPath(baseLaptop, color = outlineColor, style = Stroke(width = 2.2f * scale))

            // Mouse on right or left
            drawOval(
                color = white,
                topLeft = Offset(46f * scale, 162f * scale),
                size = Size(14f * scale, 18f * scale)
            )
            drawOval(
                color = outlineColor,
                topLeft = Offset(46f * scale, 162f * scale),
                size = Size(14f * scale, 18f * scale),
                style = Stroke(width = 2f * scale)
            )

            // --- 6. Hands Typing on Laptop ---
            val handYOffset = (typing.value * scale)
            // Left Hand
            drawCircle(
                color = creamFill,
                radius = 7.5f * scale,
                center = Offset(78f * scale, 154f * scale + handYOffset)
            )
            drawCircle(
                color = outlineColor,
                radius = 7.5f * scale,
                center = Offset(78f * scale, 154f * scale + handYOffset),
                style = Stroke(width = 2.2f * scale)
            )

            // Right Hand
            drawCircle(
                color = creamFill,
                radius = 7.5f * scale,
                center = Offset(122f * scale, 154f * scale - handYOffset)
            )
            drawCircle(
                color = outlineColor,
                radius = 7.5f * scale,
                center = Offset(122f * scale, 154f * scale - handYOffset),
                style = Stroke(width = 2.2f * scale)
            )

            // --- 7. Pulsing Lightbulb / Idea Bubble ---
            val bulbCenterX = 158f * scale
            val bulbCenterY = (58f + offsetY * 0.7f) * scale
            val bulbR = 10.5f * scale

            // Outer glow ring
            drawCircle(
                color = bulbGlow.copy(alpha = 0.25f * bulbPulse.value),
                radius = bulbR * 1.8f,
                center = Offset(bulbCenterX, bulbCenterY)
            )

            // Bulb body
            val bulbPath = Path().apply {
                moveTo(bulbCenterX - 5f * scale, bulbCenterY + 8f * scale)
                lineTo(bulbCenterX - 5f * scale, bulbCenterY + 5f * scale)
                arcTo(
                    rect = androidx.compose.ui.geometry.Rect(
                        left = bulbCenterX - bulbR,
                        top = bulbCenterY - bulbR,
                        right = bulbCenterX + bulbR,
                        bottom = bulbCenterY + bulbR
                    ),
                    startAngleDegrees = 135f,
                    sweepAngleDegrees = 270f,
                    forceMoveTo = false
                )
                lineTo(bulbCenterX + 5f * scale, bulbCenterY + 8f * scale)
                close()
            }
            drawPath(bulbPath, color = white, style = Fill)
            drawPath(bulbPath, color = outlineColor, style = Stroke(width = 2.2f * scale, join = StrokeJoin.Round))

            // Bulb filament / inner glow
            drawCircle(
                color = bulbGlow.copy(alpha = 0.85f * bulbPulse.value),
                radius = 3.5f * scale,
                center = Offset(bulbCenterX, bulbCenterY - 1f * scale)
            )

            // Light rays
            val rayAlpha = bulbPulse.value
            drawLine(
                color = outlineColor.copy(alpha = rayAlpha),
                start = Offset(bulbCenterX, bulbCenterY - (bulbR + 4f * scale)),
                end = Offset(bulbCenterX, bulbCenterY - (bulbR + 9f * scale)),
                strokeWidth = 2f * scale,
                cap = StrokeCap.Round
            )
            drawLine(
                color = outlineColor.copy(alpha = rayAlpha),
                start = Offset(bulbCenterX + (bulbR + 3f * scale), bulbCenterY - 3f * scale),
                end = Offset(bulbCenterX + (bulbR + 7f * scale), bulbCenterY - 5f * scale),
                strokeWidth = 2f * scale,
                cap = StrokeCap.Round
            )
            drawLine(
                color = outlineColor.copy(alpha = rayAlpha),
                start = Offset(bulbCenterX - (bulbR + 3f * scale), bulbCenterY - 3f * scale),
                end = Offset(bulbCenterX - (bulbR + 7f * scale), bulbCenterY - 5f * scale),
                strokeWidth = 2f * scale,
                cap = StrokeCap.Round
            )
        }
    }
}
