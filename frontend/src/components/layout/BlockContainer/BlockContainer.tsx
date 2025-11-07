import React from "react";
import s from "./BlockContainer.module.css";

interface BlockContainerProps {
  children: React.ReactNode;
  background?: string;
  borderTop?: string;
  borderBottom?: string;
}

export const BlockContainer = ({
  children,
  background = "white",
  borderTop = "none",
  borderBottom = "none",
}: BlockContainerProps) => {
  return (
    <div
      className={s.background}
      style={{ background, borderTop, borderBottom }}
    >
      <div className={s.content}>{children}</div>
    </div>
  );
};
