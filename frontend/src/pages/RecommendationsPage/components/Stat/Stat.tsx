import React from 'react'
import s from './Stat.module.css'

interface StatProps {
  image: string;
  title: string;
  description: string;
}

export const Stat = ({image, title, description}: StatProps) => {
  return (
    <div className={s.stat}>
        <img className={s.image} src={image} alt={title} />
        <h4 className={s.title}>{title}</h4>
        <p className={s.description}>{description}</p>
    </div>
  )
}
