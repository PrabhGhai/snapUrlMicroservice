
import { motion } from "framer-motion";
import { Link2 } from 'lucide-react';
import { Link } from "react-router-dom";
import { useAuth } from "../../store/AuthContext";

const UseDashboardHeader = () => {
  const{user,logout} = useAuth();
  
  const logOutHandler = async (e)=>{
    e.preventDefault();
    await logout();
  }
  return (
    <header className="border-b fixed top-0 left-0 right-0 z-50 backdrop-blur-xs border-gray-300 px-4 lg:px-10  ">
        <div className="container mx-auto px-4 py-4 flex items-center justify-between">
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
          >

           <Link to="/" className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-lg  gradient-bg flex items-center justify-center">
              <Link2 className="w-4 h-4 text-white" />
            </div>
            <span className="text-xl font-bold gradient-text bg-clip-text text-transparent">
              SnapUrl
            </span></Link>
          </motion.div>

          <div className="flex items-center gap-4">
            {user && <span className="text-sm text-gray-500">@{user.username}</span>}
            <button className="hover:cursor-pointer hover:text-red-600 transition-all duration-300" onClick={logOutHandler}>
              Logout
            </button>
          </div>
        </div>
      </header>
  )
}

export default UseDashboardHeader