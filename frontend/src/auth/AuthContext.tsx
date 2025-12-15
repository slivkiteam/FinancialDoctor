import { createContext } from "react";
import type { LoginData } from "./authTypes";

export interface AuthContextType {
  isAuthenticated: boolean;
  isLoggingOut: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  username: string | null;
  login: (username: string, password: string) => Promise<LoginData>;
  logout: () => void;
  register: (
    username: string,
    email: string,
    password: string,
    phoneNumber: string
  ) => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);
