const AUTH_URL = "http://localhost:8081/api/v1/auth"

export const AUTH_ROUTES = {
  LOGIN: `${AUTH_URL}/login`,
  REGISTER: `${AUTH_URL}/register`,
  REFRESH: `${AUTH_URL}/refresh`,
};