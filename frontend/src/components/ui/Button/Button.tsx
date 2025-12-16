import arrow from "@assets/ui/arrow.svg";
import arrowBlue from '@assets/ui/arrow-blue.svg'
import React from "react";
import s from "./Button.module.css";

interface ButtonProps {
  children: React.ReactNode;
  isArrowEnabled?: boolean;
  arrowColor?: string;
  background?: string;
  className?: string;
  color?: string;
  size?: "small" | "medium" | "large"
}

export const Button = ({
  children,
  isArrowEnabled = false,
  arrowColor,
  background,
  className,
  color = "white",
  size="medium"
}: ButtonProps) => {
  return (
    <div className={`${s.button} ${s[size]} ${className}`} style={{ background, color }}>
      {children}
      {isArrowEnabled && (
        <img className={s.arrow} src={arrowColor === 'blue' ? arrowBlue : arrow} alt="Arrow Icon" />
      )}
    </div>
  );
};
