import { Button } from "@/components/ui/Button/Button";
import tube from "@assets/analytics-page/diagnose/tube.svg";
import s from "./Diagnose.module.css";
import { Card } from "@/components/ui/Card/Card";

export const Diagnose = () => {
  return (
    <Card className={s.diagnose}>
      <div className={s.titleContainer}>
        <img src={tube} alt="Пробирка" />
        <h3 className={s.title}>Ваш диагноз</h3>
      </div>
      <div className={s.buttonContainer}>
        <Button>Обновлено Вчера</Button>
      </div>

      <h4 className={s.diagnosis}>Рассеянный спендер</h4>
      <p className={s.description}>
        Более 35% доходов уходит на сиюминутные удовольствия. Финансовые цели
        откладываются, в конце месяца приходится себя ограничивать.
      </p>
      <div className={s.keyFeatures}>
        <h4 className={s.keyFeaturesTitle}>Ключевые особенности:</h4>
        <ul className={s.keyFeaturesList}>
          <li className={s.keyFeatureItem}>Импульсивные покупки</li>
          <li className={s.keyFeatureItem}>Отсутствие бюджета</li>
          <li className={s.keyFeatureItem}>Невозможность накопления</li>
        </ul>
      </div>
    </Card>
  );
};
