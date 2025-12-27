import { createContext, useContext, useEffect, useState } from "react";
import axiosInstance from "../utils/axiosInstance";
import { userLoginSchema } from '../utils/zodSchemas';
import toast from 'react-hot-toast';
import { useNavigate } from "react-router-dom";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => { 
    const navigate = useNavigate();
    const [user, setUser] = useState({username:"",email:""});
    const [role, setRole] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(()=>{
      const verify = async()=>{
        try{
            const res = await axiosInstance.get("/api/v1/auth/verify");
           // console.log(res);
           const resData ={username:res?.data?.username , email: res?.data?.email};
            setUser(resData);
            setRole("USER");
            setIsLoggedIn(true);
        }catch(e)
        {
            console.log(e);
            setIsLoggedIn(false);
            setRole(null);
            setUser({username:"",email:""});
        }
      }
      verify();
    },[])

    //login user
    const login = async (inputs) => {
        //zod check
    const result = userLoginSchema.safeParse(inputs);

    if (!result.success) {
        const resErrors = {};
        result.error.issues.forEach((issue) => {
        resErrors[issue.path[0]] = issue.message;
        });
        return { success: false, errors: resErrors };
    }

    try {
        const res = await axiosInstance.post("/api/v1/auth/login", inputs);

        toast.success("Login Success");
        //console.log(res);
        const resData ={username:res?.data?.username , email: res?.data?.email};
        setUser(resData);
        setRole(res.data.role);
        setIsLoggedIn(true);
        navigate("/dashboard");

        return { success: true };
    } catch (err) {
        toast.error(err.response?.data?.message || "Login failed");
        return { success: false };
    }
};

//logout user
const logout = async()=>{
    try{
       const res =  await axiosInstance.post("api/v1/auth/logout");
       setUser({username:"",email:""});
       setRole(null);
       setIsLoggedIn(false);
       toast.success("Logged out successfully");
       navigate("/");
    }catch(e)
    {
        toast.error("Unauthorized access");
    }
}





    return(<AuthContext.Provider value={{user,login,role,isLoggedIn,logout}}>
        {children}
    </AuthContext.Provider>)
}
