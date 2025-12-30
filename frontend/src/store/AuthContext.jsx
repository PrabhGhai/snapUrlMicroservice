import { createContext, useContext, useEffect, useState } from "react";
import axiosInstance from "../utils/axiosInstance";
import { userLoginSchema } from "../utils/zodSchemas";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { authEvent } from "../utils/axiosInstance";

const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();

  const [user, setUser] = useState({ username: "", email: "" });
  const [role, setRole] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading , setIsLoading] = useState(true);
  
  //verify user 
  useEffect(() => {
    const verify = async () => {
      setIsLoading(true);
      try {
        const res = await axiosInstance.get("/api/v1/auth/verify");
        // if we get the error here the interceptor will detetct it written in below useffect.
        setUser({
          username: res.data.username,
          email: res.data.email,
        });
        setRole(res.data.role || "USER");
        setIsLoggedIn(true);
        setIsAuthenticated(true);
         setIsLoading(false);
      } catch(e) {
        setUser({ username: "", email: "" });
        setRole(null);
        setIsLoggedIn(false);
        setIsAuthenticated(false);
        setIsLoading(false);
      }
    };

    verify();
  }, []);

  
  useEffect(() => {
  const onLogout = () => {
    handleLogout(true); // auto logout
  };

  authEvent.addEventListener("logout", onLogout);

  return () => {
    authEvent.removeEventListener("logout", onLogout);
  };
}, []);

  //login
  const login = async (inputs) => {
    const result = userLoginSchema.safeParse(inputs);

    if (!result.success) {
      const errors = {};
      result.error.issues.forEach((issue) => {
        errors[issue.path[0]] = issue.message;
      });
      return { success: false, errors };
    }

    try {
      const res = await axiosInstance.post("/api/v1/auth/login", inputs);

      setUser({
        username: res.data.username,
        email: res.data.email,
      });
      setRole(res.data.role);
      setIsLoggedIn(true);
      setIsAuthenticated(true);

      toast.success("Login successful");
      navigate("/dashboard");

      return { success: true };
    } catch (err) {
      toast.error(err.response?.data?.message || "Login failed");
      return { success: false };
    }
  };

  //logout
  const handleLogout = async (auto = false) => {

   if (!auto) {
    await axiosInstance.post("/api/v1/auth/logout");
    toast.success("Logged out");
  }

  setUser({ username: "", email: "" });
  setRole(null);
  setIsLoggedIn(false);
  setIsAuthenticated(false);

  navigate("/login");
  };

  return (
    <AuthContext.Provider
      value={{user,role,isLoggedIn, isAuthenticated,login,logout: handleLogout, isLoading
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
