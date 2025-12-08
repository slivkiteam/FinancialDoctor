import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import s from "./SidebarItem.module.css";

interface SidebarItemProps {
  image: React.FC<IconComponentProps>;
  text: string;
  link: string;
  onClick: () => void;
}
interface IconComponentProps {
  color: string;
  className?: string;
}

export const SidebarItem = ({ image, text, link, onClick }: SidebarItemProps) => {
  const location = useLocation();
  const isActive = location.pathname === link;
  const [isHovered, setIsHovered] = useState(false);
  return (
    <li
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <Link
        to={link}
        className={isActive ? s.sidebarItemActive : s.sidebarItem}
        onClick={onClick}
      >
        {React.createElement(image, {
          color: isActive || isHovered ? "#155DFC" : "black",
          className: s.sidebarItemIcon,
        })}
        <span className={s.sidebarItemText}>{text}</span>
      </Link>
    </li>
  );
};
