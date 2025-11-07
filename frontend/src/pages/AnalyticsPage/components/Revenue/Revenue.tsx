import { Card } from "@/components/ui/Card/Card";
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
import type { LabelProps } from "recharts";
import { ChartLayout } from "../ui/ChartLayout/ChartLayout";
import s from "./Revenue.module.css";

// #region Sample data
const data = [
  {
    name: "Page A",
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    name: "Page B",
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    name: "Page C",
    uv: 2000,
    pv: 8,
    amt: 2290,
  },
  {
    name: "Page D",
    uv: 2780,
    pv: 3908,
    amt: 2000,
  },
  {
    name: "Page E",
    uv: 18,
    pv: 4800,
    amt: 2181,
  },
  {
    name: "Page F",
    uv: 2390,
    pv: 3800,
    amt: 2500,
  },
  {
    name: "Page G",
    uv: 3490,
    pv: 4300,
    amt: 2100,
  },
];

const sumUv = data.reduce((acc, curr) => acc + curr.uv, 0);
const sumPv = data.reduce((acc, curr) => acc + curr.pv, 0);

const renderCustomizedLabel = (props: LabelProps) => {
  const { x, y, width, value } = props;

  if (x == null || y == null || width == null) {
    return null;
  }
  const radius = 10;

  return (
    <g>
      <circle
        cx={Number(x) + Number(width) / 2}
        cy={Number(y) - radius}
        r={radius}
        fill="#8884d8"
      />
      <text
        x={Number(x) + Number(width) / 2}
        y={Number(y) - radius}
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
      <Tooltip />
      <Legend />
      <Bar dataKey="pv" fill="#8884d8" minPointSize={5}>
        <LabelList dataKey="name" content={renderCustomizedLabel} />
      </Bar>
      <Bar dataKey="uv" fill="#82ca9d" minPointSize={10} />
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
            <li><strong>Доходы:</strong> {sumUv}</li>
            <li><strong>Расходы:</strong> {sumPv}</li>
        </ul>
      </ChartLayout>
    </Card>
  );
};
