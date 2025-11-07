import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import s from './Functionality.module.css'
import { cardsData } from "./data";
import { FunctionalityCard } from "./FunctionalityCard/FunctionalityCard";
import { FUNCTIONALITY_BACKGROUND_COLOR } from "./Functionality.styles";

export const Functionality = () => {
  return (
    <BlockContainer background={FUNCTIONALITY_BACKGROUND_COLOR}>
      <div className={s.functionality}>
        <h2 className={s.title}>Персональный финансовый доктор</h2>
        <p className={s.description}>
          Анализирует ваши доходы и траты и предоставляет персоанализированные
          советы
        </p>
        <div className={s.cards}>
          {cardsData.map((card) => (<FunctionalityCard key={card.id} {...card} />))}
        </div>
      </div>
    </BlockContainer>
  );
};
