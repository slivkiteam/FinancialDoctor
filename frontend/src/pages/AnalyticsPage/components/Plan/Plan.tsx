import { Card } from "@/components/ui/Card/Card";
import s from "./Plan.module.css";
import pig from "@assets/analytics-page/plan/piggy-bank.svg"
import meat from "@assets/analytics-page/plan/ham.svg"
import hand from '@assets/analytics-page/plan/hand-coins.svg'
import bandage from '@assets/analytics-page/plan/bandage.svg'
import { RecommendationCard } from "./RecommendationCard/RecommendationCard";

export const Plan = () => {
    const data = [ 
        {
            image: pig,
            title: "Рекомендация 1",
            priority: "Высокий",
            description: "Описание рекомендации 1"
        },
        {
            image: meat,
            title: "Рекомендация 2",
            priority: "Средний",
            description: "Описание рекомендации 2"
        },
        {
            image: hand,
            title: "Рекомендация 3",
            priority: "Низкий",
            description: "Описание рекомендации 3"
        }
    ]

  return (
    <Card className={s.plan}>
      <div className={s.header}>
        <img className={s.image} src={bandage} alt="" />
        <h2 className={s.title}>План лечения</h2>
      </div>
      <div className={s.recommendations}>
        <p className={s.recommendationText}>Персональные рекомендации для вас</p>
        <ul className={s.recommendationList}>
            {data.map((item, index) => (
                <RecommendationCard key={index} {...item} />
            ))}
        </ul>
      </div>
    </Card>
  );
};
