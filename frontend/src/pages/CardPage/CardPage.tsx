import { Card } from "@/components/ui/Card/Card";
import shield from "@assets/card/shield.svg";
import s from "./CardPage.module.css";
import { Step } from "./components/Step/Step";

export const CardPage = () => {
  return (
    <div className={s.cardPage}>
      <div className={s.steps}>
        <Step isCurrent number={1} text="Подключение банка" isLast={false} />
        <Step number={2} text="Подтверждение личности" isLast={false} />
        <Step number={3} text="Настройка уведомлений" isLast={true} />
      </div>
      <Card className={s.bankContainer}>
        <h2 className={s.title}>Подключи свой банк</h2>
        <p className={s.description}>
          Это безопасно: за безопасность ваших данных отвечает банк
        </p>
        <div className={s.banks}>
          <Card className={s.bankItem}>
            <h3 className={s.bankTitle}>Банк 1</h3>
            <button className={s.connectButton}>Подключить</button>
          </Card>
          <Card className={s.bankItem}>
            <h3 className={s.bankTitle}>Банк 2</h3>
            <button className={s.connectButton}>Подключить</button>
          </Card>
          <Card className={s.bankItem}>
            <h3 className={s.bankTitle}>Банк 3</h3>
            <button className={s.connectButton}>Подключить</button>
          </Card>
          <Card className={s.bankItem}>
            <h3 className={s.bankTitle}>Банк 4</h3>
            <button className={s.connectButton}>Подключить</button>
          </Card>
        </div>
      </Card>
      <Card className={s.security}>
        <h3 className={s.securityTitle}>Ваши данные в безопасности</h3>
        <div className={s.securityAdvices}>
          <div className={s.securityAdvice}>
            <img src={shield} alt="shield" />
            <p className={s.securityAdviceText}>
              Безопасность гарантирует банк
            </p>
          </div>
          <div className={s.securityAdvice}>
            <img src={shield} alt="shield" />
            <p className={s.securityAdviceText}>
              Безопасность гарантирует банк
            </p>
          </div>
        </div>
      </Card>
    </div>
  );
};
