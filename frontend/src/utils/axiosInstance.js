import axios from "axios";

export const authEvent = new EventTarget();

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosInstance.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (!originalRequest || !error.response) {
      return Promise.reject(error);
    }

    const requestUrl = originalRequest.url || "";

    const isAuthRoute =
      requestUrl.includes("/auth/login") ||
      requestUrl.includes("/auth/refresh-token") ||
      requestUrl.includes("/auth/verify");

    if (
      error.response.status === 401 &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true;

      try {
        await axiosInstance.post("/api/v1/auth/refresh-token");

        if(!requestUrl.includes("/auth/verify"))
        {
          return axiosInstance(originalRequest);
        }
        return "";
        
      } catch {
        authEvent.dispatchEvent(new Event("logout"));
        return Promise.reject(error);
      }
    }

    return Promise.reject(error);
  }
);



export default axiosInstance;
