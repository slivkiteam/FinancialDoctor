import { AppRoutes } from "@/services/router/routes";
import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";
import { AUTH_ROUTES } from "./authRoutes";
import type { LoginData, RegisterData, UserData } from "./authTypes";

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [accessToken, setAccessToken] = useState<string | null>(null);
  const [refreshToken, setRefreshToken] = useState<string | null>(
    localStorage.getItem("refreshToken")
  );
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const [isLoggingOut, setIsLoggingOut] = useState(false);
  const [user, setUser] = useState<UserData | null>(null);
  const [isAuthInitialized, setIsAuthInitialized] = useState(false);
  const navigate = useNavigate();

  const refreshAccessToken = useCallback(async (): Promise<string> => {
    const response = await fetch(AUTH_ROUTES.REFRESH, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: refreshToken,
    });

    if (!response.ok) {
      throw new Error("Token refresh failed");
    }

    const data = await response.json();
    setAccessToken(data.accessToken);
    setRefreshToken(data.refreshToken);
    setUser({ username: data.username, id: data.id });
    localStorage.setItem("refreshToken", data.refreshToken);
    setIsAuthenticated(true);

    return data.accessToken;
  }, [refreshToken]);

  useEffect(() => {
    const initAuth = async () => {
      if (!refreshToken) {
        setIsAuthInitialized(true);
        return;
      }

      try {
        await refreshAccessToken();
      } catch {
        logout();
      } finally {
        setIsAuthInitialized(true);
      }
    };
    initAuth();
  }, [refreshAccessToken, refreshToken]);

  async function register(registerData: RegisterData): Promise<void> {
    const response = await fetch(AUTH_ROUTES.REGISTER, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(registerData),
    });

    if (!response.ok) {
      throw new Error("Registration failed");
    }
    login(registerData.email, registerData.password);
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

    if (!data) {
      throw new Error("Invalid login response");
    }

    setAccessToken(data.accessToken);
    setRefreshToken(data.refreshToken);
    setIsAuthenticated(true);
    localStorage.setItem("refreshToken", data.refreshToken);
    setUser({ username: data.username, id: data.id });
    navigate(AppRoutes.ANALYTICS);
    return data;
  }

  function logout() {
    setIsLoggingOut(true);
    setUser(null);
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
        user,
        login,
        logout,
        register,
        isAuthInitialized,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
