package com.example.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class TimerMode(val title: String, val durationSeconds: Int) {
    POMODORO("Pomodoro", 25 * 60),
    SHORT_BREAK("Short Break", 5 * 60),
    LONG_BREAK("Long Break", 15 * 60),
    LOOP("Loop", 25 * 60)
}

data class TimerUiState(
    val currentMode: TimerMode = TimerMode.POMODORO,
    val isLoopEnabled: Boolean = false,
    val totalSeconds: Int = 25 * 60,
    val remainingSeconds: Int = 24 * 60 + 56, // Initial state matching reference "24:56"
    val isRunning: Boolean = false,
    val roundsCompleted: Int = 2,
    val roundsTarget: Int = 4,
    val goalsCompleted: Int = 0,
    val goalsTarget: Int = 15,
    val todayCompleted: Int = 0,
    val lifetimeCompleted: Int = 0,
    val isFullscreen: Boolean = false
) {
    val progress: Float
        get() = if (totalSeconds > 0) 1f - (remainingSeconds.toFloat() / totalSeconds.toFloat()) else 0f

    val formattedTime: String
        get() {
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            return "%02d:%02d".format(minutes, seconds)
        }
}

class PomodoroTimerManager(
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(TimerUiState())
    val state: StateFlow<TimerUiState> = _state.asStateFlow()

    private var timerJob: Job? = null

    fun togglePlayPause() {
        if (_state.value.isRunning) {
            pause()
        } else {
            start()
        }
    }

    fun start() {
        if (_state.value.isRunning) return
        _state.update { it.copy(isRunning = true) }

        timerJob?.cancel()
        timerJob = scope.launch {
            while (_state.value.isRunning && _state.value.remainingSeconds > 0) {
                delay(1000L)
                _state.update { current ->
                    if (current.remainingSeconds > 1) {
                        current.copy(remainingSeconds = current.remainingSeconds - 1)
                    } else {
                        // Timer completed
                        onTimerFinished(current)
                    }
                }
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
        _state.update { it.copy(isRunning = false) }
    }

    fun reset() {
        pause()
        _state.update { current ->
            current.copy(
                remainingSeconds = current.currentMode.durationSeconds
            )
        }
    }

    fun selectMode(mode: TimerMode) {
        if (mode == TimerMode.LOOP) {
            // Toggle loop mode
            val newLoop = !_state.value.isLoopEnabled
            _state.update { it.copy(isLoopEnabled = newLoop) }
            return
        }

        pause()
        _state.update { current ->
            current.copy(
                currentMode = mode,
                totalSeconds = mode.durationSeconds,
                remainingSeconds = mode.durationSeconds
            )
        }
    }

    fun toggleFullscreen() {
        _state.update { it.copy(isFullscreen = !it.isFullscreen) }
    }

    private fun onTimerFinished(current: TimerUiState): TimerUiState {
        val wasPomodoro = current.currentMode == TimerMode.POMODORO
        val newRounds = if (wasPomodoro) current.roundsCompleted + 1 else current.roundsCompleted
        val newToday = if (wasPomodoro) current.todayCompleted + 1 else current.todayCompleted
        val newLifetime = if (wasPomodoro) current.lifetimeCompleted + 1 else current.lifetimeCompleted
        val newGoals = if (wasPomodoro) current.goalsCompleted + 1 else current.goalsCompleted

        if (!current.isLoopEnabled) {
            return current.copy(
                isRunning = false,
                remainingSeconds = 0,
                roundsCompleted = newRounds,
                todayCompleted = newToday,
                lifetimeCompleted = newLifetime,
                goalsCompleted = newGoals
            )
        }

        // Loop mode auto-transition
        val nextMode = if (wasPomodoro) {
            if (newRounds % 4 == 0) TimerMode.LONG_BREAK else TimerMode.SHORT_BREAK
        } else {
            TimerMode.POMODORO
        }

        return current.copy(
            isRunning = true,
            currentMode = nextMode,
            totalSeconds = nextMode.durationSeconds,
            remainingSeconds = nextMode.durationSeconds,
            roundsCompleted = newRounds,
            todayCompleted = newToday,
            lifetimeCompleted = newLifetime,
            goalsCompleted = newGoals
        )
    }
}
