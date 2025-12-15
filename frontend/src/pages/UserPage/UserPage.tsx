import { useContext } from 'react';
import s from './UserPage.module.css'
import user from '@/assets/user/user-round-pen.svg'
import { AuthContext } from '@/auth/AuthContext';
import { AppRoutes } from '@/services/router/routes';
import { useNavigate } from 'react-router-dom';

export const UserPage = () => {
  const ctx = useContext(AuthContext);  
  const navigate = useNavigate();

  const handleLogout = () => {
    ctx?.logout();
    navigate(AppRoutes.HOME);
  }

  return (
    <div className={s.userPage}>
      <div className={s.header}>
        <img src={user} alt="User Avatar" className={s.icon}/>
        <h2>Профиль</h2>
      </div>
      <div className={s.input}>
        <span>Имя</span>
        <input type="text" value='Alex' disabled/>
      </div>
      <div className={s.input}>
        <span>Почта</span>
        <input type="email" value='alex.sund@example.com' disabled/>
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
