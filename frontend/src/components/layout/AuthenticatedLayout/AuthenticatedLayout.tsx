import { memo, useEffect } from "react";
import { BaseLayout } from "../BaseLayout/BaseLayout";
import { Sidebar } from "../Sidebar/Sidebar";
import s from './AuthenticatedLayout.module.css'
import { Outlet } from "react-router-dom";

export const AuthenticatedLayout = memo(() => {
  console.log("AuthenticatedLayout rendered");
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);
  
  return (
    <BaseLayout>
      <div className={s.authenticatedLayout}>
        <Sidebar />
        <Outlet/>
      </div>
    </BaseLayout>
  );
});
