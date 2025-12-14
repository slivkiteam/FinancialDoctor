import { Logo } from "@/components/ui/Logo/Logo";
// import { Button } from "@components/ui/Button/Button";
import { AppRoutes } from "@/services/router/routes";
import { memo } from "react";
import { Link, useLocation } from "react-router-dom";
import s from "./Header.module.css";

interface HeaderProps {
  styles: {
    border: string;
    logoTextColor?: string;
  };
  setIsSidebarOpen?: React.Dispatch<React.SetStateAction<boolean>>;
}

export const Header = memo(({ styles, setIsSidebarOpen }: HeaderProps) => {
  const location = useLocation();

  const burgerPaths = [
    String(AppRoutes.ANALYTICS),
    String(AppRoutes.USER_PROFILE),
    String(AppRoutes.CARD),
    String(AppRoutes.RECOMMENDATIONS),
    String(AppRoutes.STEP_PAGE),
    String(AppRoutes.CREATE_GOAL),
  ];

  const showBurger = burgerPaths.includes(location.pathname);

  return (
    <header className={s.header} style={styles}>
      <Logo />
      {/* <Button>Зарегистрироваться</Button> */}
      <div className={s.rightSection}>
        <Link to={AppRoutes.USER_PROFILE}>
          <div className={s.avatar}>AS</div>
        </Link>
        {showBurger && <div onClick={() => setIsSidebarOpen?.(prev => !prev)} className={s.burgerMenu}>&#9776;</div>}
      </div>
    </header>
  );
});
