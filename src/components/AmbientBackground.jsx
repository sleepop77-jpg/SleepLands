import React, { useEffect, useRef } from 'react'

export default function AmbientBackground({ children, baseColor = '#D35D59' }) {
  const canvasRef = useRef(null)

  useEffect(() => {
    const canvas = canvasRef.current
    if (!canvas) return
    const ctx = canvas.getContext('2d')
    let animationFrameId
    let width = (canvas.width = canvas.offsetWidth || window.innerWidth)
    let height = (canvas.height = canvas.offsetHeight || window.innerHeight)

    const handleResize = () => {
      if (!canvas) return
      width = canvas.width = canvas.offsetWidth || window.innerWidth
      height = canvas.height = canvas.offsetHeight || window.innerHeight
    }
    window.addEventListener('resize', handleResize)

    // Particles
    const particles = Array.from({ length: 24 }, () => ({
      x: Math.random() * width,
      y: Math.random() * height,
      r: Math.random() * 2.5 + 1.2,
      speedX: (Math.random() - 0.5) * 0.4,
      speedY: -Math.random() * 0.5 - 0.2,
      alpha: Math.random() * 0.25 + 0.1
    }))

    let time = 0

    const render = () => {
      time += 0.015

      // Base gradient
      const bgGrad = ctx.createLinearGradient(0, 0, 0, height)
      bgGrad.addColorStop(0, '#D35D59')
      bgGrad.addColorStop(0.5, '#BA4E4A')
      bgGrad.addColorStop(1, '#D35D59')
      ctx.fillStyle = bgGrad
      ctx.fillRect(0, 0, width, height)

      // Floating Glow Orb 1 (Top right)
      const orb1X = width * 0.7 + Math.cos(time * 0.7) * (width * 0.12)
      const orb1Y = height * 0.25 + Math.sin(time * 0.5) * (height * 0.08)
      const grad1 = ctx.createRadialGradient(orb1X, orb1Y, 0, orb1X, orb1Y, width * 0.6)
      grad1.addColorStop(0, 'rgba(235, 120, 115, 0.4)')
      grad1.addColorStop(1, 'rgba(235, 120, 115, 0)')
      ctx.fillStyle = grad1
      ctx.beginPath()
      ctx.arc(orb1X, orb1Y, width * 0.6, 0, Math.PI * 2)
      ctx.fill()

      // Floating Glow Orb 2 (Bottom left)
      const orb2X = width * 0.28 + Math.sin(time * 0.6) * (width * 0.14)
      const orb2Y = height * 0.75 + Math.cos(time * 0.4) * (height * 0.1)
      const grad2 = ctx.createRadialGradient(orb2X, orb2Y, 0, orb2X, orb2Y, width * 0.5)
      grad2.addColorStop(0, 'rgba(240, 140, 135, 0.3)')
      grad2.addColorStop(1, 'rgba(240, 140, 135, 0)')
      ctx.fillStyle = grad2
      ctx.beginPath()
      ctx.arc(orb2X, orb2Y, width * 0.5, 0, Math.PI * 2)
      ctx.fill()

      // Drifting light specks
      particles.forEach((p) => {
        p.x += p.speedX
        p.y += p.speedY
        if (p.y < 0) p.y = height + 10
        if (p.x < 0) p.x = width
        if (p.x > width) p.x = 0

        ctx.fillStyle = `rgba(255, 255, 255, ${p.alpha})`
        ctx.beginPath()
        ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2)
        ctx.fill()
      })

      animationFrameId = requestAnimationFrame(render)
    }

    render()

    return () => {
      window.removeEventListener('resize', handleResize)
      cancelAnimationFrame(animationFrameId)
    }
  }, [baseColor])

  return (
    <div style={{ position: 'relative', width: '100%', height: '100%', overflow: 'hidden' }}>
      <canvas
        ref={canvasRef}
        style={{
          position: 'absolute',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
          pointerEvents: 'none'
        }}
      />
      <div style={{ position: 'relative', zIndex: 1, width: '100%', height: '100%' }}>
        {children}
      </div>
    </div>
  )
}
