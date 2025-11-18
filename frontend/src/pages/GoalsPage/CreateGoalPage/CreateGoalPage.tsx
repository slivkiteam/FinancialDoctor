import { Button } from "@/components/ui/Button/Button";
import s from "./CreateGoal.module.css";

export const CreateGoalPage = () => {
  return (
    <div className={s.goalsPage}>
      <h2 className={s.title}>Создать новую цель</h2>
      <div className={s.createGoal}>
        <h3 className={s.createGoalTitle}>Новая цель</h3>
        <input name="goal" type="text" placeholder="Цель" className={s.input} />
        <input
          name="amount"
          type="text"
          placeholder="Сумма"
          className={s.input}
        />
        <input
          name="deadline"
          type="date"
          placeholder="Дедлайн"
          className={s.input}
        />
        <div className={s.replenishment}>
          <span>Пополнение</span>
          <select name="replenishment" id="replenishment">
            <option value="">Ежемесячно</option>
            <option value="">Еженедельно</option>
            <option value="">Ежесуточно</option>
          </select>
        </div>
      </div>
      <Button>Создать</Button>
    </div>
  );
};
