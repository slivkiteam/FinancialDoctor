import React from "react";
import s from "./BlockContainer.module.css";

interface BlockContainerProps {
  children: React.ReactNode;
  background?: string;
  borderTop?: string;
  borderBottom?: string;
  className?: string;
}

export const BlockContainer = ({
  children,
  className,
  background = "white",
  borderTop = "none",
  borderBottom = "none",
}: BlockContainerProps) => {
  return (
    <div
      className={`${s.background} ${className}`}
      style={{ background, borderTop, borderBottom }}
    >
      <div className={s.content}>{children}</div>
    </div>
  );
};
