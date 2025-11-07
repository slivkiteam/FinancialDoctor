import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { Button } from "@/components/ui/Button/Button";
import s from "./ReadyToStart.module.css";
import { READY_TO_START_BACKGROUND_COLOR } from "./ReadyToStart.styles";

export const ReadyToStart = () => {
  return (
    <BlockContainer background={READY_TO_START_BACKGROUND_COLOR}>
      <div className={s.container}>
        <h2 className={s.title}>Готовы проанализировать ваши финансы?</h2>
        <p className={s.description}>Получи свою финансовую аналитику</p>
        <Button isArrowEnabled={true} color="blue" background="white" arrowColor="blue">
          Получить аналитику
        </Button>
        <p className={s.footer}>Бесплатный старт • Регистрация 2 минуты</p>
      </div>
    </BlockContainer>
  );
};
