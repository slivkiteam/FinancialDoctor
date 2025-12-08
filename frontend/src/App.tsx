import { AnalyticsPage } from "@/pages/AnalyticsPage/AnalyticsPage";
import { HomePage } from "@/pages/HomePage/HomePage";
import { useState } from "react";
import { Route, Routes } from "react-router-dom";
import "./App.css";
import { AuthenticatedLayout } from "./components/layout/AuthenticatedLayout/AuthenticatedLayout";
import { CardPage } from "./pages/CardPage/CardPage";
import { CreateGoalPage } from "./pages/GoalsPage/CreateGoalPage/CreateGoalPage";
import { RecommendationsPage } from "./pages/RecommendationsPage/RecommendationsPage";
import { StepPage } from "./pages/StepPage/StepPage";
import { UserPage } from "./pages/UserPage/UserPage";
import { AppRoutes } from "./services/router/routes";

function App() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  return (
    <>
      <Routes>
        <Route path={AppRoutes.HOME} element={<HomePage />} />
        <Route
          element={
            <AuthenticatedLayout
              isSidebarOpen={isSidebarOpen}
              setIsSidebarOpen={setIsSidebarOpen}
            />
          }
        >
          <Route path={AppRoutes.ANALYTICS} element={<AnalyticsPage />} />
          <Route path={AppRoutes.CARD} element={<CardPage />} />
          <Route
            path={AppRoutes.RECOMMENDATIONS}
            element={<RecommendationsPage />}
          />
          <Route path={AppRoutes.STEP_PAGE} element={<StepPage />} />
          <Route path={AppRoutes.CREATE_GOAL} element={<CreateGoalPage />} />
          <Route path={AppRoutes.USER_PROFILE} element={<UserPage />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
