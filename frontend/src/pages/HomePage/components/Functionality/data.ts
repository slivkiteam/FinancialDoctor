import chart from "@assets/functionality/chart-column-big.svg";
import goal from "@assets/functionality/goal.svg";
import pig from "@assets/functionality/piggy-bank.svg";
import shield from "@assets/functionality/shield.svg";

interface Card {
  id: number;
  image: string;
  title: string;
  description: string;
}

export const cardsData: Card[] = [
  {
    id: 1,
    image: pig,
    title: "Персонализированные советы",
    description: "Рекомендации на основе ваших доходов, расходов и целей",
  },
  {
    id: 2,
    image: chart,
    title: "Оценка финансового здоровья",
    description: "Понятная оценка вашего текущего финансового состояния",
  },
  {
    id: 3,
    image: goal,
    title: "Планы трат",
    description: "Планируйте расходы и следите за бюджетом без лишней рутины",
  },
  {
    id: 4,
    image: shield,
    title: "Безопасность на уровне банка",
    description: "Данные защищены бансковским уровня шифрования",
  },
];
