import React, {useState}from 'react'
import {
  Plus,
  Link as LinkIcon,
} from "lucide-react";
import { motion } from "framer-motion";

const CreateUrlInput = () => {
     const [url, setUrl] = useState("");
     const handleShorten = (e) => {
        e.preventDefault();
        setUrl("");
    };
  return (
     <motion.div 
     initial={{ opacity: 0, y: 20 }}
     animate={{ opacity: 1, y: 0 }}
     transition={{ duration: 0.5, ease: "easeOut"  }}
     className="px-6 md:px-32 pt-32"> 
            <div className="bg-white px-8 py-8 mb-8 rounded-2xl border border-gray-200 shadow-sm ">
             <h2 className="mb-4 text-xl font-semibold">Shorten a URL</h2>
              <form
              onSubmit={handleShorten}
               className="flex-row md:flex gap-4 "
              >
              <input
               type="url"
               placeholder="Paste your long URL here..."
               value={url}
               onChange={(e) => setUrl(e.target.value)}
               className="flex-1 w-full md:w-auto h-12 px-4 rounded-xl bg-gray-100 border border-gray-300 outline-none focus:ring-2 focus:ring-cyan-500"
              />
              <button
               type="submit"
               className="gradient-bg mt-5 md:mt-0 w-full md:w-auto justify-center text-white px-6 py-3 rounded-xl font-semibold flex items-center gap-2 hover:opacity-90 transition"
               >
               <Plus size={16} />
               Shorten
              </button>
            </form>         
            </div>
        </motion.div>
  )
}

export default CreateUrlInput