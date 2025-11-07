import { Button } from "@/components/ui/Button/Button";
import s from "./Recommendation.module.css";
import { Card } from "@/components/ui/Card/Card";

interface RecommendationCardProps {
  title: string;
  image: string;
  priority: string;
  description: string;
}

export const RecommendationCard = ({title, image, description, priority}: RecommendationCardProps) => {
  return (
    <li className={s.item}>
      <Card className={s.card}>
        <div className={s.header}>
            <div className={s.titleContainer}>
              <img className={s.image} src={image} alt={title} />
              <h3 className={s.title}>{title}</h3>
            </div>
            <Button>{priority}</Button>
        </div>
        <p className={s.description}>{description}</p>
        <div className={s.footer}>
            <p className={s.date}>01.01.2000</p>
            <Button>Приступить</Button>
        </div>
      </Card>
    </li>
  );
};
