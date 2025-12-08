import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { Button } from "@/components/ui/Button/Button";
import { AppRoutes } from "@/services/router/routes";
import banknote from "@assets/hero/banknote.svg";
import ellipsFirst from "@assets/hero/Ellipse.svg";
import ellipsSecond from "@assets/hero/Ellipse2.svg";
import heroImage from "@assets/hero/hero-image.svg";
import mobileHeroImage from "@assets/hero/mobile-hero-image.svg";
import shield from "@assets/hero/shield.svg";
import spring from "@assets/hero/spring.svg";
import { Link } from "react-router-dom";
import s from "./Hero.module.css";
import { HERO_BACKGROUND_COLOR } from "./Hero.styles";

export const Hero = () => {
  return (
    <BlockContainer background={HERO_BACKGROUND_COLOR}>
      <div className={s.hero}>
        <div className={s.textBlock}>
          <h1 className={s.title}>
            Диагностика вашего
            <span className={s.highlight}>Финансового Здоровья</span>
          </h1>
          <p className={s.description}>
            Получайте персонализированную финансовую аналитику, планы лечения и
            оценки состояния здоровья.
          </p>
        </div>
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
        <picture>
          <source media="(max-width: 640px)" srcSet={mobileHeroImage} />
          <source media="(min-width: 641px)" srcSet={heroImage} />
          <img className={s.heroImage} src={heroImage} alt="Hero" />
        </picture>
        <img className={s.ellipsFirst} src={ellipsFirst} alt="Ellips" />
        <img className={s.ellipsSecond} src={ellipsSecond} alt="Ellips" />
        <img className={s.spring} src={spring} alt="Spring" />
      </div>
    </BlockContainer>
  );
};
