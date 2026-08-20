import React, { useState, useEffect, useRef } from 'react'
import AmbientBackground from './AmbientBackground'
import MascotCharacter from './MascotCharacter'
import { FullscreenIcon, PlayPauseIcon, ReloadIcon } from './SvgIcons'

const TIMER_MODES = {
  POMODORO: { title: 'Pomodoro', duration: 25 * 60 },
  SHORT_BREAK: { title: 'Short Break', duration: 5 * 60 },
  LONG_BREAK: { title: 'Long Break', duration: 15 * 60 },
  LOOP: { title: 'Loop', duration: 25 * 60 }
}

export default function PomodoroTimerScreen({ user, onNavigateToIntro, onNavigateToAuth }) {
  const [mode, setMode] = useState('POMODORO')
  const [isLoop, setIsLoop] = useState(false)
  const [totalSeconds, setTotalSeconds] = useState(25 * 60)
  const [remainingSeconds, setRemainingSeconds] = useState(24 * 60 + 56) // Initial 24:56 matching screenshot
  const [isRunning, setIsRunning] = useState(false)
  const [isFullscreen, setIsFullscreen] = useState(false)

  // 4 Stats
  const [roundsCompleted, setRoundsCompleted] = useState(2)
  const [roundsTarget] = useState(4)
  const [goalsCompleted, setGoalsCompleted] = useState(0)
  const [goalsTarget] = useState(15)
  const [todayCompleted, setTodayCompleted] = useState(0)
  const [lifetimeCompleted, setLifetimeCompleted] = useState(0)

  const timerRef = useRef(null)

  // Timer Tick Engine
  useEffect(() => {
    if (isRunning) {
      timerRef.current = setInterval(() => {
        setRemainingSeconds((prev) => {
          if (prev <= 1) {
            // Finished
            handleTimerComplete()
            return 0
          }
          return prev - 1
        })
      }, 1000)
    } else {
      if (timerRef.current) clearInterval(timerRef.current)
    }

    return () => {
      if (timerRef.current) clearInterval(timerRef.current)
    }
  }, [isRunning, mode, isLoop])

  const handleTimerComplete = () => {
    if (mode === 'POMODORO') {
      const nextRounds = roundsCompleted + 1
      setRoundsCompleted(nextRounds)
      setTodayCompleted((t) => t + 1)
      setLifetimeCompleted((l) => l + 1)
      setGoalsCompleted((g) => g + 1)

      if (isLoop) {
        if (nextRounds % 4 === 0) {
          switchMode('LONG_BREAK', true)
        } else {
          switchMode('SHORT_BREAK', true)
        }
      } else {
        setIsRunning(false)
      }
    } else {
      if (isLoop) {
        switchMode('POMODORO', true)
      } else {
        setIsRunning(false)
      }
    }
  }

  const switchMode = (newMode, autoStart = false) => {
    if (newMode === 'LOOP') {
      setIsLoop((prev) => !prev)
      return
    }

    setIsRunning(autoStart)
    setMode(newMode)
    const duration = TIMER_MODES[newMode].duration
    setTotalSeconds(duration)
    setRemainingSeconds(duration)
  }

  const togglePlayPause = () => {
    setIsRunning((prev) => !prev)
  }

  const handleReset = () => {
    setIsRunning(false)
    const duration = TIMER_MODES[mode].duration
    setRemainingSeconds(duration)
  }

  const toggleFullscreen = () => {
    setIsFullscreen((prev) => !prev)
  }

  // Format mm:ss
  const minutes = Math.floor(remainingSeconds / 60)
  const seconds = remainingSeconds % 60
  const formattedTime = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`

  // Progress 0..1
  const progress = totalSeconds > 0 ? (totalSeconds - remainingSeconds) / totalSeconds : 0

  return (
    <AmbientBackground>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
          alignItems: 'center',
          height: '100%',
          padding: isFullscreen ? '16px 20px' : '10px 20px 14px',
          boxSizing: 'border-box',
          maxWidth: '430px',
          margin: '0 auto',
          position: 'relative',
          userSelect: 'none'
        }}
      >
        {/* Top Section */}
        <div style={{ width: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          {/* Drag Handle from reference */}
          <div
            style={{
              width: '36px',
              height: '4px',
              borderRadius: '2px',
              backgroundColor: 'rgba(255, 255, 255, 0.85)',
              marginTop: '4px',
              marginBottom: '6px'
            }}
          />

          {!isFullscreen && (
            <div
              style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                width: '100%',
                padding: '2px 4px 6px'
              }}
            >
              <div
                onClick={onNavigateToAuth}
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  gap: '6px',
                  backgroundColor: 'rgba(255, 255, 255, 0.16)',
                  padding: '4px 10px',
                  borderRadius: '12px',
                  cursor: 'pointer',
                  fontSize: '12px',
                  fontWeight: 600,
                  color: '#FFFFFF'
                }}
              >
                <div style={{ width: '7px', height: '7px', borderRadius: '50%', backgroundColor: '#66BB6A' }} />
                <span>{user?.name || 'Guest'}</span>
              </div>

              <button
                onClick={onNavigateToIntro}
                style={{
                  background: 'none',
                  border: 'none',
                  color: 'rgba(255, 255, 255, 0.8)',
                  fontSize: '13px',
                  fontWeight: 600,
                  cursor: 'pointer'
                }}
              >
                Guide
              </button>
            </div>
          )}
        </div>

        {/* 4-Stat Column Row (Exact layout from reference) */}
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(4, 1fr)',
            width: '100%',
            textAlign: 'center',
            padding: '2px 0 6px'
          }}
        >
          {/* Rounds */}
          <div>
            <div style={{ fontSize: '11px', color: 'rgba(255, 255, 255, 0.8)', fontWeight: 500 }}>Rounds</div>
            <div style={{ fontSize: '18px', fontWeight: 800, color: '#FFFFFF', margin: '2px 0' }}>
              {roundsCompleted}/{roundsTarget}
            </div>
            <div style={{ fontSize: '9.5px', color: 'rgba(255, 255, 255, 0.75)' }}>Pomodoros</div>
          </div>

          {/* Goals */}
          <div>
            <div style={{ fontSize: '11px', color: 'rgba(255, 255, 255, 0.8)', fontWeight: 500 }}>Goals</div>
            <div style={{ fontSize: '18px', fontWeight: 800, color: '#FFFFFF', margin: '2px 0' }}>
              {goalsCompleted}/{goalsTarget}
            </div>
            <div style={{ fontSize: '9.5px', color: 'rgba(255, 255, 255, 0.75)' }}>Pomodoros</div>
          </div>

          {/* Today */}
          <div>
            <div style={{ fontSize: '11px', color: 'rgba(255, 255, 255, 0.8)', fontWeight: 500 }}>Today</div>
            <div style={{ fontSize: '18px', fontWeight: 800, color: '#FFFFFF', margin: '2px 0' }}>
              {todayCompleted}
            </div>
            <div style={{ fontSize: '9.5px', color: 'rgba(255, 255, 255, 0.75)' }}>Pomodoros</div>
          </div>

          {/* Lifetime */}
          <div>
            <div style={{ fontSize: '11px', color: 'rgba(255, 255, 255, 0.8)', fontWeight: 500 }}>Lifetime</div>
            <div style={{ fontSize: '18px', fontWeight: 800, color: '#FFFFFF', margin: '2px 0' }}>
              {lifetimeCompleted}
            </div>
            <div style={{ fontSize: '9.5px', color: 'rgba(255, 255, 255, 0.75)' }}>Pomodoros</div>
          </div>
        </div>

        {/* Center Semi-Circle Progress Arch with Mascot */}
        <div
          style={{
            position: 'relative',
            width: '240px',
            height: '210px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            margin: '2px 0'
          }}
        >
          {/* Arch SVG */}
          <svg
            viewBox="0 0 240 180"
            width="240"
            height="180"
            style={{ position: 'absolute', top: 0, left: 0, overflow: 'visible' }}
          >
            {/* Background Arch Track */}
            <path
              d="M 20 160 A 100 100 0 0 1 220 160"
              fill="none"
              stroke="rgba(255, 255, 255, 0.95)"
              strokeWidth="4"
              strokeLinecap="round"
            />

            {/* Dynamic Progress Stroke */}
            {progress > 0 && (
              <path
                d="M 20 160 A 100 100 0 0 1 220 160"
                fill="none"
                stroke="#FFD54F"
                strokeWidth="5.5"
                strokeLinecap="round"
                strokeDasharray="314.159"
                strokeDashoffset={314.159 * (1 - progress)}
                style={{ transition: 'stroke-dashoffset 0.5s linear' }}
              />
            )}
          </svg>

          {/* Mascot in the Center */}
          <div style={{ marginTop: '16px' }}>
            <MascotCharacter isRunning={isRunning} size={175} />
          </div>
        </div>

        {/* Big Countdown Timer Display (e.g. 24:56) */}
        <div
          style={{
            fontSize: '48px',
            fontWeight: 800,
            color: '#FFFFFF',
            letterSpacing: '2px',
            textAlign: 'center',
            lineHeight: 1,
            margin: '2px 0 10px'
          }}
        >
          {formattedTime}
        </div>

        {/* Control Buttons Row (Fullscreen, Play/Pause, Reset) */}
        <div
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            width: '210px',
            marginBottom: '14px'
          }}
        >
          {/* Fullscreen Toggle */}
          <button
            onClick={toggleFullscreen}
            style={{
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              padding: '6px'
            }}
          >
            <FullscreenIcon isFullscreen={isFullscreen} size={24} color="#FFFFFF" />
          </button>

          {/* Play / Pause Primary Button */}
          <button
            onClick={togglePlayPause}
            style={{
              width: '56px',
              height: '56px',
              borderRadius: '50%',
              backgroundColor: 'rgba(255, 255, 255, 0.2)',
              border: 'none',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              cursor: 'pointer',
              transition: 'transform 0.1s ease',
              boxShadow: '0 4px 12px rgba(0,0,0,0.1)'
            }}
            onMouseDown={(e) => (e.currentTarget.style.transform = 'scale(0.94)')}
            onMouseUp={(e) => (e.currentTarget.style.transform = 'scale(1)')}
          >
            <PlayPauseIcon isRunning={isRunning} size={30} color="#FFFFFF" />
          </button>

          {/* Reset Reload Button */}
          <button
            onClick={handleReset}
            style={{
              background: 'none',
              border: 'none',
              cursor: 'pointer',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              padding: '6px'
            }}
          >
            <ReloadIcon size={24} color="#FFFFFF" />
          </button>
        </div>

        {/* 2x2 Grid Mode Pills (Matches reference screenshot) */}
        <div
          style={{
            display: 'grid',
            gridTemplateColumns: '1fr 1fr',
            gap: '10px',
            width: '100%',
            paddingBottom: '6px'
          }}
        >
          {/* Pomodoro */}
          <button
            onClick={() => switchMode('POMODORO')}
            style={{
              height: '46px',
              borderRadius: '23px',
              backgroundColor: mode === 'POMODORO' ? '#FFFFFF' : 'transparent',
              color: mode === 'POMODORO' ? '#D35D59' : '#FFFFFF',
              border: '1.5px solid ' + (mode === 'POMODORO' ? '#FFFFFF' : 'rgba(255, 255, 255, 0.8)'),
              fontSize: '14px',
              fontWeight: 700,
              cursor: 'pointer',
              transition: 'all 0.2s ease'
            }}
          >
            Pomodoro
          </button>

          {/* Short Break */}
          <button
            onClick={() => switchMode('SHORT_BREAK')}
            style={{
              height: '46px',
              borderRadius: '23px',
              backgroundColor: mode === 'SHORT_BREAK' ? '#FFFFFF' : 'transparent',
              color: mode === 'SHORT_BREAK' ? '#D35D59' : '#FFFFFF',
              border: '1.5px solid ' + (mode === 'SHORT_BREAK' ? '#FFFFFF' : 'rgba(255, 255, 255, 0.8)'),
              fontSize: '14px',
              fontWeight: 700,
              cursor: 'pointer',
              transition: 'all 0.2s ease'
            }}
          >
            Short Break
          </button>

          {/* Long Break */}
          <button
            onClick={() => switchMode('LONG_BREAK')}
            style={{
              height: '46px',
              borderRadius: '23px',
              backgroundColor: mode === 'LONG_BREAK' ? '#FFFFFF' : 'transparent',
              color: mode === 'LONG_BREAK' ? '#D35D59' : '#FFFFFF',
              border: '1.5px solid ' + (mode === 'LONG_BREAK' ? '#FFFFFF' : 'rgba(255, 255, 255, 0.8)'),
              fontSize: '14px',
              fontWeight: 700,
              cursor: 'pointer',
              transition: 'all 0.2s ease'
            }}
          >
            Long Break
          </button>

          {/* Loop */}
          <button
            onClick={() => switchMode('LOOP')}
            style={{
              height: '46px',
              borderRadius: '23px',
              backgroundColor: isLoop ? '#FFFFFF' : 'transparent',
              color: isLoop ? '#D35D59' : '#FFFFFF',
              border: '1.5px solid ' + (isLoop ? '#FFFFFF' : 'rgba(255, 255, 255, 0.8)'),
              fontSize: '14px',
              fontWeight: 700,
              cursor: 'pointer',
              transition: 'all 0.2s ease'
            }}
          >
            {isLoop ? 'Loop (ON)' : 'Loop'}
          </button>
        </div>
      </div>
    </AmbientBackground>
  )
}
