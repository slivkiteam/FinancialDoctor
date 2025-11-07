import { BaseLayout } from "@/components/layout/BaseLayout/BaseLayout";
import { Diagnose } from "./components/Diagnose/Diagnose";
import { Functionality } from "./components/Functionality/Functionality";
import { Hero } from "./components/Hero/Hero";
import { ReadyToStart } from "./components/ReadyToStart/ReadyToStart";
import { footerStyles } from "./HomePage.style";
import { memo } from "react";

export const HomePage = memo(() => {

  return (
    <BaseLayout footerStyles={footerStyles}>
      <Hero />
      <Functionality />
      <Diagnose />
      <ReadyToStart />
    </BaseLayout>
  );
});
