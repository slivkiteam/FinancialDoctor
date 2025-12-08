import { Logo } from "@/components/ui/Logo/Logo";
import tgLogo from "@assets/footer/tg-logo.svg";
import vkLogo from "@assets/footer/vk-logo.svg";
import s from "./Footer.module.css";
import { memo } from "react";

interface FooterProps {
  styles?: {
    backgroundColor: string;
    border: string;
    logoTextColor?: string;
  };
}

export const Footer = memo(({ styles }: FooterProps) => {
  const backgroundColor = styles?.backgroundColor;
  const border = styles?.border;
  const logoTextColor = styles?.logoTextColor;
  
  return (
    <footer className={s.footerContainer} style={{ backgroundColor, borderTop:border }}>
      <div className={s.footer}>
        <div className={s.logoRights}>
          <Logo textBackground={logoTextColor} />
          <div className={s.rightsText}>
            © Financial Doctor. All rights reserved.
          </div>
        </div>
        <a className={s.supportLink}>Поддержка</a>
        <div className={s.socialLinks}>
          <img src={vkLogo} alt="VK" />
          <img src={tgLogo} alt="Telegram" />
        </div>
      </div>
    </footer>
  );
});
