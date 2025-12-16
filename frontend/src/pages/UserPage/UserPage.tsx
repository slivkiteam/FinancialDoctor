import user from "@/assets/user/user-round-pen.svg";
import { AuthContext } from "@/auth/AuthContext";
import { AppRoutes } from "@/services/router/routes";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import s from "./UserPage.module.css";

export const UserPage = () => {
  const ctx = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    ctx?.logout();
    navigate(AppRoutes.HOME);
  };

  if (!ctx?.isAuthenticated) {
    return (
      <div className={s.userPage}>
        Пожалуйста, войдите в систему, чтобы просмотреть профиль.
      </div>
    );
  }

  return (
    <div className={s.userPage}>
      <div className={s.header}>
        <img src={user} alt="User Avatar" className={s.icon} />
        <h2>Профиль</h2>
      </div>
      <div className={s.input}>
        <span>Имя</span>
        <input type="text" value={ctx?.username || ""} disabled />
      </div>
      <div className={s.input}>
        <span>Почта</span>
        <input type="email" value={ctx?.username || ""} disabled />
      </div>
      <div className={s.buttons}>
        <button>Редактировать</button>
        <button>Сменить пароль</button>
        <button>Отвязать банк</button>
        <button onClick={handleLogout}>Выйти</button>
      </div>
    </div>
  );
};
