import s from './UserPage.module.css'
import user from '@/assets/user/user-round-pen.svg'

export const UserPage = () => {
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
        <span>Фамилия</span>
        <input type="text" value='Sund' disabled/>
      </div>
      <div className={s.input}>
        <span>Почта</span>
        <input type="email" value='alex.sund@example.com' disabled/>
      </div>
      <div className={s.buttons}>
        <button>Редактировать</button>
        <button>Сменить пароль</button>
        <button>Отвязать банк</button>
      </div>
    </div>
  );
};
