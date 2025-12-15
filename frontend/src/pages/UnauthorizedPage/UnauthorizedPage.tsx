import lock from "@/assets/unauthorized/lock.png";
import { BaseLayout } from "@/components/layout/BaseLayout/BaseLayout";
import { Button } from "@/components/ui/Button/Button";
import { Link } from "react-router-dom";
import s from "./UnauthorizedPage.module.css";
import { AppRoutes } from "@/services/router/routes";

export const UnauthorizedPage = () => {
  return (
    <BaseLayout>
      <div className={s.unauthorizedPage}>
        <h2 className={s.title}>Авторизуйтесь для доступа</h2>
        <img className={s.lock} src={lock} alt="Lock" />
        <p className={s.message}>
          Пожалуйста, войдите в систему, чтобы получить доступ к этой странице.
        </p>
        <Button>
          <Link to={AppRoutes.LOGIN}>
            Войти
          </Link>
        </Button>
      </div>
    </BaseLayout>
  );
};
