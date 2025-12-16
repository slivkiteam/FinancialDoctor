import { AuthContext } from "@/auth/AuthContext";
import { AppRoutes } from "@/services/router/routes";
import { memo, useContext, useEffect } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { BaseLayout } from "../BaseLayout/BaseLayout";
import { Sidebar } from "../Sidebar/Sidebar";
import s from "./AuthenticatedLayout.module.css";

interface AuthenticatedLayoutProps {
  isSidebarOpen: boolean;
  setIsSidebarOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export const AuthenticatedLayout = memo(
  ({ isSidebarOpen, setIsSidebarOpen }: AuthenticatedLayoutProps) => {
    const ctx = useContext(AuthContext);

    useEffect(() => {
      window.scrollTo(0, 0);
    }, []);

    if (!ctx) return null;
    
    if (!ctx.isAuthInitialized) {
      return null;
    }

    if (!ctx.isAuthenticated && !ctx.isLoggingOut) {
      return <Navigate to={AppRoutes.UNAUTHORIZED} replace />;
    }

    return (
      <BaseLayout setIsSidebarOpen={setIsSidebarOpen}>
        <div className={s.authenticatedLayout}>
          <Sidebar
            isSidebarOpen={isSidebarOpen}
            setIsSidebarOpen={setIsSidebarOpen}
          />
          <Outlet />
        </div>
      </BaseLayout>
    );
  }
);
