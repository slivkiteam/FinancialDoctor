import arrow from "@assets/ui/arrow.svg";
import arrowBlue from '@assets/ui/arrow-blue.svg'
import React from "react";
import s from "./Button.module.css";

interface ButtonProps {
  children: React.ReactNode;
  isArrowEnabled?: boolean;
  arrowColor?: string;
  background?: string;
  color?: string;
}

export const Button = ({
  children,
  isArrowEnabled = false,
  arrowColor,
  background,
  color = "white",
  
}: ButtonProps) => {
  return (
    <div className={s.button} style={{ background, color }}>
      {children}
      {isArrowEnabled && (
        <img className={s.arrow} src={arrowColor === 'blue' ? arrowBlue : arrow} alt="Arrow Icon" />
      )}
    </div>
  );
};
