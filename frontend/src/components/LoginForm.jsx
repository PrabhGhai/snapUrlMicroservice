import React, { useState } from 'react'
import { Link } from "react-router-dom";
import { Link2, User, Lock } from "lucide-react";
import { motion } from "framer-motion";
import axiosInstance from '../utils/axiosInstance';
import { ClipLoader } from "react-spinners";
import { userLoginSchema } from '../utils/zodSchemas';


const LoginForm = () => {
    const [inputs,setInputs] = useState({username:"",password:""});
    const [errors, setErrors] = useState({username: "",password: "",});
    const [loading, setLoading] = useState(false);

    const change = (e)=>{
        const {name,value} = e.target;
        setInputs({...inputs ,[name]:value});
        setErrors((prev) => ({...prev,[name]: "",
    }));
     }
    
     const login = async(e)=>{
       e.preventDefault();
        // ZOD validation
       const result = userLoginSchema.safeParse(inputs);
       if(!result.success)
       {
        const resErrors = {};
        result.error.issues.forEach((issue) => {
         resErrors[issue.path[0]] = issue.message;
        });
        setErrors(resErrors);
        return;
       }
       
       //hit backend
       const res = await axiosInstance("/api/v1/auth/login",inputs);
       console.log(res);
     }
  return (
    <motion.div className="flex items-center justify-center bg-gray-100 p-6">
        <motion.div 
        initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1,y:0}}
          transition={{ duration: 1, delay: 0.3 }} className="w-full max-w-md bg-white p-8 rounded-lg shadow-md">
          
          {/* Mobile logo */}
          <Link to="/" className="flex items-center gap-2 mb-6 lg:hidden">
            <div className="w-10 h-10 rounded-lg gradient-bg flex items-center justify-center">
              <Link2 className="w-5 h-5 text-white" />
            </div>
            <span className="text-2xl font-bold gradient-text">
              SnapUrl
            </span>
          </Link>

          <h2 className="text-2xl font-bold mb-2">Create an account</h2>
          <p className="text-sm text-gray-500 mb-6">
            Start shortening your URLs for free
          </p>

          <form className="space-y-4" onSubmit={login}>

            {/* Username */}
            <div>
              <label className="text-sm font-medium">Username</label>
              <div className="relative mt-1">
                <User className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
                <input
                  type="text"
                  placeholder="Username"
                  className="w-full border border-gray-300 rounded-md py-2 pl-10 pr-3
                             focus:outline-none focus:border-teal-800 focus:ring-1 focus:ring-teal-800"
                    name="username"
                    onChange={change}
                />
              </div>
               {errors.username && (
                <p className="text-red-500 text-xs mt-1">
                  {errors.username}
                </p>
              )}
            </div>

            {/* Password */}
            <div>
              <label className="text-sm font-medium">Password</label>
              <div className="relative mt-1">
                <Lock className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-gray-400" />
                <input
                  type="password"
                  placeholder="Password"
                  className="w-full border border-gray-300 rounded-md py-2 pl-10 pr-3
                             focus:outline-none focus:border-teal-800 focus:ring-1 focus:ring-teal-800"
                    name="password"
                    onChange={change}
                />
              </div>
               {errors.password && (
                <p className="text-red-500 text-xs mt-1">
                  {errors.password}
                </p>
              )}
            </div>

            <button
              type="submit"
              disabled={loading}
              className={`w-full py-2 rounded-md flex items-center justify-center gap-2
                text-white gradient-bg transition
                ${loading ? "opacity-70 cursor-not-allowed" : "hover:opacity-90 cursor-pointer"}`}
            >
              {loading ? <ClipLoader size={20} color="#fff" /> : "Login"}
            </button>
          </form>

          <p className="text-center text-sm text-gray-500 mt-4">
            Don't have an account?{" "}
            <Link to="/signup" className="text-teal-800 hover:underline">
              Sign up for free
            </Link>
          </p>
        </motion.div>
      </motion.div>
  )
}

export default LoginForm