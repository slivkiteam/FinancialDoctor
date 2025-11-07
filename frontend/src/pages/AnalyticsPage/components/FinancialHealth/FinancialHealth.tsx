import { Label, Pie, PieChart, ResponsiveContainer } from "recharts";
import s from "./FinancialHealth.module.css";
import { Card } from "@/components/ui/Card/Card";

const data = [
  { name: "Group A", value: 75, fill: "rgba(0, 201, 81, 1)" },
  { name: "Group B", value: 25, fill: "rgba(185, 251, 210, 0.7)" },
];

const MyLabel = () => {
  return (
    <text
      x="50%"
      y="50%"
      textAnchor="middle"
      dominantBaseline="middle"
      className={s.label}
    >
      <tspan fontSize="34" fontWeight="700" fill="#000">
        75%
      </tspan>
      <tspan x="50%" dy="26" fontSize="16" fontWeight="400" fill="#666">
        Хорошо
      </tspan>
    </text>
  );
};

const MyPie = () => (
  <Pie
    data={data}
    dataKey="value"
    nameKey="name"
    outerRadius="100%"
    innerRadius="62%"
    isAnimationActive={true}
  />
);

export const FinancialHealth = () => {
  return (
    <Card className={s.financialHealth}>
      <h3 className={s.title}>Финансовое здоровье</h3>
      <ResponsiveContainer width="100%" height={300}>
        <PieChart responsive className={s.pieChart}>
          <MyPie />
          <Label
            position="center"
            fill="#666"
            fontSize={20}
            fontWeight={600}
            content={MyLabel}
          ></Label>
        </PieChart>
      </ResponsiveContainer>
    </Card>
  );
};
