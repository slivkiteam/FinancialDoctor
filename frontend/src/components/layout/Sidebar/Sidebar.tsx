import { memo } from "react";
import s from "./Sidebar.module.css";
import { SidebarItem } from "./SidebarItem/SidebarItem";
import { sidebarItems } from "./data";

interface SidebarProps {
  isSidebarOpen: boolean;
  setIsSidebarOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const Sidebar = memo(
  ({ isSidebarOpen, setIsSidebarOpen }: SidebarProps) => {
    const handleBackdropClick = () => {
      setIsSidebarOpen(false);
    };
    return (
      <>
        <div className={`${s.sidebar} ${isSidebarOpen ? s.sidebarOpen : ""}`}>
          <ul className={s.sidebarList}>
            {sidebarItems.map((item, index) => (
              <SidebarItem
                onClick={() => setIsSidebarOpen(false)}
                key={index}
                image={item.image}
                text={item.text}
                link={item.link}
              />
            ))}
          </ul>
        </div>
        {isSidebarOpen && (
          <div className={s.backdrop} onClick={handleBackdropClick} />
        )}
      </>
    );
  }
);
