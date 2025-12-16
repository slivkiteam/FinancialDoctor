import { Logo } from "@/components/ui/Logo/Logo";
// import { Button } from "@components/ui/Button/Button";
import { AuthContext } from "@/auth/AuthContext";
import { Button } from "@/components/ui/Button/Button";
import { AppRoutes } from "@/services/router/routes";
import { memo, useContext } from "react";
import { Link, useLocation } from "react-router-dom";
import s from "./Header.module.css";

interface HeaderProps {
  styles: {
    border: string;
    logoTextColor?: string;
  };
  setIsSidebarOpen?: React.Dispatch<React.SetStateAction<boolean>>;
  isButtonVisible?: boolean;
}

export const Header = memo(
  ({ styles, setIsSidebarOpen, isButtonVisible = true }: HeaderProps) => {
    const location = useLocation();
    const ctx = useContext(AuthContext);
    const burgerPaths = [
      String(AppRoutes.ANALYTICS),
      String(AppRoutes.USER_PROFILE),
      String(AppRoutes.CARD),
      String(AppRoutes.RECOMMENDATIONS),
      String(AppRoutes.STEP_PAGE),
      String(AppRoutes.CREATE_GOAL),
    ];

    const showBurger = burgerPaths.includes(location.pathname);
    const username = ctx?.isAuthenticated ? ctx?.user?.username : null;
    const headerUsername = username?.slice(0, 2).toUpperCase();

    return (
      <header className={s.header} style={styles}>
        <Logo />

        <div className={s.rightSection}>
          {isButtonVisible &&
            (ctx?.isAuthenticated ? (
              <Link to={AppRoutes.USER_PROFILE}>
                <div className={s.avatar}>{headerUsername}</div>
              </Link>
            ) : (
              <Button className={s.headerButton}>
                <Link to={AppRoutes.REGISTER}>Зарегистрироваться</Link>
              </Button>
            ))}

          {showBurger && (
            <div
              onClick={() => setIsSidebarOpen?.((prev) => !prev)}
              className={s.burgerMenu}
            >
              &#9776;
            </div>
          )}
        </div>
      </header>
    );
  }
);
