import s from "./StepCard.module.css";

interface StepCardProps {
  id: number;
  bgColor: string;
  title: string;
}

export const StepCard = ({ id, bgColor, title }: StepCardProps) => {
  return (
    <div className={s.card}>
      <div className={s.ellips} style={{ background: bgColor }}>
        <span className={s.stepNumber}>{id}</span>
      </div>
      <h3 className={s.title}>{title}</h3>
    </div>
  );
};
