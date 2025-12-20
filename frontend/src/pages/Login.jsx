import React from 'react'
import { Link } from "react-router-dom";
import { Link2, User, Lock } from "lucide-react";
import AuthLayout from "../components/auth/AuthLayout";
import { motion } from "framer-motion";
const Login = () => {
  return (
   <div className="min-h-screen grid lg:grid-cols-2">
      <AuthLayout />
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

          <form className="space-y-4">

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
                />
              </div>
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
                />
              </div>
            </div>

            <button
              type="submit"
              className="w-full gradient-bg text-white py-2 rounded-md hover:opacity-90 transition hover:cursor-pointer"
            >
              Create Account
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
    </div>
  )
}

export default Login