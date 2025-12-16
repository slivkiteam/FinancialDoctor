import { AuthContext } from "@/auth/AuthContext";
import { fetchAuthData } from "@/services/api/requests";
import { RequestRoutes } from "@/services/api/routes";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useContext, useEffect } from "react";
import { PuffLoader } from "react-spinners";
import s from "./Analytics.module.css";
import { DayAdvice } from "./components/DayAdvice/DayAdvice";
import { Diagnose } from "./components/Diagnose/Diagnose";
import { FinancialHealth } from "./components/FinancialHealth/FinancialHealth";
import { Revenue } from "./components/Revenue/Revenue";
import { SpendCategory } from "./components/SpendCategory/SpendCategory";

export const AnalyticsPage = () => {
  const ctx = useContext(AuthContext);
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["user", ctx?.user?.id, "diagnoses", "current"],
    queryFn: () =>
      fetchAuthData(
        RequestRoutes.DIAGNOSES_CURRENT,
        ctx?.accessToken || "",
        ctx?.user?.id
      ),
    enabled: !!ctx?.accessToken && !!ctx?.user?.id,
  });

  const recalculateMutation = useMutation({
    mutationFn: () =>
      fetchAuthData(
        RequestRoutes.DIAGNOSES_RECALCULATE,
        ctx!.accessToken!,
        ctx!.user!.id,
        "POST"
      ),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["user", ctx!.user!.id, "diagnoses"],
      });
    },
  });

  useEffect(() => {
    if (!ctx?.accessToken || !ctx?.user?.id) return;

    recalculateMutation.mutate();
  }, [ctx?.accessToken, ctx?.user?.id]);

  const data = query.data;
  const healthScore = data?.financialHealthScore;

  if (query.isLoading) {
    return (
      <div className={s.loadingContainer}>
        <PuffLoader color="#36d7b7" />
      </div>
    );
  }

  return (
    <div className={s.analyticsContainer}>
      <div className={s.financialHealthContainer}>
        <FinancialHealth healthScore={healthScore} />
        <Diagnose />
      </div>
      <div className={s.financialHealthContainer}>
        <SpendCategory />
        <Revenue />
      </div>
      <DayAdvice />
    </div>
  );
};
