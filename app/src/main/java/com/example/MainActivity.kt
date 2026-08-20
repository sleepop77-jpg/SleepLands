package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.AuthRepository
import com.example.data.PomodoroTimerManager
import com.example.ui.auth.AuthScreen
import com.example.ui.intro.IntroScreen
import com.example.ui.theme.StudyOSTheme
import com.example.ui.timer.PomodoroTimerScreen

enum class AppScreen {
    INTRO,
    AUTH,
    TIMER
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authRepository = AuthRepository(applicationContext)

        setContent {
            StudyOSTheme {
                val coroutineScope = rememberCoroutineScope()
                val timerManager = remember { PomodoroTimerManager(coroutineScope) }
                val currentUser by authRepository.currentUser.collectAsState()

                var currentScreen by remember {
                    mutableStateOf(
                        if (currentUser != null) AppScreen.TIMER else AppScreen.INTRO
                    )
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(350))
                                .togetherWith(fadeOut(animationSpec = tween(250)))
                        },
                        label = "screen_transition"
                    ) { screen ->
                        when (screen) {
                            AppScreen.INTRO -> {
                                IntroScreen(
                                    onFinish = {
                                        currentScreen = if (currentUser != null) AppScreen.TIMER else AppScreen.AUTH
                                    }
                                )
                            }
                            AppScreen.AUTH -> {
                                AuthScreen(
                                    authRepository = authRepository,
                                    onAuthSuccess = {
                                        currentScreen = AppScreen.TIMER
                                    },
                                    onBackToIntro = {
                                        currentScreen = AppScreen.INTRO
                                    }
                                )
                            }
                            AppScreen.TIMER -> {
                                PomodoroTimerScreen(
                                    timerManager = timerManager,
                                    authRepository = authRepository,
                                    onNavigateToIntro = {
                                        currentScreen = AppScreen.INTRO
                                    },
                                    onNavigateToAuth = {
                                        currentScreen = AppScreen.AUTH
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

