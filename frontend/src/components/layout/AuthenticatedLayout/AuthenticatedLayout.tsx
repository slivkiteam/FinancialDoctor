import React, { memo } from "react";
import { BaseLayout } from "../BaseLayout/BaseLayout";
import { Sidebar } from "../Sidebar/Sidebar";
import s from './AuthenticatedLayout.module.css'

interface AuthenticatedLayoutProps {
  children: React.ReactNode;
}

export const AuthenticatedLayout = memo(({ children }: AuthenticatedLayoutProps) => {
  return (
    <BaseLayout>
      <div className={s.authenticatedLayout}>
        <Sidebar />
        {children}
      </div>
    </BaseLayout>
  );
});
