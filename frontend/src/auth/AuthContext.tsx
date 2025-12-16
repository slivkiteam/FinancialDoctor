import { createContext } from "react";
import type { LoginData, RegisterData } from "./authTypes";

export interface AuthContextType {
  isAuthenticated: boolean;
  isLoggingOut: boolean;
  isAuthInitialized: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  username: string | null;
  login: (username: string, password: string) => Promise<LoginData>;
  logout: () => void;
  register: (registerData: RegisterData) => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);
