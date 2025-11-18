import { Card } from "@/components/ui/Card/Card";
import bandage from "@assets/analytics-page/plan/bandage.svg";
import s from "./PlanLayout.module.css";

interface PlanProps<T> {
  description?: string;
  elements: Array<T>;
  TypeOfCards: React.ComponentType<T>;
  children?: React.ReactNode;
}

export const PlanLayout = <T,>({
  description,
  elements,
  TypeOfCards,
  children
}: PlanProps<T>) => {
  return (
    <Card className={s.plan}>
      <div className={s.header}>
        <img className={s.image} src={bandage} alt="" />
        <h2 className={s.title}>План лечения</h2>
      </div>
      <div className={s.recommendations}>
        {description ? (
          <p className={s.recommendationText}>{description}</p>
        ) : null}
        <ul className={s.recommendationList}>
          {elements.map((item, index) => (
            <TypeOfCards key={index} {...item}/>
          ))}
        </ul>
        {children}
      </div>
    </Card>
  );
};
