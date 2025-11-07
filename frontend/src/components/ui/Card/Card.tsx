import React from 'react'
import s from './Card.module.css'

interface CardProps {
  children: React.ReactNode
  backgroundColor?: string
  className?: string
}

export const Card = ({children, className}: CardProps) => {
  return (
    <div className={`${s.card} ${className}`}>
        {children}
    </div>
  )
}
