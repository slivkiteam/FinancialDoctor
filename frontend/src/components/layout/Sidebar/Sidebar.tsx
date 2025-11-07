import s from "./Sidebar.module.css";
import { SidebarItem } from "./SidebarItem/SidebarItem";
import { sidebarItems } from "./data";

export const Sidebar = () => {
  return (
    <div className={s.sidebar}>
      <ul className={s.sidebarList}>
        {sidebarItems.map((item, index) => (
          <SidebarItem key={index} image={item.image} text={item.text} link={item.link} />
        ))}
      </ul>
    </div>
  );
};
