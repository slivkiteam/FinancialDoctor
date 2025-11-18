import tube from '@assets/analytics-page/diagnose/tube.svg'
import s from './DiagnoseLayout.module.css'
import { Card } from '@/components/ui/Card/Card'
import { Button } from '@/components/ui/Button/Button'
import type { ReactNode } from 'react';

interface DiagnoseLayoutProps {
    diagnosis: string;
    children: ReactNode;
    className?: string;
}

export const DiagnoseLayout = ({diagnosis, children, className}: DiagnoseLayoutProps) => {
  return (
    <Card className={`${s.diagnose} ${className}`}>
      <div className={s.titleContainer}>
        <img src={tube} alt="Пробирка" />
        <h3 className={s.title}>Ваш диагноз</h3>
      </div>
      <div className={s.buttonContainer}>
        <Button>Обновлено Вчера</Button>
      </div>
      <h4 className={s.diagnosis}>{diagnosis}</h4>
      {children}
    </Card>
  )
}
