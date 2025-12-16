const BASE_URL = "http://localhost:8081/api/v1";

export const fetchAuthData = async (
  url: string,
  accessToken: string,
  userId: number = 0,
  method: "GET" | "POST" = "GET"
) => {
  const userIdParam = userId !== 0 ? `?userId=${userId}` : "";
  const data = await fetch(`${BASE_URL}${url}${userIdParam}`, {
    method: method,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
  });
  return await data.json();
};
