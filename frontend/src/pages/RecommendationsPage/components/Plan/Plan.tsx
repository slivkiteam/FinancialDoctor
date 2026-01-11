import { PlanLayout } from "@/components/layout/PlanLayout/PlanLayout";
import meat from "@assets/analytics-page/plan/ham.svg";
import hand from "@assets/analytics-page/plan/hand-coins.svg";
import pig from "@assets/analytics-page/plan/piggy-bank.svg";
import {
  RecommendationCard,
  type RecommendationCardProps,
} from "@/components/ui/RecommendationCard/RecommendationCard";

export const Plan = () => {
  const data = [
    {
      id: 1,
      image: pig,
      title: "Заплати сначала себе",
      priority: "Высокий",
      description:
        "Настройте автоматический перевод 10% от зарплаты на накопительный счёт",
      deadline: "До 25 декабря",
    },
    {
      id: 2,
      image: meat,
      title: "Развлечения под контролем",
      priority: "Средний",
      description: 'Установите еженедельный лимит на категорию "Развлечения"',
      descriptionHelp: "Дополнительная информация 2",
      deadline: "До 30 декабря",
    },
    {
      id: 3,
      image: hand,
      title: "Правило 24 часов",
      priority: "Низкий",
      description:
        "Если планируете купить что-то дороже 2000₽ вне плана — давайте себе 24 часа на обдумывание",
      descriptionHelp: "Дополнительная информация 3",
      deadline: "До 5 января",
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
