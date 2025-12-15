import ellips from "@/assets/hero/Ellipse.svg";
import spring from "@/assets/hero/spring.svg";
import { AuthContext } from "@/auth/AuthContext";
import { BaseLayout } from "@/components/layout/BaseLayout/BaseLayout";
import { AppRoutes } from "@/services/router/routes";
import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import s from "./AuthLayout.module.css";

interface AuthLayoutProps {
  type: "login" | "register";
}

export const AuthLayout = ({ type }: AuthLayoutProps) => {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [phone, setPhone] = useState("");
  const isRegister = type === "register";
  const ctx = useContext(AuthContext);

  const handleAuthSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (isRegister) {
      ctx?.register(email, name, password, phone);
    } else {
      ctx?.login(email, password);
    }
  };

  return (
    <BaseLayout isButtonVisible={false}>
      <div className={s.loginPage}>
        <form className={s.loginForm} onSubmit={handleAuthSubmit}>
          {isRegister ? (
            <h2 className={s.loginTitle}>Регистрация в FinancialDoctor</h2>
          ) : (
            <h2 className={s.loginTitle}>Вход в FinancialDoctor</h2>
          )}
          <div className={s.inputGroup}>
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          {isRegister && (
            <div className={s.inputGroup}>
              <label htmlFor="name">Имя</label>
              <input
                type="text"
                id="name"
                name="name"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>
          )}
          <div className={s.inputGroup}>
            <label htmlFor="password">Пароль</label>
            <input
              type="password"
              id="password"
              name="password"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          {isRegister && (
            <>
              <div className={s.inputGroup}>
                <label htmlFor="confirmPassword">Подтвердите пароль</label>
                <input
                  type="password"
                  id="confirmPassword"
                  name="confirmPassword"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  required
                />
              </div>
            </>
          )}
          {isRegister && (
            <div className={s.inputGroup}>
              <label htmlFor="terms" className={s.termsLabel}>
                Телефон
              </label>
              <input
                type="tel"
                id="phone"
                name="phone"
                required
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
              />
            </div>
          )}
          <button type="submit" className={s.loginButton}>
            {isRegister ? "Зарегистрироваться" : "Войти"}
          </button>
        </form>
        {isRegister ? (
          <div className={s.registerLinkContainer}>
            <span className={s.text}>
              Уже есть аккаунт?{" "}
              <Link to={AppRoutes.LOGIN} className={s.registerLink}>
                Войдите
              </Link>
            </span>
          </div>
        ) : (
          <div className={s.registerLinkContainer}>
            <span className={s.text}>
              Нет аккаунта?{" "}
              <Link to={AppRoutes.REGISTER} className={s.registerLink}>
                Создайте новый
              </Link>
            </span>
          </div>
        )}
        <img className={s.spring} src={spring} alt="FinancialDoctor Logo" />
        <img className={s.ellipse} src={ellips} alt="FinancialDoctor Logo" />
        <img
          className={s.ellipseSecond}
          src={ellips}
          alt="FinancialDoctor Logo"
        />
      </div>
    </BaseLayout>
  );
};
