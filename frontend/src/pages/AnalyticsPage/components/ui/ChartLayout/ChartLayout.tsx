import React from 'react'
import s from './ChartLayout.module.css'

interface ChartLayoutProps {
  children: React.ReactNode
}

export const ChartLayout = ({children} : ChartLayoutProps) => {
  return (
    <div className={s.chartLayout}>{children}</div>
  )
}
