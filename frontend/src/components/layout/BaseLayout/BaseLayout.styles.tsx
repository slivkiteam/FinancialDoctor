export interface StylesBase {
  border: string;
  logoTextColor?: string;
}

export interface FooterStyles extends StylesBase {
  backgroundColor: string;
}

export const BASE_FOOTER_STYLES: FooterStyles = {
  border: "1px solid rgba(229, 229, 229, 1)",
  backgroundColor: "rgba(0, 120, 111, 1)",
  logoTextColor: "white",
};

export const BASE_HEADER_STYLES: StylesBase = {
  border: "1px solid rgba(229, 229, 229, 1)",
  logoTextColor: "black",
};