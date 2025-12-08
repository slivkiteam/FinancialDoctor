import { memo, useEffect } from "react";
import { BaseLayout } from "../BaseLayout/BaseLayout";
import { Sidebar } from "../Sidebar/Sidebar";
import s from './AuthenticatedLayout.module.css'
import { Outlet } from "react-router-dom";

interface AuthenticatedLayoutProps {
  isSidebarOpen: boolean;
  setIsSidebarOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const AuthenticatedLayout = memo(({ isSidebarOpen, setIsSidebarOpen }: AuthenticatedLayoutProps) => {
  
  console.log("AuthenticatedLayout rendered");
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  
  return (
    <BaseLayout setIsSidebarOpen={setIsSidebarOpen}>
      <div className={s.authenticatedLayout}>
        <Sidebar isSidebarOpen={isSidebarOpen} setIsSidebarOpen={setIsSidebarOpen} />
        <Outlet/>
      </div>
    </BaseLayout>
  );
});
