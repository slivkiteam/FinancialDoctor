import { Card } from '@/components/ui/Card/Card'
import s from './DayAdvice.module.css'

export const DayAdvice = () => {
  return (
    <Card className={s.dayAdvice}>
      <div className="">
        <img src="" alt="" />
        <h3 className={s.title}>Совет дня</h3>
      </div>
      
      <p className={s.description}>
        Сфокусируйтесь на своих финансовых целях и избегайте импульсивных покупок.
      </p>
    </Card>
  )
}
