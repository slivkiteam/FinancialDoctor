import { AuthenticatedLayout } from "@/components/layout/AuthenticatedLayout/AuthenticatedLayout";
import s from "./Analytics.module.css";
import { DayAdvice } from "./components/DayAdvice/DayAdvice";
import { Diagnose } from "./components/Diagnose/Diagnose";
import { FinancialHealth } from "./components/FinancialHealth/FinancialHealth";

export const AnalyticsPage = () => {
  return (
    <AuthenticatedLayout>
      <div className={s.analyticsContainer}>
        <div className={s.financialHealthContainer}>
          <FinancialHealth />
          <Diagnose />
        </div>
        <div className={s.buttonContainer}>
          <DayAdvice />
        </div>
      </div>
    </AuthenticatedLayout>
  );
};
