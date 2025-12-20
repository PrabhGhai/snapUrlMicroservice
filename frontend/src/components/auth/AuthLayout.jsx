import React from 'react'
import { Link } from "react-router-dom";
import { Link2 } from "lucide-react";
import { motion } from "framer-motion";
const AuthLayout = () => {
  return (
    <div className="hidden lg:flex relative overflow-hidden gradient-bg text-white">

        <div className="absolute top-20 left-20 w-64 h-64 rounded-full border border-white/20" />
        <div className="absolute bottom-40 right-20 w-96 h-96 rounded-full border border-white/10" />
        <div className="absolute top-1/2 left-1/3 w-32 h-32 rounded-full bg-white/10 blur-2xl" />

        {/* Content */}
        <motion.div  
        initial={{ opacity: 0, x: -50 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 1 ,delay:0.3 }} 
        className="relative z-10 flex flex-col justify-center px-16">
          <Link to="/" className="flex items-center gap-3 mb-12">
            <div className="w-12 h-12 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center">
              <Link2 className="w-7 h-7" />
            </div>
            <span className="text-3xl font-bold">SnapUrl</span>
          </Link>

          <h2 className="text-4xl font-bold mb-6 leading-tight">
            Simplify your links,<br />
            Amplify your impact
          </h2>

          <p className="text-lg text-white/80 max-w-md">
            Join thousands of marketers, creators, and businesses who trust
            SnapUrl to manage and track their links.
          </p>

          {/* Stats */}
          <div className="flex gap-10 mt-12">
            <div>
              <div className="text-3xl font-bold">2.5M+</div>
              <div className="text-sm text-white/70">Links shortened</div>
            </div>
            <div>
              <div className="text-3xl font-bold">10K+</div>
              <div className="text-sm text-white/70">Active users</div>
            </div>
            <div>
              <div className="text-3xl font-bold">99.9%</div>
              <div className="text-sm text-white/70">Uptime</div>
            </div>
          </div>
        </motion.div>
      </div>
  )
}

export default AuthLayout