import { Route, Routes } from "react-router-dom";
import "./App.css";
import { HomePage } from "@/pages/HomePage/HomePage";
import { AppRoutes } from "./services/router/routes";
import { AnalyticsPage } from "@/pages/AnalyticsPage/AnalyticsPage";
import { CardPage } from "./pages/CardPage/CardPage";

function App() {
  return (
    <>
    <Routes>
      <Route path={AppRoutes.HOME} element={<HomePage />} />
      <Route path={AppRoutes.ANALYTICS} element={<AnalyticsPage />} />
      <Route path={AppRoutes.CARD} element={<CardPage />} />
    </Routes>
    </>
  );
}

export default App;
