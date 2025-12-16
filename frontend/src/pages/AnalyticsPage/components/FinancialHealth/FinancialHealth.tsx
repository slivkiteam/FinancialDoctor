import { Card } from "@/components/ui/Card/Card";
import { Label, Pie, PieChart, ResponsiveContainer } from "recharts";
import s from "./FinancialHealth.module.css";

const procent = Math.floor(Math.random() * 100);
const status =
  procent >= 75
    ? "Отлично"
    : procent >= 50
    ? "Хорошо"
    : procent >= 25
    ? "Средне"
    : "Плохо";

const data = [
  { name: "Group A", value: procent, fill: "rgba(0, 201, 81, 1)" },
  { name: "Group B", value: 100 - procent, fill: "rgba(185, 251, 210, 0.7)" },
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
        {procent}%
      </tspan>
      <tspan x="50%" dy="26" fontSize="16" fontWeight="400" fill="#666">
        {status}
      </tspan>
    </text>
  );
};

const MyPie = () => (
  <Pie
    startAngle={90}
    endAngle={-270}
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
