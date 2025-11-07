import s from "./FunctionalityCard.module.css";

interface FunctionalityCardProps {
  image: string;
  title: string;
  description: string;
}

export const FunctionalityCard = ({
  image,
  title,
  description,
}: FunctionalityCardProps) => {
  return (
    <div className={s.card}>
      <img src={image} alt={title} className={s.image} />
      <h3 className={s.title}>{title}</h3>
      <p className={s.description}>{description}</p>
    </div>
  );
};
