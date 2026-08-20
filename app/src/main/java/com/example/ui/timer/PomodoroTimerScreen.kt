package com.example.ui.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AuthRepository
import com.example.data.PomodoroTimerManager
import com.example.data.TimerMode
import com.example.ui.components.AmbientBackground
import com.example.ui.components.FullscreenIcon
import com.example.ui.components.MascotCharacter
import com.example.ui.components.PlayPauseIcon
import com.example.ui.components.ReloadIcon
import com.example.ui.theme.CoralPrimary
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextWhite
import com.example.ui.theme.TextWhiteMuted

@Composable
fun PomodoroTimerScreen(
    timerManager: PomodoroTimerManager,
    authRepository: AuthRepository,
    onNavigateToIntro: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val uiState by timerManager.state.collectAsState()
    val currentUser by authRepository.currentUser.collectAsState()

    // Smooth animated arc progress
    val animatedProgress by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "arc_progress"
    )

    AmbientBackground(baseColor = CoralPrimary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // --- 1. Top Section (Pill Handle + Quick Bar) ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top drag/notch handle pill from the reference design
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(36.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.85f))
                )

                // Optional minimal user bar (can be toggled or compact)
                AnimatedVisibility(visible = !uiState.isFullscreen) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .clickable { onNavigateToAuth() }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User Profile",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentUser?.name ?: "Guest",
                                color = TextWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        IconButton(
                            onClick = onNavigateToIntro,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "App Introduction",
                                tint = TextWhiteMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // --- 2. Top Stats 4-Column Row (Matches reference screenshot) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (uiState.isFullscreen) 16.dp else 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Column 1: Rounds
                StatColumn(
                    title = "Rounds",
                    value = "${uiState.roundsCompleted}/${uiState.roundsTarget}",
                    subtitle = "Pomodoros",
                    tag = "stat_rounds"
                )

                // Column 2: Goals
                StatColumn(
                    title = "Goals",
                    value = "${uiState.goalsCompleted}/${uiState.goalsTarget}",
                    subtitle = "Pomodoros",
                    tag = "stat_goals"
                )

                // Column 3: Today
                StatColumn(
                    title = "Today",
                    value = "${uiState.todayCompleted}",
                    subtitle = "Pomodoros",
                    tag = "stat_today"
                )

                // Column 4: Lifetime
                StatColumn(
                    title = "Lifetime",
                    value = "${uiState.lifetimeCompleted}",
                    subtitle = "Pomodoros",
                    tag = "stat_lifetime"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- 3. Center Focus Mascot with Progress Arc (Exact match) ---
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .testTag("timer_mascot_container"),
                contentAlignment = Alignment.Center
            ) {
                // Background Arch & Active Progress Arch
                Canvas(modifier = Modifier.size(230.dp)) {
                    val w = size.width
                    val h = size.height
                    val strokeWidth = 3.5.dp.toPx()
                    val arcRadius = (w / 2f) - (strokeWidth / 2f)

                    val arcRect = androidx.compose.ui.geometry.Rect(
                        left = strokeWidth / 2f,
                        top = strokeWidth / 2f,
                        right = w - (strokeWidth / 2f),
                        bottom = h - (strokeWidth / 2f)
                    )

                    // Arch track (180 degrees semi-circle over the character)
                    drawArc(
                        color = Color.White.copy(alpha = 0.95f),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(arcRect.left, arcRect.top),
                        size = Size(arcRect.width, arcRect.height),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Dynamic highlighted progress indicator along the arc
                    if (animatedProgress > 0f) {
                        drawArc(
                            color = Color(0xFFFFD54F),
                            startAngle = 180f,
                            sweepAngle = 180f * animatedProgress,
                            useCenter = false,
                            topLeft = Offset(arcRect.left, arcRect.top),
                            size = Size(arcRect.width, arcRect.height),
                            style = Stroke(width = strokeWidth + 2f, cap = StrokeCap.Round)
                        )
                    }
                }

                // Animated Mascot Character
                MascotCharacter(
                    size = 175.dp,
                    isRunning = uiState.isRunning
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // --- 4. Digital Countdown Time (e.g. 24:56) ---
            Text(
                text = uiState.formattedTime,
                color = TextWhite,
                fontSize = 46.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("timer_display_text")
            )

            Spacer(modifier = Modifier.height(14.dp))

            // --- 5. Controls Row (Fullscreen, Play/Pause, Reset) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Fullscreen button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            timerManager.toggleFullscreen()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    FullscreenIcon(
                        size = 24.dp,
                        tint = Color.White,
                        isFullscreen = uiState.isFullscreen
                    )
                }

                // Play / Pause button (Primary center)
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.18f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            timerManager.togglePlayPause()
                        }
                        .testTag("play_pause_button"),
                    contentAlignment = Alignment.Center
                ) {
                    PlayPauseIcon(
                        isRunning = uiState.isRunning,
                        size = 32.dp,
                        tint = Color.White
                    )
                }

                // Reload / Reset button
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            timerManager.reset()
                        }
                        .testTag("reset_button"),
                    contentAlignment = Alignment.Center
                ) {
                    ReloadIcon(
                        size = 24.dp,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- 6. Bottom 2x2 Grid Mode Pills (Matches reference screenshot) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Row 1: Pomodoro & Short Break
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimerPillButton(
                        title = "Pomodoro",
                        isSelected = uiState.currentMode == TimerMode.POMODORO,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_pomodoro"),
                        onClick = { timerManager.selectMode(TimerMode.POMODORO) }
                    )

                    TimerPillButton(
                        title = "Short Break",
                        isSelected = uiState.currentMode == TimerMode.SHORT_BREAK,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_short_break"),
                        onClick = { timerManager.selectMode(TimerMode.SHORT_BREAK) }
                    )
                }

                // Row 2: Long Break & Loop
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TimerPillButton(
                        title = "Long Break",
                        isSelected = uiState.currentMode == TimerMode.LONG_BREAK,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_long_break"),
                        onClick = { timerManager.selectMode(TimerMode.LONG_BREAK) }
                    )

                    TimerPillButton(
                        title = if (uiState.isLoopEnabled) "Loop (ON)" else "Loop",
                        isSelected = uiState.isLoopEnabled,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_loop"),
                        onClick = { timerManager.selectMode(TimerMode.LOOP) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatColumn(
    title: String,
    value: String,
    subtitle: String,
    tag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.testTag(tag)
    ) {
        Text(
            text = title,
            color = TextWhiteMuted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(
            text = subtitle,
            color = TextWhiteMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun TimerPillButton(
    title: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(46.dp)
            .clip(RoundedCornerShape(23.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .border(
                width = 1.5.dp,
                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                shape = RoundedCornerShape(23.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) CoralPrimary else TextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
