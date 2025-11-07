import { Logo } from "@/components/ui/Logo/Logo";
import { Button } from "@components/ui/Button/Button";
import s from "./Header.module.css";
import { memo } from "react";

interface HeaderProps {
  styles: {
    border: string;
    logoTextColor?: string;
  };
}

export const Header = memo(({ styles }: HeaderProps) => {
  return (
      <header className={s.header} style={styles}>
        <Logo />
        <Button>Зарегистрироваться</Button>
      </header>
  );
});
