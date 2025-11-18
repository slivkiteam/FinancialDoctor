import React from 'react'
import s from './ChartLayout.module.css'

interface ChartLayoutProps {
  children: React.ReactNode
  className?: string
}

export const ChartLayout = ({children ,className} : ChartLayoutProps) => {
  return (
    <div className={`${s.chartLayout} ${className}`}>{children}</div>
  )
}
