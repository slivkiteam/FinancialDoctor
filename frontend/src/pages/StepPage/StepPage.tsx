import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { PlanLayout } from "@/components/layout/PlanLayout/PlanLayout";
import pig from "@assets/analytics-page/plan/piggy-bank.svg";
import hand from "@assets/analytics-page/plan/hand-coins.svg";
import meat from "@assets/analytics-page/plan/ham.svg";
import help from '@assets/analytics-page/plan/circle-help.svg'
import "@components/ui/RecommendationCard/RecommendationCard";
import { RecommendationCard } from "@components/ui/RecommendationCard/RecommendationCard";
import { useNavigate, useParams } from "react-router-dom";
import s from "./StepPage.module.css";

const data = [
  {
    id: 1,
    image: pig,
    title: "Рекомендация 1",
    priority: "Высокий",
    description: "Описание рекомендации 1",
  },
  {
    id: 2,
    image: meat,
    title: "Рекомендация 2",
    priority: "Средний",
    description: "Описание рекомендации 2",
    descriptionHelp: "Дополнительная информация 2",
  },
  {
    id: 3,
    image: hand,
    title: "Рекомендация 3",
    priority: "Низкий",
    description: "Описание рекомендации 3",
    descriptionHelp: "Дополнительная информация 3",
  },
];

export const StepPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const recommendation = data.find((item) => item.id === Number(id));
  
  return (
    <BlockContainer className={s.container}>
      <PlanLayout
        elements={recommendation ? [recommendation] : []}
        TypeOfCards={RecommendationCard}
      >
        <div className={s.help}>
          <div className={s.helpHeader}><img src={help} alt="Help icon" />
          <h3>Как мне это поможет?</h3></div>
          <p>{recommendation?.descriptionHelp}</p>
        </div>
        <div className={s.buttons}>
          <button className={s.markDone}>Отметить выполненным</button>
          <button className={s.back} onClick={() => navigate(-1)}>Назад</button>
        </div>
      </PlanLayout>
    </BlockContainer>
  );
};
