import { ChartLayout } from "@/components/layout/ChartLayout/ChartLayout";
import { DiagnoseLayout } from "@/components/layout/DiagnoseLayout/DiagnoseLayout";
import { Card } from "@/components/ui/Card/Card";
import {
  Bar,
  BarChart,
  CartesianGrid,
  Line,
  LineChart,
  Rectangle,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import { Plan } from "./components/Plan/Plan";
import { statData } from "./components/Stat/data";
import { Stat } from "./components/Stat/Stat";
import s from "./RecommendationsPage.module.css";

const dataLinear = [
  {
    name: "Jan",
    Rubles: 5000,
    amt: 2400,
  },
  {
    name: "Feb",
    Rubles: 6000,
    amt: 2210,
  },
  {
    name: "Mar",
    Rubles: 7000,
    amt: 2290,
  },
  {
    name: "Apr",
    Rubles: 2780,
    amt: 2000,
  },
  {
    name: "May",
    Rubles: 1890,
    amt: 2181,
  },
  {
    name: "Jun",
    Rubles: 2390,
    amt: 2500,
  },
  {
    name: "Jul",
    Rubles: 3490,
    amt: 2100,
  },
];

const data = [
  {
    name: "Jan",
    Rubles: 4000,
    amt: 2400,
  },
  {
    name: "Feb",
    Rubles: 3000,
    amt: 2210,
  },
  {
    name: "Mar",
    Rubles: 2000,
    amt: 2290,
  },
  {
    name: "Apr",
    Rubles: 2780,
    amt: 2000,
  },
  {
    name: "May",
    Rubles: 1890,
    amt: 2181,
  },
  {
    name: "Jun",
    Rubles: 2390,
    amt: 2500,
  },
];

const SimpleLineChart = () => {
  return (
    <LineChart
      responsive
      data={dataLinear}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis width="auto" />
      <Tooltip />
      <Line type="monotone" dataKey="Rubles" stroke="#82ca9d" />
    </LineChart>
  );
};

const SimpleBarChart = () => {
  return (
    <BarChart
      responsive
      data={data}
      margin={{
        top: 5,
        right: 0,
        left: 0,
        bottom: 5,
      }}
    >
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <Tooltip />
      <Bar
        radius={8}
        fill="rgba(0, 150, 137, 1)"
        maxBarSize={40}
        dataKey="Rubles"
        activeBar={<Rectangle fill="rgba(0, 150, 137, 0.7)" />}
      />
    </BarChart>
  );
};

export const RecommendationsPage = () => {
  return (
    <div className={s.recommendations}>
      <DiagnoseLayout diagnosis="Рассеянный спендер" className={s.diagnose}>
        <div className={s.stats}>
          {statData.map((stat, i) => (
            <Stat key={i} {...stat} />
          ))}
        </div>
      </DiagnoseLayout>
      <div className={s.analyticsCards}>
        <Card className={s.card}>
          <ChartLayout className={s.chart}>
            <h3>Траты после зарплаты</h3>
            <ResponsiveContainer className={s.simpleChart}>
              <SimpleBarChart />
            </ResponsiveContainer>
            <p className={s.description}>
              Ваши траты в первую неделю после зарплаты по месяцам
            </p>
          </ChartLayout>
        </Card>
        <Card className={s.card}>
          <ChartLayout className={s.chart}>
            <h3>Динамика сбережений</h3>
            <ResponsiveContainer className={s.lineChart}>
              <SimpleLineChart />
            </ResponsiveContainer>
            <p className={s.description}>Ваша динамика сбережений по месяцам</p>
          </ChartLayout>
        </Card>
      </div>
      <Plan />
    </div>
  );
};
