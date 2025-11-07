import { Card } from '@/components/ui/Card/Card'
import s from './DayAdvice.module.css'
import sun from '@assets/analytics-page/day-advice/sun.svg'

export const DayAdvice = () => {
  return (
    <Card className={s.dayAdvice}>
      <div className={s.header}>
        <img className={s.image} src={sun} alt="Солнце" />
        <h3 className={s.title}>Совет дня</h3>
      </div>
      <p className={s.description}>
        Сфокусируйтесь на своих финансовых целях и избегайте импульсивных покупок.
      </p>
    </Card>
  )
}
