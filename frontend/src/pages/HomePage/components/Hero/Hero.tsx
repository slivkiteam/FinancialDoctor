import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { Button } from "@/components/ui/Button/Button";
import banknote from "@assets/hero/banknote.svg";
import ellipsFirst from "@assets/hero/Ellipse.svg";
import ellipsSecond from "@assets/hero/Ellipse2.svg";
import heroImage from "@assets/hero/hero-image.svg";
import shield from "@assets/hero/shield.svg";
import spring from "@assets/hero/spring.svg";
import s from "./Hero.module.css";
import { HERO_BACKGROUND_COLOR } from "./Hero.styles";
import { Link } from "react-router-dom";
import { AppRoutes } from "@/services/router/routes";


export const Hero = () => {
  return (
    <BlockContainer background={HERO_BACKGROUND_COLOR}>
      <div className={s.hero}>
        <h1 className={s.title}>
          Диагностика вашего
          <span className={s.highlight}>Финансового Здоровья</span>
        </h1>
        <p className={s.description}>
          Получайте персонализированную финансовую аналитику, планы лечения и
          оценки состояния здоровья.
        </p>
        <Link to={AppRoutes.ANALYTICS}>
          <Button isArrowEnabled={true}>Получить аналитику</Button>
        </Link>
        <div className={s.advantagesBlock}>
          <div className={s.advantage}>
            <img src={banknote} alt="Бесплатный старт" />
            <p className={s.advantageDescription}>Бесплатный старт</p>
          </div>
          <div className={s.advantage}>
            <img src={shield} alt="Безопасность на уровне банка" />
            <p className={s.advantageDescription}>
              Безопасность на уровне банка
            </p>
          </div>
        </div>
        <img className={s.heroImage} src={heroImage} alt="Hero Image" />
        <img className={s.ellipsFirst} src={ellipsFirst} alt="Ellips" />
        <img className={s.ellipsSecond} src={ellipsSecond} alt="Ellips" />
        <img className={s.spring} src={spring} alt="Spring" />
      </div>
    </BlockContainer>
  );
};
