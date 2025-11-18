import { DiagnoseLayout } from "@/components/layout/DiagnoseLayout/DiagnoseLayout";
import s from "./Diagnose.module.css";

export const Diagnose = () => {
  return (
    <DiagnoseLayout diagnosis="Рассеянный спендер">
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
    </DiagnoseLayout>
  );
};
