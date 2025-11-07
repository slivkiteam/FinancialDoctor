import { AuthenticatedLayout } from "@/components/layout/AuthenticatedLayout/AuthenticatedLayout";
import s from "./Analytics.module.css";
import { DayAdvice } from "./components/DayAdvice/DayAdvice";
import { Diagnose } from "./components/Diagnose/Diagnose";
import { FinancialHealth } from "./components/FinancialHealth/FinancialHealth";
import { Plan } from "./components/Plan/Plan";
import { SpendCategory } from "./components/SpendCategory/SpendCategory";
import { Revenue } from "./components/Revenue/Revenue";

export const AnalyticsPage = () => {
  return (
    <AuthenticatedLayout>
      <div className={s.analyticsContainer}>
        <div className={s.financialHealthContainer}>
          <FinancialHealth />
          <Diagnose />
        </div>
        <div className={s.financialHealthContainer}>
          <SpendCategory />
          <Revenue />
        </div>
          <DayAdvice />
          <Plan />
      </div>
    </AuthenticatedLayout>
  );
};
