import { memo } from "react";
import s from "./Step.module.css";

interface StepProps {
  isCurrent?: boolean;
  number: number;
  text: string;
  isLast: boolean;
}

export const Step = memo(({ isCurrent=false, number, text, isLast }: StepProps) => {
  const circleClass = isCurrent ? `${s.circle} ${s.circleActive}` : s.circle;
  const stepClass = isLast ? s.step : `${s.step} ${s.stepNotLast}`;
  return (
    <div className={stepClass}>
      <div className={circleClass}>
        {number}
      </div>
      <p
        className={
          isCurrent
            ? `${s.stepsDescription} ${s.stepsDescriptionActive}`
            : s.stepsDescription
        }
      >
        {text}
      </p>
    </div>
  );
});
