import { AppRoutes } from "@/services/router/routes";
import logo from "@assets/ui/logo.svg";
import { Link } from "react-router-dom";
import s from "./Logo.module.css";
import React from "react"; 

interface LogoProps {
  textBackground?: string; 
}

export const Logo = ({ textBackground }: LogoProps) => {
  const dynamicStyles = textBackground
    ? { '--logo-background-color': textBackground } as React.CSSProperties
    : {};

  return (
    <Link to={AppRoutes.HOME}>
      <div className={s.logoContainer} style={dynamicStyles}>
        <img src={logo} alt="Logo" className={s.logo} />
        <span className={s.logoText}>
          Financial Doctor
        </span>
      </div>
    </Link>
  );
};