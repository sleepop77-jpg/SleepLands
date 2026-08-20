package com.example.ui.intro

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AmbientBackground
import com.example.ui.components.MascotCharacter
import com.example.ui.components.ShieldSuccessIcon
import com.example.ui.components.StarBadgeIcon
import com.example.ui.components.TargetDialIllustration
import com.example.ui.theme.CoralPrimary
import com.example.ui.theme.TextDark
import com.example.ui.theme.TextWhite
import com.example.ui.theme.TextWhiteMuted

data class IntroSlide(
    val title: String,
    val subtitle: String,
    val description: String,
    val badge: String
)

@Composable
fun IntroScreen(
    onFinish: () -> Unit
) {
    var currentStep by remember { mutableIntStateOf(0) }

    val slides = listOf(
        IntroSlide(
            title = "Precision Focus Cycles",
            subtitle = "25-Minute Deep Flow",
            description = "Harness structured Pomodoro intervals with adaptive rest periods. Your animated focus companion keeps you locked in and distraction-free.",
            badge = "STEP 1 - FOCUS"
        ),
        IntroSlide(
            title = "Gamified Fame & Momentum",
            subtitle = "Earn Points Every Minute",
            description = "Gain +2 Fame for every uninterrupted study minute. Level up your daily mastery, avoid procrastination traps, and celebrate real progress.",
            badge = "STEP 2 - ECONOMY"
        ),
        IntroSlide(
            title = "Live Stats & Milestones",
            subtitle = "Track Rounds, Goals & Streaks",
            description = "Monitor your completed session rounds, daily targets, and lifetime achievements inside a clean, live, animated operating system.",
            badge = "STEP 3 - MASTERY"
        )
    )

    AmbientBackground(baseColor = CoralPrimary) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top App Title & Skip button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "StudyOS",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                TextButton(
                    onClick = onFinish,
                    modifier = Modifier.testTag("skip_intro_button")
                ) {
                    Text(
                        text = "Skip",
                        color = TextWhiteMuted,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Animated Visual Illustration
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(400)) + slideInHorizontally { width -> if (targetState > initialState) width else -width })
                            .togetherWith(fadeOut(animationSpec = tween(300)) + slideOutHorizontally { width -> if (targetState > initialState) -width else width })
                    },
                    label = "intro_visual"
                ) { step ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (step) {
                            0 -> {
                                MascotCharacter(
                                    size = 180.dp,
                                    isRunning = true
                                )
                            }
                            1 -> {
                                Box(
                                    modifier = Modifier.size(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ShieldSuccessIcon(size = 120.dp, tint = Color.White)
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                    ) {
                                        StarBadgeIcon(size = 32.dp)
                                    }
                                }
                            }
                            else -> {
                                Box(
                                    modifier = Modifier.size(170.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    TargetDialIllustration(size = 130.dp, tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            // Animated Text Content
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(350)) + slideInHorizontally { width -> if (targetState > initialState) width / 2 else -width / 2 })
                            .togetherWith(fadeOut(animationSpec = tween(250)))
                    },
                    label = "intro_text"
                ) { step ->
                    val slide = slides[step]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Badge Pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = slide.badge,
                                color = TextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = slide.title,
                            color = TextWhite,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = slide.subtitle,
                            color = Color(0xFFFFD54F),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = slide.description,
                            color = TextWhiteMuted,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Page Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    slides.forEachIndexed { index, _ ->
                        val isSelected = index == currentStep
                        val widthAnim by animateDpAsState(
                            targetValue = if (isSelected) 26.dp else 8.dp,
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            label = "indicator_width"
                        )
                        val alphaAnim by animateFloatAsState(
                            targetValue = if (isSelected) 1f else 0.4f,
                            animationSpec = tween(300),
                            label = "indicator_alpha"
                        )

                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .height(8.dp)
                                .width(widthAnim)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = alphaAnim))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    currentStep = index
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action Button (Next or Get Started)
                Button(
                    onClick = {
                        if (currentStep < slides.size - 1) {
                            currentStep++
                        } else {
                            onFinish()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("intro_action_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = TextDark
                    ),
                    shape = RoundedCornerShape(27.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                ) {
                    Text(
                        text = if (currentStep == slides.size - 1) "Enter StudyOS" else "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
