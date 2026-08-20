import React from 'react'

export default function MascotCharacter({ isRunning = true, size = 190 }) {
  return (
    <div
      style={{
        width: size,
        height: size,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        position: 'relative',
        userSelect: 'none'
      }}
    >
      <style>{`
        @keyframes mascot-bobbing {
          0%, 100% { transform: translateY(0px); }
          50% { transform: translateY(-4px); }
        }
        @keyframes typing-left {
          0%, 100% { transform: translateY(0px); }
          50% { transform: translateY(-2px); }
        }
        @keyframes typing-right {
          0%, 100% { transform: translateY(0px); }
          50% { transform: translateY(2px); }
        }
        @keyframes bulb-pulse {
          0%, 100% { opacity: 0.6; transform: scale(0.96); }
          50% { opacity: 1; transform: scale(1.04); }
        }
        .mascot-head-group {
          animation: mascot-bobbing 2s ease-in-out infinite;
          transform-origin: center bottom;
        }
        .typing-hand-left {
          animation: typing-left ${isRunning ? '0.3s' : '1.5s'} ease-in-out infinite;
        }
        .typing-hand-right {
          animation: typing-right ${isRunning ? '0.3s' : '1.5s'} ease-in-out infinite;
        }
        .idea-bulb {
          animation: bulb-pulse 1.4s ease-in-out infinite;
          transform-origin: 158px 58px;
        }
      `}</style>

      <svg
        viewBox="0 0 200 200"
        width={size}
        height={size}
        style={{ overflow: 'visible' }}
      >
        <defs>
          <filter id="bulbGlow" x="-20%" y="-20%" width="140%" height="140%">
            <feGaussianBlur stdDeviation="3" result="blur" />
            <feComposite in="SourceGraphic" in2="blur" operator="over" />
          </filter>
        </defs>

        {/* Desk Surface */}
        <polygon
          points="20,175 180,175 190,195 10,195"
          fill="#F1CECB"
          stroke="#4A2525"
          strokeWidth="2.5"
          strokeLinejoin="round"
        />

        <g className="mascot-head-group">
          {/* Shoulders / Body */}
          <ellipse
            cx="100"
            cy="142"
            rx="48"
            ry="34"
            fill="#FBE4E2"
            stroke="#4A2525"
            strokeWidth="2.8"
          />

          {/* Tomato Head */}
          <circle
            cx="100"
            cy="84"
            r="42"
            fill="#FBE4E2"
            stroke="#4A2525"
            strokeWidth="3.2"
          />

          {/* Leaves / Stem */}
          <path
            d="M100,42 L100,30 Q90,36 82,38 Q92,44 97,42 Q108,36 118,38 Q108,44 103,42 Z"
            fill="#F1CECB"
            stroke="#4A2525"
            strokeWidth="2.5"
            strokeLinecap="round"
            strokeLinejoin="round"
          />

          {/* Eyes */}
          <circle cx="86" cy="82" r="3.4" fill="#4A2525" />
          <circle cx="114" cy="82" r="3.4" fill="#4A2525" />

          {/* Blush */}
          <circle cx="76" cy="89" r="5.5" fill="#E57373" opacity="0.45" />
          <circle cx="124" cy="89" r="5.5" fill="#E57373" opacity="0.45" />

          {/* Smile */}
          <path
            d="M96,92 Q100,96 104,92"
            fill="none"
            stroke="#4A2525"
            strokeWidth="2.2"
            strokeLinecap="round"
          />

          {/* Headphones Headband */}
          <path
            d="M58,82 Q100,38 142,82"
            fill="none"
            stroke="#4A2525"
            strokeWidth="3.5"
            strokeLinecap="round"
          />

          {/* Earpad Left */}
          <rect
            x="52"
            y="68"
            width="14"
            height="30"
            rx="6"
            fill="#F1CECB"
            stroke="#4A2525"
            strokeWidth="2.5"
          />

          {/* Earpad Right */}
          <rect
            x="134"
            y="68"
            width="14"
            height="30"
            rx="6"
            fill="#F1CECB"
            stroke="#4A2525"
            strokeWidth="2.5"
          />

          {/* Headphone Cord */}
          <path
            d="M59,98 Q65,132 80,142"
            fill="none"
            stroke="#4A2525"
            strokeWidth="1.8"
            strokeDasharray="2 1"
            opacity="0.6"
          />
        </g>

        {/* Laptop Screen (Back) */}
        <rect
          x="72"
          y="126"
          width="56"
          height="38"
          rx="4"
          fill="#FFFFFF"
          stroke="#4A2525"
          strokeWidth="2.5"
        />

        {/* Apple/Tomato logo on laptop */}
        <circle cx="100" cy="145" r="3.8" fill="#4A2525" opacity="0.8" />

        {/* Laptop Base */}
        <polygon
          points="66,164 134,164 138,169 62,169"
          fill="#F1CECB"
          stroke="#4A2525"
          strokeWidth="2.2"
        />

        {/* Mouse */}
        <ellipse
          cx="53"
          cy="171"
          rx="7"
          ry="9"
          fill="#FFFFFF"
          stroke="#4A2525"
          strokeWidth="2"
        />

        {/* Hands Typing */}
        <g className="typing-hand-left">
          <circle cx="78" cy="154" r="7.5" fill="#FBE4E2" stroke="#4A2525" strokeWidth="2.2" />
        </g>
        <g className="typing-hand-right">
          <circle cx="122" cy="154" r="7.5" fill="#FBE4E2" stroke="#4A2525" strokeWidth="2.2" />
        </g>

        {/* Pulsing Idea Lightbulb */}
        <g className="idea-bulb">
          <circle cx="158" cy="58" r="16" fill="#FFD54F" opacity="0.25" filter="url(#bulbGlow)" />
          <path
            d="M153,66 L153,63 C148,60 148,52 153,49 C158,45 166,49 166,54 C166,58 163,60 163,63 L163,66 Z"
            fill="#FFFFFF"
            stroke="#4A2525"
            strokeWidth="2"
            strokeLinejoin="round"
          />
          <circle cx="158" cy="56" r="3.5" fill="#FFD54F" />
          <line x1="158" y1="42" x2="158" y2="37" stroke="#4A2525" strokeWidth="2" strokeLinecap="round" />
          <line x1="168" y1="46" x2="173" y2="42" stroke="#4A2525" strokeWidth="2" strokeLinecap="round" />
          <line x1="148" y1="46" x2="143" y2="42" stroke="#4A2525" strokeWidth="2" strokeLinecap="round" />
        </g>
      </svg>
    </div>
  )
}
