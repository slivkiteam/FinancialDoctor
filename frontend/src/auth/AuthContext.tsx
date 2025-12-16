import { createContext } from "react";
import type { LoginData, RegisterData, UserData } from "./authTypes";

export interface AuthContextType {
  isAuthenticated: boolean;
  isLoggingOut: boolean;
  isAuthInitialized: boolean;
  accessToken: string | null;
  refreshToken: string | null;
  user: UserData | null;
  login: (username: string, password: string) => Promise<LoginData>;
  logout: () => void;
  register: (registerData: RegisterData) => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);
