import { Card } from "@/components/icons/Card/Card";
import { Chart } from "@/components/icons/Chart/Chart";
import { Goal } from "@/components/icons/Goal/Goal";
import { Cross } from "@components/icons/Cross/Cross"
import { AppRoutes } from "@/services/router/routes";

export const sidebarItems = [
  { image: Chart, text: "Финансовый анализ", link: AppRoutes.ANALYTICS },
  { image: Card, text: "Связь с банком", link: AppRoutes.CARD },
  { image: Cross, text: "Финансовые советы", link: AppRoutes.RECOMMENDATIONS },
  { image: Goal, text: "Финансовые цели", link: AppRoutes.CREATE_GOAL },
];
