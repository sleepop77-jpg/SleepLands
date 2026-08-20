import React, { useState, useEffect } from 'react'
import IntroScreen from './components/IntroScreen'
import AuthScreen from './components/AuthScreen'
import PomodoroTimerScreen from './components/PomodoroTimerScreen'

export default function App() {
  const [user, setUser] = useState(() => {
    try {
      const saved = localStorage.getItem('studyos_user')
      return saved ? JSON.parse(saved) : null
    } catch {
      return null
    }
  })

  const [currentScreen, setCurrentScreen] = useState(() => {
    // If already logged in, show timer directly, otherwise intro
    try {
      const saved = localStorage.getItem('studyos_user')
      return saved ? 'TIMER' : 'INTRO'
    } catch {
      return 'INTRO'
    }
  })

  return (
    <div
      style={{
        width: '100vw',
        height: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundColor: '#1E1212',
        overflow: 'hidden'
      }}
    >
      {/* Mobile Device Frame Container */}
      <div
        style={{
          width: '100%',
          maxWidth: '430px',
          height: '100%',
          maxHeight: '880px',
          backgroundColor: '#D35D59',
          position: 'relative',
          overflow: 'hidden',
          boxShadow: '0 20px 50px rgba(0,0,0,0.5)',
          borderRadius: window.innerWidth > 500 ? '36px' : '0px',
          border: window.innerWidth > 500 ? '4px solid #4A2525' : 'none'
        }}
      >
        {currentScreen === 'INTRO' && (
          <IntroScreen
            onFinish={() => {
              if (user) {
                setCurrentScreen('TIMER')
              } else {
                setCurrentScreen('AUTH')
              }
            }}
          />
        )}

        {currentScreen === 'AUTH' && (
          <AuthScreen
            onAuthSuccess={(authedUser) => {
              setUser(authedUser)
              setCurrentScreen('TIMER')
            }}
            onBackToIntro={() => setCurrentScreen('INTRO')}
          />
        )}

        {currentScreen === 'TIMER' && (
          <PomodoroTimerScreen
            user={user}
            onNavigateToIntro={() => setCurrentScreen('INTRO')}
            onNavigateToAuth={() => setCurrentScreen('AUTH')}
          />
        )}
      </div>
    </div>
  )
}
