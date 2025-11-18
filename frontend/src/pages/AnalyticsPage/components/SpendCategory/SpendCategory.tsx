import { Card } from "@/components/ui/Card/Card";
import { Pie, PieChart, ResponsiveContainer } from "recharts";
import { ChartLayout } from "../../../../components/layout/ChartLayout/ChartLayout";
import s from "./SpendCategory.module.css";

const INITIAL_DATA = [
  { name: "Группа А (Еда)", value: 400 },
  { name: "Группа B (Развлечения)", value: 300 },
  { name: "Группа C (Транспорт)", value: 300 },
  { name: "Группа D (Одежда)", value: 200 },
];

const COLORS = ["#3761A6", "#FF7F50", "#B83B5E", "#FFC300"];

const data01 = INITIAL_DATA.map((item, index) => ({
  ...item,
  fill: COLORS[index % COLORS.length],
}));

export default function TwoLevelPieChart({
  isAnimationActive = true,
}: {
  isAnimationActive?: boolean;
}) {
  return (
    <PieChart className={s.chart} responsive>
      <Pie
        data={data01}
        dataKey="value"
        cx="50%"
        cy="50%"
        outerRadius="100%"
        isAnimationActive={isAnimationActive}
      />
    </PieChart>
  );
}

export const SpendCategory = () => {
  return (
    <Card className={s.card}>
      <ChartLayout>
        <h3>Категории трат</h3>
        <ResponsiveContainer
          width="100%"
          height={300}
          className={s.chartContainer}
        >
          <TwoLevelPieChart />
        </ResponsiveContainer>
        <ul>
          {INITIAL_DATA.map((item, index) => (
            <li key={item.name}>
              <span style={{ color: COLORS[index % COLORS.length] }}>
                {item.name}
              </span>
              : {item.value}
            </li>
          ))}
        </ul>
      </ChartLayout>
    </Card>
  );
};
