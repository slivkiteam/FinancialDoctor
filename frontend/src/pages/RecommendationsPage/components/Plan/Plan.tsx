import { PlanLayout } from "@/components/layout/PlanLayout/PlanLayout";
import meat from "@assets/analytics-page/plan/ham.svg";
import hand from "@assets/analytics-page/plan/hand-coins.svg";
import pig from "@assets/analytics-page/plan/piggy-bank.svg";
import {
  RecommendationCard,
  type RecommendationCardProps,
} from "../../../../components/ui/RecommendationCard/RecommendationCard";

export const Plan = () => {
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

  return (
    <PlanLayout<RecommendationCardProps>
      description="Персональные рекомендации для вас"
      elements={data}
      TypeOfCards={RecommendationCard}
    />
  );
};
