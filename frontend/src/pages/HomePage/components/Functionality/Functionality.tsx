import { BlockContainer } from "@/components/layout/BlockContainer/BlockContainer";
import { useEffect, useState } from "react";
import "swiper/css";
import "swiper/css/navigation";
import { Navigation } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";
import { cardsData } from "./data";
import s from "./Functionality.module.css";
import { FUNCTIONALITY_BACKGROUND_COLOR } from "./Functionality.styles";
import { FunctionalityCard } from "./FunctionalityCard/FunctionalityCard";

export const Functionality = () => {
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const check = () => setIsMobile(window.innerWidth < 1024);
    check();
    window.addEventListener("resize", check);
    return () => window.removeEventListener("resize", check);
  }, []);

  return (
    <BlockContainer background={FUNCTIONALITY_BACKGROUND_COLOR}>
      <div className={s.functionality}>
        <h2 className={s.title}>Персональный финансовый доктор</h2>
        <p className={s.description}>
          Анализирует ваши доходы и траты и предоставляет персоанализированные
          советы
        </p>

        {isMobile ? (
          <div className={s.sliderWrapper}>
            <Swiper
              modules={[Navigation]}
              navigation
              centeredSlides
              spaceBetween={16}
              slidesPerView={1}
            >
              {cardsData.map((card) => (
                <SwiperSlide key={card.id} className={s["swiper-slide"]}>
                  <FunctionalityCard {...card} />
                </SwiperSlide>
              ))}
            </Swiper>
          </div>
        ) : (
          <div className={s.cards}>
            {cardsData.map((card) => (
              <FunctionalityCard key={card.id} {...card} />
            ))}
          </div>
        )}
      </div>
    </BlockContainer>
  );
};
