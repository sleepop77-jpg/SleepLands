package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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
 * Clean, minimalist SVG vector icons drawn using Compose Canvas primitives.
 * Completely emoji-free and rendered with sharp vector paths.
 */

@Composable
fun FullscreenIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color.White,
    isFullscreen: Boolean = false
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val stroke = 2.2f * (w / 24f)
        val inset = 4f * (w / 24f)
        val corner = 6f * (w / 24f)

        if (!isFullscreen) {
            // Expand corners
            // Top-Left
            drawLine(tint, Offset(inset, inset + corner), Offset(inset, inset), stroke, StrokeCap.Round)
            drawLine(tint, Offset(inset, inset), Offset(inset + corner, inset), stroke, StrokeCap.Round)

            // Top-Right
            drawLine(tint, Offset(w - inset - corner, inset), Offset(w - inset, inset), stroke, StrokeCap.Round)
            drawLine(tint, Offset(w - inset, inset), Offset(w - inset, inset + corner), stroke, StrokeCap.Round)

            // Bottom-Left
            drawLine(tint, Offset(inset, h - inset - corner), Offset(inset, h - inset), stroke, StrokeCap.Round)
            drawLine(tint, Offset(inset, h - inset), Offset(inset + corner, h - inset), stroke, StrokeCap.Round)

            // Bottom-Right
            drawLine(tint, Offset(w - inset - corner, h - inset), Offset(w - inset, h - inset), stroke, StrokeCap.Round)
            drawLine(tint, Offset(w - inset, h - inset), Offset(w - inset, h - inset - corner), stroke, StrokeCap.Round)
        } else {
            // Collapse corners
            val midX = w / 2f
            val midY = h / 2f
            drawLine(tint, Offset(midX - corner, inset), Offset(midX - corner, inset + corner), stroke, StrokeCap.Round)
            drawLine(tint, Offset(midX - corner, inset + corner), Offset(inset, inset + corner), stroke, StrokeCap.Round)

            drawLine(tint, Offset(midX + corner, inset), Offset(midX + corner, inset + corner), stroke, StrokeCap.Round)
            drawLine(tint, Offset(midX + corner, inset + corner), Offset(w - inset, inset + corner), stroke, StrokeCap.Round)
        }
    }
}

@Composable
fun PlayPauseIcon(
    isRunning: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val scale = w / 32f

        if (isRunning) {
            // Pause icon: Two vertical rounded bars
            val barW = 5f * scale
            val barH = 18f * scale
            val topY = (h - barH) / 2f

            drawRoundRect(
                color = tint,
                topLeft = Offset(9f * scale, topY),
                size = Size(barW, barH),
                cornerRadius = CornerRadius(2.5f * scale)
            )
            drawRoundRect(
                color = tint,
                topLeft = Offset(18f * scale, topY),
                size = Size(barW, barH),
                cornerRadius = CornerRadius(2.5f * scale)
            )
        } else {
            // Play icon: Right pointing filled triangle with smooth corners
            val path = Path().apply {
                moveTo(11f * scale, 7f * scale)
                lineTo(25f * scale, 16f * scale)
                lineTo(11f * scale, 25f * scale)
                close()
            }
            drawPath(path, color = tint, style = Fill)
        }
    }
}

@Composable
fun ReloadIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val stroke = 2.4f * (w / 24f)
        val center = Offset(w / 2f, h / 2f)
        val r = 7f * (w / 24f)

        // Arc (300 degrees)
        drawArc(
            color = tint,
            startAngle = 40f,
            sweepAngle = 290f,
            useCenter = false,
            topLeft = Offset(center.x - r, center.y - r),
            size = Size(r * 2, r * 2),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )

        // Arrow head on arc start
        val arrowHead = Path().apply {
            val startX = center.x + r * 0.76f
            val startY = center.y + r * 0.64f
            moveTo(startX - 2f, startY - 6f)
            lineTo(startX, startY)
            lineTo(startX + 6f, startY)
        }
        drawPath(arrowHead, color = tint, style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun StarBadgeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color(0xFFFFD54F)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val scale = w / 24f

        val path = Path().apply {
            moveTo(12f * scale, 3f * scale)
            lineTo(14.8f * scale, 8.8f * scale)
            lineTo(21f * scale, 9.7f * scale)
            lineTo(16.5f * scale, 14.1f * scale)
            lineTo(17.6f * scale, 20.3f * scale)
            lineTo(12f * scale, 17.3f * scale)
            lineTo(6.4f * scale, 20.3f * scale)
            lineTo(7.5f * scale, 14.1f * scale)
            lineTo(3f * scale, 9.7f * scale)
            lineTo(9.2f * scale, 8.8f * scale)
            close()
        }
        drawPath(path, color = tint, style = Fill)
    }
}

