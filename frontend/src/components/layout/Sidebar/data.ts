import { Card } from "@/components/icons/Card/Card";
import { Chart } from "@/components/icons/Chart/Chart";
import { Goal } from "@/components/icons/Goal/Goal";
import { AppRoutes } from "@/services/router/routes";

export const sidebarItems = [
  { image: Chart, text: "Финансовый анализ", link: AppRoutes.ANALYTICS },
  { image: Card, text: "Кредитная карта", link: AppRoutes.CARD },
  { image: Goal, text: "Финансовые советы", link: AppRoutes.RECOMMENDATIONS },
];
