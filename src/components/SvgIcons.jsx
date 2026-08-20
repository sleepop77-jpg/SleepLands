import React from 'react'

export function FullscreenIcon({ isFullscreen, size = 24, color = '#FFFFFF' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke={color} strokeWidth="2.2" strokeLinecap="round" strokeLinejoin="round">
      {!isFullscreen ? (
        <>
          <path d="M4 8V4h4" />
          <path d="M20 8V4h-4" />
          <path d="M4 16v4h4" />
          <path d="M20 16v4h-4" />
        </>
      ) : (
        <>
          <path d="M8 4v4H4" />
          <path d="M16 4v4h4" />
          <path d="M8 20v-4H4" />
          <path d="M16 20v-4h4" />
        </>
      )}
    </svg>
  )
}

export function PlayPauseIcon({ isRunning, size = 32, color = '#FFFFFF' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 32 32" fill={color}>
      {isRunning ? (
        <>
          <rect x="9" y="7" width="5" height="18" rx="2.5" />
          <rect x="18" y="7" width="5" height="18" rx="2.5" />
        </>
      ) : (
        <path d="M11 7 L25 16 L11 25 Z" />
      )}
    </svg>
  )
}

export function ReloadIcon({ size = 24, color = '#FFFFFF' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke={color} strokeWidth="2.4" strokeLinecap="round" strokeLinejoin="round">
      <path d="M21 12a9 9 0 1 1-2.6-6.4" />
      <polyline points="21 5 21 11 15 11" />
    </svg>
  )
}

export function StarBadgeIcon({ size = 24, color = '#FFD54F' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill={color}>
      <polygon points="12,2 15,8.5 22,9.3 17,14 18.5,21 12,17.5 5.5,21 7,14 2,9.3 9,8.5" />
    </svg>
  )
}

export function ShieldIcon({ size = 64, color = '#FFFFFF' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 64 64" fill="none" stroke={color} strokeWidth="3.5" strokeLinecap="round" strokeLinejoin="round">
      <path d="M32 8 L54 16 C54 38 42 50 32 56 C22 50 10 38 10 16 Z" fill="rgba(255,255,255,0.15)" />
      <polyline points="24 32 29 37 40 24" strokeWidth="4" />
    </svg>
  )
}

export function TargetDialIcon({ size = 80, color = '#FFFFFF' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 80 80" fill="none" stroke={color} strokeWidth="3.5" strokeLinecap="round">
      <circle cx="40" cy="40" r="34" stroke="rgba(255,255,255,0.2)" />
      <path d="M40 6 A34 34 0 1 1 16 64" stroke="#FFD54F" strokeWidth="4.5" />
      <circle cx="40" cy="40" r="4" fill={color} />
      <line x1="40" y1="40" x2="40" y2="22" strokeWidth="3.5" />
      <line x1="40" y1="40" x2="52" y2="46" strokeWidth="3.5" />
    </svg>
  )
}
