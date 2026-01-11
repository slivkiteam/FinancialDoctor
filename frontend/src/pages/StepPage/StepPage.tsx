import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { PlanLayout } from "@/components/layout/PlanLayout/PlanLayout";
import meat from "@assets/analytics-page/plan/ham.svg";
import hand from "@assets/analytics-page/plan/hand-coins.svg";
import pig from "@assets/analytics-page/plan/piggy-bank.svg";
import "@components/ui/RecommendationCard/RecommendationCard";
import { RecommendationCard } from "@components/ui/RecommendationCard/RecommendationCard";
import { useNavigate, useParams } from "react-router-dom";
import s from "./StepPage.module.css";

const data = [
  {
    id: 1,
    image: pig,
    title: "Заплати сначала себе",
    priority: "Высокий",
    description:
      "Настройте автоматический перевод 10% от зарплаты на накопительный счёт",
    deadline: "До 25 июня",
  },
  {
    id: 2,
    image: meat,
    title: "Развлечения под контролем",
    priority: "Средний",
    description: 'Установите еженедельный лимит на категорию "Развлечения"',
    descriptionHelp: "Дополнительная информация 2",
    deadline: "До 30 июня",
  },
  {
    id: 3,
    image: hand,
    title: "Правило 24 часов",
    priority: "Низкий",
    description:
      "Если планируете купить что-то дороже 2000₽ вне плана — давайте себе 24 часа на обдумывание",
    descriptionHelp: "Дополнительная информация 3",
    deadline: "До 5 июля",
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
        <div className={s.buttons}>
          <button className={s.markDone}>Отметить выполненным</button>
          <button className={s.back} onClick={() => navigate(-1)}>
            Назад
          </button>
        </div>
      </PlanLayout>
    </BlockContainer>
  );
};
