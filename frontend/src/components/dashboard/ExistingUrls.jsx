import { useState,useEffect } from "react";
import { handleCopy , redirectUrl, handleDelete, fetchLinks} from "../../services/ExistingUrlsService";
import {
  Copy,
  ExternalLink,
  Trash2,
  Link as LinkIcon,
} from "lucide-react";
import { motion } from "framer-motion";


const ExistingUrls = () => {
    const [links, setLinks] = useState([]);
    useEffect(() => {
    setLinks(fetchLinks());
    }, []);

  return (
    <div className="px-6 md:px-32 pb-8">
        <div className="space-y-4">
              {links.map((link,i) => (
                <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration:0.5, delay : i*0.08, ease: "easeOut"   }}
                  key={link.id}
                  className="bg-white p-5 rounded-2xl border border-gray-200 hover:shadow-lg transition"
                >
                  <div className="flex justify-between items-start">
                    <div>
                      <p className="font-semibold gradient-text">
                        snapurl.app/{link.shortCode}
                      </p>
                      <p className="text-sm text-gray-500 truncate max-w-xl">
                        {link.originalUrl}
                      </p>
                    </div>

                    <div className="flex gap-2">
                      <button
                        onClick={(e)=>handleCopy(e,link.shortCode)}
                        className="p-2 rounded-md hover:bg-gray-100"
                      >
                        <Copy size={16} />
                      </button>

                      <button className="p-2 rounded-md hover:bg-gray-100" onClick={(e)=>redirectUrl(e,link.shortCode)}>
                        <ExternalLink size={16} />
                      </button>

                      <button
                        onClick={handleDelete}
                        className="p-2 rounded-md text-gray-400 hover:text-red-500 transition"
                      >
                        <Trash2 size={16} />
                      </button>
                    </div>
                  </div>
                 <hr className="my-4 border-gray-300"/>
                  <div className=" flex text-sm text-gray-500">
                    <span>{link.createdAt}</span>
                  </div>
                </motion.div>
              ))}
            </div></div>
  )
}

export default ExistingUrls