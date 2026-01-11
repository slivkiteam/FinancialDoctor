import { Button } from "@/components/ui/Button/Button";
import { Card } from "@/components/ui/Card/Card";
import { Link, useLocation } from "react-router-dom";
import s from "./Recommendation.module.css";

export interface RecommendationCardProps {
  title: string;
  image: string;
  priority: string;
  description: string;
  deadline: string;
  id?: number;
}

export const RecommendationCard = ({
  title,
  image,
  description,
  priority,
  id,
  deadline,
}: RecommendationCardProps) => {
  const location = useLocation();
  const isStepPage = location.pathname.startsWith("/step/");
  const currentCardClassName = isStepPage ? `${s.card} ${s.stepPageCard}` : `${s.card}`;
  
  return (
    <li className={s.item}>
      <Card className={currentCardClassName}>
        <div className={s.header}>
          <div className={s.titleContainer}>
            <img className={s.image} src={image} alt={title} />
            <h3 className={s.title}>{title}</h3>
          </div>
          <Button>{priority}</Button>
        </div>
        <p className={s.description}>{description}</p>
        <div className={s.footer}>
          <p className={s.date}>{deadline}</p>
          {!isStepPage && (
            <Button>
              <Link to={`/step/${id}`}>Приступить</Link>
            </Button>
          )}
        </div>
      </Card>
    </li>
  );
};
