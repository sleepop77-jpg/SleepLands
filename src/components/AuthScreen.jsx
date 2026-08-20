import React, { useState } from 'react'
import AmbientBackground from './AmbientBackground'

export default function AuthScreen({ onAuthSuccess, onBackToIntro }) {
  const [isSignUp, setIsSignUp] = useState(false)
  const [name, setName] = useState('')
  const [email, setEmail] = useState('kartitk2121@gmail.com')
  const [password, setPassword] = useState('password123')
  const [showPassword, setShowPassword] = useState(false)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleSubmit = (e) => {
    e?.preventDefault()
    setError('')

    const cleanEmail = email.trim().toLowerCase()
    if (!cleanEmail || !cleanEmail.includes('@')) {
      setError('Please enter a valid email address')
      return
    }

    if (password.length < 4) {
      setError('Password must be at least 4 characters')
      return
    }

    if (isSignUp && !name.trim()) {
      setError('Please enter your name')
      return
    }

    setLoading(true)
    setTimeout(() => {
      setLoading(false)
      const user = {
        name: isSignUp ? name.trim() : (cleanEmail.includes('kartitk') ? 'Kartik' : cleanEmail.split('@')[0]),
        email: cleanEmail,
        isGuest: false
      }
      localStorage.setItem('studyos_user', JSON.stringify(user))
      onAuthSuccess(user)
    }, 400)
  }

  const handleGuestLogin = () => {
    const guestUser = {
      name: 'Guest Explorer',
      email: 'guest@studyos.local',
      isGuest: true
    }
    localStorage.setItem('studyos_user', JSON.stringify(guestUser))
    onAuthSuccess(guestUser)
  }

  const handleDemoLogin = () => {
    setEmail('kartitk2121@gmail.com')
    setPassword('password123')
    const demoUser = {
      name: 'Kartik',
      email: 'kartitk2121@gmail.com',
      isGuest: false
    }
    localStorage.setItem('studyos_user', JSON.stringify(demoUser))
    onAuthSuccess(demoUser)
  }

  return (
    <AmbientBackground>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
          alignItems: 'center',
          height: '100%',
          padding: '20px 20px',
          boxSizing: 'border-box',
          maxWidth: '440px',
          margin: '0 auto',
          overflowY: 'auto'
        }}
      >
        {/* Header Bar */}
        <div
          style={{
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
            width: '100%',
            paddingTop: '8px'
          }}
        >
          <button
            onClick={onBackToIntro}
            style={{
              background: 'none',
              border: 'none',
              color: 'rgba(255,255,255,0.85)',
              fontSize: '14px',
              fontWeight: 600,
              cursor: 'pointer'
            }}
          >
            ← Introduction
          </button>

          <div
            style={{
              backgroundColor: 'rgba(255, 255, 255, 0.2)',
              padding: '4px 10px',
              borderRadius: '12px',
              fontSize: '11px',
              fontWeight: 700,
              letterSpacing: '1px',
              color: '#FFFFFF'
            }}
          >
            SECURE AUTH
          </div>
        </div>

        {/* Card Form */}
        <div
          style={{
            width: '100%',
            backgroundColor: '#FFFFFF',
            borderRadius: '24px',
            padding: '24px 22px',
            boxSizing: 'border-box',
            boxShadow: '0 10px 30px rgba(0,0,0,0.18)',
            margin: '16px 0'
          }}
        >
          <h2 style={{ fontSize: '22px', fontWeight: 800, color: '#2B1818', textAlign: 'center', marginBottom: '4px' }}>
            {isSignUp ? 'Create Account' : 'Welcome Back'}
          </h2>
          <p style={{ fontSize: '13px', color: '#7A6463', textAlign: 'center', marginBottom: '18px' }}>
            {isSignUp ? 'Sign up to start earning study points' : 'Sign in to access your study history'}
          </p>

          {/* Toggle Pills */}
          <div
            style={{
              display: 'flex',
              backgroundColor: '#F3E7E5',
              borderRadius: '20px',
              padding: '4px',
              marginBottom: '18px'
            }}
          >
            <button
              onClick={() => {
                setIsSignUp(false)
                setError('')
              }}
              style={{
                flex: 1,
                padding: '8px 0',
                border: 'none',
                borderRadius: '16px',
                backgroundColor: !isSignUp ? '#D35D59' : 'transparent',
                color: !isSignUp ? '#FFFFFF' : '#2B1818',
                fontSize: '13.5px',
                fontWeight: 700,
                cursor: 'pointer',
                transition: 'all 0.2s ease'
              }}
            >
              Sign In
            </button>
            <button
              onClick={() => {
                setIsSignUp(true)
                setError('')
              }}
              style={{
                flex: 1,
                padding: '8px 0',
                border: 'none',
                borderRadius: '16px',
                backgroundColor: isSignUp ? '#D35D59' : 'transparent',
                color: isSignUp ? '#FFFFFF' : '#2B1818',
                fontSize: '13.5px',
                fontWeight: 700,
                cursor: 'pointer',
                transition: 'all 0.2s ease'
              }}
            >
              Create Account
            </button>
          </div>

          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            {isSignUp && (
              <div>
                <label style={{ display: 'block', fontSize: '12px', fontWeight: 600, color: '#4A2525', marginBottom: '4px' }}>
                  Full Name
                </label>
                <input
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Enter your name"
                  style={{
                    width: '100%',
                    padding: '12px 14px',
                    borderRadius: '12px',
                    border: '1.5px solid #E5D5D3',
                    backgroundColor: '#FFF7F6',
                    fontSize: '14px',
                    color: '#2B1818',
                    outline: 'none',
                    boxSizing: 'border-box'
                  }}
                />
              </div>
            )}

            <div>
              <label style={{ display: 'block', fontSize: '12px', fontWeight: 600, color: '#4A2525', marginBottom: '4px' }}>
                Email Address
              </label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="name@domain.com"
                style={{
                  width: '100%',
                  padding: '12px 14px',
                  borderRadius: '12px',
                  border: '1.5px solid #E5D5D3',
                  backgroundColor: '#FFF7F6',
                  fontSize: '14px',
                  color: '#2B1818',
                  outline: 'none',
                  boxSizing: 'border-box'
                }}
              />
            </div>

            <div>
              <label style={{ display: 'block', fontSize: '12px', fontWeight: 600, color: '#4A2525', marginBottom: '4px' }}>
                Password
              </label>
              <div style={{ position: 'relative' }}>
                <input
                  type={showPassword ? 'text' : 'password'}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Password"
                  style={{
                    width: '100%',
                    padding: '12px 42px 12px 14px',
                    borderRadius: '12px',
                    border: '1.5px solid #E5D5D3',
                    backgroundColor: '#FFF7F6',
                    fontSize: '14px',
                    color: '#2B1818',
                    outline: 'none',
                    boxSizing: 'border-box'
                  }}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  style={{
                    position: 'absolute',
                    right: '12px',
                    top: '50%',
                    transform: 'translateY(-50%)',
                    background: 'none',
                    border: 'none',
                    color: '#8D7574',
                    fontSize: '12px',
                    cursor: 'pointer',
                    fontWeight: 600
                  }}
                >
                  {showPassword ? 'Hide' : 'Show'}
                </button>
              </div>
            </div>

            {error && (
              <div style={{ color: '#D32F2F', fontSize: '12.5px', textAlign: 'center', marginTop: '2px' }}>
                {error}
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              style={{
                width: '100%',
                padding: '14px 0',
                borderRadius: '24px',
                backgroundColor: '#D35D59',
                color: '#FFFFFF',
                border: 'none',
                fontSize: '15px',
                fontWeight: 700,
                cursor: 'pointer',
                marginTop: '6px',
                boxShadow: '0 4px 12px rgba(211, 93, 89, 0.35)'
              }}
            >
              {loading ? 'Authenticating...' : isSignUp ? 'Create Account' : 'Sign In'}
            </button>

            <button
              type="button"
              onClick={handleDemoLogin}
              style={{
                width: '100%',
                padding: '11px 0',
                borderRadius: '24px',
                backgroundColor: '#FFF0EE',
                color: '#D35D59',
                border: 'none',
                fontSize: '13.5px',
                fontWeight: 600,
                cursor: 'pointer'
              }}
            >
              Instant Demo Login (Kartik)
            </button>
          </form>
        </div>

        {/* Guest access */}
        <button
          onClick={handleGuestLogin}
          style={{
            background: 'none',
            border: 'none',
            color: '#FFFFFF',
            fontSize: '14px',
            fontWeight: 600,
            cursor: 'pointer',
            padding: '8px 16px'
          }}
        >
          Continue as Guest Explorer
        </button>
      </div>
    </AmbientBackground>
  )
}
