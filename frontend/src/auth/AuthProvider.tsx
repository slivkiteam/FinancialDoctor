import { AppRoutes } from "@/services/router/routes";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { AUTH_ROUTES } from "./authRoutes";
import type { LoginData } from "./authTypes";

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [refreshToken, setRefreshToken] = useState<string | null>(
    localStorage.getItem("refreshToken")
  );
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [isLoggingOut, setIsLoggingOut] = useState(false);
  const [username, setUsername] = useState<string | null>(null);

  const navigate = useNavigate();

  async function register(
    username: string,
    email: string,
    password: string,
    phoneNumber: string
  ): Promise<void> {
    const response = await fetch(AUTH_ROUTES.REGISTER, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, email, password, phoneNumber }),
    });

    if (!response.ok) {
      throw new Error("Registration failed");
    }

    return await response.json();
  }

  async function login(email: string, password: string): Promise<LoginData> {
    const response = await fetch(AUTH_ROUTES.LOGIN, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      throw new Error("Login failed");
    }

    const data: LoginData = await response.json();
    setAccessToken(data.accessToken);
    setRefreshToken(data.refreshToken);
    setIsAuthenticated(true);
    localStorage.setItem("refreshToken", data.refreshToken);
    setUsername(data.username);
    navigate(AppRoutes.ANALYTICS);
    return data;
  }

  function logout() {
    setIsLoggingOut(true);
    setUsername(null);
    setAccessToken(null);
    setRefreshToken(null);
    setIsAuthenticated(false);
    localStorage.removeItem("refreshToken");

    setTimeout(() => {
      setIsLoggingOut(false);
    }, 50);
  }

  return (
    <AuthContext.Provider
      value={{
        accessToken,
        refreshToken,
        isAuthenticated,
        isLoggingOut,
        username,
        login,
        logout,
        register,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
