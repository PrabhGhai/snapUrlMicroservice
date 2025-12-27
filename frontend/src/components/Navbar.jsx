import { Link, useLocation } from "react-router-dom";
import { motion } from "framer-motion";
import { Link2, Menu, X } from "lucide-react";
import { useState } from "react";
import { useAuth } from "../store/AuthContext";

const Navbar = () => {
  const {isLoggedIn} = useAuth();

  return (
    <motion.nav
      initial={{ y: -20, opacity: 0 }}
      animate={{ y: 0, opacity: 1 }}
      transition={{ duration: 0.5, ease: "easeOut" }}
      className="fixed top-0 left-0 right-0 z-50 backdrop-blur-xs border-b border-gray-300 "
    >
      <div className="container mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2 group">
            <motion.div
              whileHover={{ rotate: 15 }}
              transition={{ type: "spring", stiffness: 400 }}
              className="w-9 h-9 rounded-lg gradient-bg flex items-center justify-center text-white"
            >
              <Link2 className="w-5 h-5 text-primary-foreground" />
            </motion.div>
            <span className="text-xl font-bold gradient-text">SnapUrl</span>
          </Link>


          {/* Auth Buttons */}
          <div className="flex items-center gap-3">
            {isLoggedIn ? <Link to="/dashboard">
              <button className=" p-2 rounded-lg gradient-bg hover:cursor-pointer hover:shadow-xl transition-all duration-300 flex items-center justify-center text-white">
                Dashboard
              </button>
            </Link> : <>
            <Link to="/login">
              <button className="p-2 hover:text-cyan-600 hover:cursor-pointer  transition-all duration-300">
                Log in
              </button>
            </Link>
            <Link to="/signup">
              <button className=" p-2 rounded-lg gradient-bg hover:cursor-pointer hover:shadow-xl transition-all duration-300 flex items-center justify-center text-white">
                Sign up
              </button>
            </Link>
            </> }
            
            
          </div>
        </div>
      </div>
    </motion.nav>
  );
};

export default Navbar;
