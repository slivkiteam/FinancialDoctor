import React, { memo, useEffect, useMemo } from "react";
import { Footer } from "../Footer/Footer";
import { Header } from "../Header/Header";
import {
  BASE_FOOTER_STYLES,
  BASE_HEADER_STYLES,
  type FooterStyles,
  type StylesBase,
} from "./BaseLayout.styles";

interface BaseLayoutProps {
  children: React.ReactNode;
  footerStyles?: FooterStyles;
  headerStyles?: StylesBase;
  setIsSidebarOpen?: React.Dispatch<React.SetStateAction<boolean>>;
  isButtonVisible?: boolean;
}

export const BaseLayout = memo(({
  children,
  footerStyles,
  headerStyles,
  setIsSidebarOpen,
  isButtonVisible
}: BaseLayoutProps) => {

  const finalFooterStyles = useMemo(() => {
    return { ...BASE_FOOTER_STYLES, ...footerStyles };
  }, [footerStyles]);

  const finalHeaderStyles = useMemo(() => {
    return { ...BASE_HEADER_STYLES, ...headerStyles };
  }, [headerStyles]);

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  return (
    <>
      <Header styles={finalHeaderStyles} setIsSidebarOpen={setIsSidebarOpen} isButtonVisible={isButtonVisible} />
      <main>{children}</main>
      <Footer styles={finalFooterStyles} />
    </>
  );
});