@Composable
fun FlameStreakIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    tint: Color = Color(0xFFFFB74D)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val scale = w / 24f

        val path = Path().apply {
            moveTo(12f * scale, 3f * scale)
            cubicTo(
                13.5f * scale, 7f * scale,
                18f * scale, 9.5f * scale,
                18f * scale, 14f * scale
            )
            cubicTo(
                18f * scale, 18.5f * scale,
                14.5f * scale, 21f * scale,
                12f * scale, 21f * scale
            )
            cubicTo(
                9.5f * scale, 21f * scale,
                6f * scale, 18.5f * scale,
                6f * scale, 14f * scale
            )
            cubicTo(
                6f * scale, 10f * scale,
                9f * scale, 6.5f * scale,
                12f * scale, 3f * scale
            )
            close()
        }
        drawPath(path, color = tint, style = Fill)

        // Inner flame
        val innerPath = Path().apply {
            moveTo(12f * scale, 11f * scale)
            cubicTo(
                13.5f * scale, 13f * scale,
                15f * scale, 14.5f * scale,
                15f * scale, 16.5f * scale
            )
            cubicTo(
                15f * scale, 18.5f * scale,
                13.5f * scale, 19.5f * scale,
                12f * scale, 19.5f * scale
            )
            cubicTo(
                10.5f * scale, 19.5f * scale,
                9f * scale, 18.5f * scale,
                9f * scale, 16.5f * scale
            )
            cubicTo(
                9f * scale, 14.5f * scale,
                10.5f * scale, 13f * scale,
                12f * scale, 11f * scale
            )
            close()
        }
        drawPath(innerPath, color = Color(0xFFFFEB3B), style = Fill)
    }
}

@Composable
fun ShieldSuccessIcon(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val scale = w / 64f

        val shieldPath = Path().apply {
            moveTo(32f * scale, 8f * scale)
            lineTo(54f * scale, 16f * scale)
            cubicTo(54f * scale, 38f * scale, 42f * scale, 50f * scale, 32f * scale, 56f * scale)
            cubicTo(22f * scale, 50f * scale, 10f * scale, 38f * scale, 10f * scale, 16f * scale)
            close()
        }
        drawPath(shieldPath, color = tint.copy(alpha = 0.15f), style = Fill)
        drawPath(shieldPath, color = tint, style = Stroke(width = 3.5f * scale, cap = StrokeCap.Round, join = StrokeJoin.Round))

        // Checkmark inside shield
        val checkPath = Path().apply {
            moveTo(24f * scale, 32f * scale)
            lineTo(29f * scale, 37f * scale)
            lineTo(40f * scale, 24f * scale)
        }
        drawPath(checkPath, color = tint, style = Stroke(width = 4f * scale, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
}

@Composable
fun TargetDialIllustration(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val scale = w / 80f
        val center = Offset(w / 2f, h / 2f)

        // Outer circular dial
        drawCircle(
            color = tint.copy(alpha = 0.2f),
            radius = 34f * scale,
            center = center
        )
        drawCircle(
            color = tint,
            radius = 34f * scale,
            center = center,
            style = Stroke(width = 3.5f * scale)
        )

        // Arc progress
        drawArc(
            color = Color(0xFFFFD54F),
            startAngle = -90f,
            sweepAngle = 240f,
            useCenter = false,
            topLeft = Offset(center.x - 34f * scale, center.y - 34f * scale),
            size = Size(68f * scale, 68f * scale),
            style = Stroke(width = 4.5f * scale, cap = StrokeCap.Round)
        )

        // Center dot & clock hands
        drawCircle(color = tint, radius = 4f * scale, center = center)
        drawLine(tint, center, Offset(center.x, center.y - 18f * scale), 3.5f * scale, StrokeCap.Round)
        drawLine(tint, center, Offset(center.x + 12f * scale, center.y + 6f * scale), 3.5f * scale, StrokeCap.Round)
    }
}
