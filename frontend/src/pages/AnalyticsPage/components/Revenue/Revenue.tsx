import { Card } from "@/components/ui/Card/Card";
import type { LabelProps } from "recharts";
import {
  Bar,
  BarChart,
  CartesianGrid,
  LabelList,
  Legend,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import { ChartLayout } from "../../../../components/layout/ChartLayout/ChartLayout";
import s from "./Revenue.module.css";

// #region Sample data
const data = [
  {
    name: "Январь",
    revenue: 4000,
    waste: 2400,
  },
  {
    name: "Февраль",
    revenue: 3000,
    waste: 1398,
  },
  {
    name: "Март",
    revenue: 2000,
    waste: 8,
  },
  {
    name: "Апрель",
    revenue: 2780,
    waste: 3908,
  },
  {
    name: "Май",
    revenue: 18,
    waste: 4800,
  },
  {
    name: "Июнь",
    revenue: 2390,
    waste: 3800,
  },
];

const sumrevenue = data.reduce((acc, curr) => acc + curr.revenue, 0);
const sumwaste = data.reduce((acc, curr) => acc + curr.waste, 0);

const renderCustomizedLabel = (props: LabelProps) => {
  const { x, y, width, value } = props;

  if (x == null || y == null || width == null) {
    return null;
  }

  return (
    <g>
      <text
        x={Number(x) + Number(width) / 2}
        y={Number(y)}
        fill="#fff"
        textAnchor="middle"
        dominantBaseline="middle"
      >
        {String(value).split(" ")[1]}
      </text>
    </g>
  );
};

const BarChartWithMinHeight = () => {
  return (
    <BarChart
      responsive
      data={data}
      margin={{
        top: 25,
        right: 0,
        left: 0,
        bottom: 5,
      }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis width="auto" />
      <Tooltip
        content={({ payload, label }) => {
          if (!payload || payload.length === 0) return null;
          return (
            <div
              style={{
                background: "#fff",
                padding: 8,
                border: "1px solid #ccc",
              }}
            >
              <strong>{label}</strong>
              <br />
              {payload.map((p) => (
                <div key={p.dataKey}>
                  {p.dataKey === "revenue" ? "Доход: " : "Расход: "} {p.value} ₽
                </div>
              ))}
            </div>
          );
        }}
      />
      <Legend
        formatter={(value) => (value === "revenue" ? "Доход" : "Расход")}
      />
      <Bar dataKey="waste" fill="#8884d8" minPointSize={5}>
        <LabelList dataKey="name" content={renderCustomizedLabel} />
      </Bar>
      <Bar dataKey="revenue" fill="#82ca9d" minPointSize={10} />
    </BarChart>
  );
};

export default BarChartWithMinHeight;

export const Revenue = () => {
  return (
    <Card className={s.card}>
      <ChartLayout>
        <h3>Доходы и расходы</h3>
        <ResponsiveContainer width="100%">
          <BarChartWithMinHeight />
        </ResponsiveContainer>
        <ul>
          <li>
            <strong>Доходы:</strong> {sumrevenue}
          </li>
          <li>
            <strong>Расходы:</strong> {sumwaste}
          </li>
        </ul>
      </ChartLayout>
    </Card>
  );
};
