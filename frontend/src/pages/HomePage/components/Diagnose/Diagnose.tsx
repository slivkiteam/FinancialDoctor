import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { steps } from "./data";
import s from "./Diagnose.module.css";
import { DIAGNOSE_BACKGROUND_COLOR } from "./Diagnose.styles";
import { StepCard } from "./StepCard/StepCard";

export const Diagnose = () => {
  return (
    <BlockContainer background={DIAGNOSE_BACKGROUND_COLOR}>
      <div className={s.stepsBlockContainer}>
        <h2 className={s.title}>
          Получи свой финансовый "диагноз и план лечения" в 3 этапа
        </h2>
        <div className={s.stepsContainer}>
          {steps.map((step) => (
            <StepCard key={step.id} {...step} />
          ))}
        </div>
      </div>
    </BlockContainer>
  );
};
