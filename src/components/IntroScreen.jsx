import React, { useState } from 'react'
import AmbientBackground from './AmbientBackground'
import MascotCharacter from './MascotCharacter'
import { ShieldIcon, StarBadgeIcon, TargetDialIcon } from './SvgIcons'

export default function IntroScreen({ onFinish }) {
  const [currentStep, setCurrentStep] = useState(0)

  const slides = [
    {
      badge: 'STEP 1 · FOCUS CYCLES',
      title: 'Precision Focus Cycles',
      subtitle: '25-Minute Deep Flow',
      description: 'Harness structured Pomodoro intervals with adaptive rest periods. Your animated focus companion keeps you locked in and distraction-free.'
    },
    {
      badge: 'STEP 2 · ECONOMY',
      title: 'Gamified Fame & Momentum',
      subtitle: 'Earn Points Every Minute',
      description: 'Gain +2 Fame for every uninterrupted study minute. Level up your daily mastery, avoid procrastination traps, and celebrate real progress.'
    },
    {
      badge: 'STEP 3 · MASTERY',
      title: 'Live Stats & Milestones',
      subtitle: 'Track Rounds, Goals & Streaks',
      description: 'Monitor your completed session rounds, daily targets, and lifetime achievements inside a clean, live, animated operating system.'
    }
  ]

  const slide = slides[currentStep]

  return (
    <AmbientBackground>
      <div
        style={{
          display: 'flex',
          flexDirection: 'column',
          justifyContent: 'space-between',
          alignItems: 'center',
          height: '100%',
          padding: '24px 24px',
          boxSizing: 'border-box',
          maxWidth: '440px',
          margin: '0 auto',
          position: 'relative'
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
          <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <div style={{ width: '10px', height: '10px', borderRadius: '50%', backgroundColor: '#FFFFFF' }} />
            <span style={{ fontSize: '18px', fontWeight: 800, letterSpacing: '1px', color: '#FFFFFF' }}>StudyOS</span>
          </div>

          <button
            onClick={onFinish}
            style={{
              background: 'none',
              border: 'none',
              color: 'rgba(255,255,255,0.75)',
              fontSize: '14px',
              fontWeight: 600,
              cursor: 'pointer',
              padding: '6px 12px'
            }}
          >
            Skip
          </button>
        </div>

        {/* Center Animated Visual Graphic */}
        <div
          style={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            flex: 1,
            width: '100%',
            transition: 'all 0.4s ease'
          }}
        >
          {currentStep === 0 && <MascotCharacter isRunning={true} size={180} />}

          {currentStep === 1 && (
            <div style={{ position: 'relative', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <ShieldIcon size={120} color="#FFFFFF" />
              <div style={{ position: 'absolute', top: '-10px', right: '-10px' }}>
                <StarBadgeIcon size={36} />
              </div>
            </div>
          )}

          {currentStep === 2 && (
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
              <TargetDialIcon size={130} color="#FFFFFF" />
            </div>
          )}
        </div>

        {/* Bottom Narrative & Slide Controls */}
        <div style={{ width: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
          {/* Badge Pill */}
          <div
            style={{
              backgroundColor: 'rgba(255, 255, 255, 0.2)',
              padding: '6px 14px',
              borderRadius: '16px',
              fontSize: '11px',
              fontWeight: 700,
              letterSpacing: '1.2px',
              color: '#FFFFFF',
              marginBottom: '12px'
            }}
          >
            {slide.badge}
          </div>

          <h2 style={{ fontSize: '24px', fontWeight: 800, textAlign: 'center', color: '#FFFFFF', marginBottom: '4px' }}>
            {slide.title}
          </h2>

          <h4 style={{ fontSize: '15px', fontWeight: 600, textAlign: 'center', color: '#FFD54F', marginBottom: '10px' }}>
            {slide.subtitle}
          </h4>

          <p
            style={{
              fontSize: '13.5px',
              lineHeight: '1.45',
              textAlign: 'center',
              color: 'rgba(255, 255, 255, 0.85)',
              padding: '0 12px',
              marginBottom: '20px'
            }}
          >
            {slide.description}
          </p>

          {/* Step Indicators */}
          <div style={{ display: 'flex', gap: '8px', marginBottom: '22px' }}>
            {slides.map((_, idx) => (
              <div
                key={idx}
                onClick={() => setCurrentStep(idx)}
                style={{
                  width: idx === currentStep ? '28px' : '8px',
                  height: '8px',
                  borderRadius: '4px',
                  backgroundColor: idx === currentStep ? '#FFFFFF' : 'rgba(255,255,255,0.4)',
                  transition: 'all 0.3s ease',
                  cursor: 'pointer'
                }}
              />
            ))}
          </div>

          {/* Action Button */}
          <button
            onClick={() => {
              if (currentStep < slides.length - 1) {
                setCurrentStep(currentStep + 1)
              } else {
                onFinish()
              }
            }}
            style={{
              width: '100%',
              height: '52px',
              borderRadius: '26px',
              backgroundColor: '#FFFFFF',
              color: '#2B1818',
              border: 'none',
              fontSize: '16px',
              fontWeight: 700,
              cursor: 'pointer',
              boxShadow: '0 4px 14px rgba(0,0,0,0.15)',
              transition: 'transform 0.15s ease'
            }}
            onMouseDown={(e) => (e.currentTarget.style.transform = 'scale(0.98)')}
            onMouseUp={(e) => (e.currentTarget.style.transform = 'scale(1)')}
          >
            {currentStep === slides.length - 1 ? 'Enter StudyOS' : 'Continue'}
          </button>
        </div>
      </div>
    </AmbientBackground>
  )
}
