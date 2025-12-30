import { useState, useEffect } from "react";
import {
  handleCopy,
  redirectUrl,
  handleDelete,
  fetchLinks,
} from "../../services/ExistingUrlsService";
import {
  Copy,
  ExternalLink,
  Trash2,
  Link as LinkIcon,
} from "lucide-react";
import { motion } from "framer-motion";
import { useUrlService } from "../../store/UrlServiceContext";

const ExistingUrls = () => {
  const [links, setLinks] = useState([]);
  const [refresh, setRefresh] = useState(false);
  const {showShortUrlDiv} = useUrlService()


  useEffect(() => {
  const loadLinks = async () => {
    try {
      const data = await fetchLinks();
      setLinks(data);
    } catch (err) {
     
    }
  };

  loadLinks();
}, [refresh,showShortUrlDiv]);


  return (
   <div className="px-4 md:px-32 pb-8">
  <div className="space-y-4">
    {links && links.length > 0 ? (
      links.map((link, i) => (
        <motion.div
          key={i}
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{
            duration: 0.5,
            delay: i * 0.08,
            ease: "easeOut",
          }}
          className="relative bg-white p-5 rounded-2xl border border-gray-200 hover:shadow-lg transition"
        >
          
          <div className="absolute top-4 right-4 md:static md:flex md:justify-end flex gap-2 shrink-0">
            <button
              onClick={(e) => handleCopy(e, link.short_url)}
              className="p-2 rounded-lg hover:bg-gray-100"
              title="Copy"
            >
              <Copy size={16} />
            </button>

            <button
              onClick={(e) => redirectUrl(e, link.short_url)}
              className="p-2 rounded-lg hover:bg-gray-100"
              title="Open"
            >
              <ExternalLink size={16} />
            </button>

            <button
              onClick={async(e)=>{
                await handleDelete(e,link.short_url);
                setRefresh((prev) => !prev);
              }}
              className="p-2 rounded-lg text-gray-400 hover:text-red-500 transition"
              title="Delete"
            >
              <Trash2 size={16} />
            </button>
          </div>
          <div className="pr-14 md:pr-0">
            <p className="font-semibold text-cyan-600 break-all">
              {link.short_url}
            </p>

            <p className="text-sm text-gray-500 mt-2 wrap-break-words">
              {link.long_url}
            </p>
          </div>

          <hr className="my-4 border-gray-200" />

          <div className="text-xs text-gray-500">
            Created At — {link.time}
          </div>
        </motion.div>
      ))
    ) : (
     
      <motion.div
        initial={{ opacity: 0, y: 30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.6, ease: "easeOut" }}
        className="flex flex-col items-center justify-center py-24 text-center"
      >
        <motion.div
          initial={{ scale: 0.8 }}
          animate={{ scale: 1 }}
          transition={{
            duration: 1,
            repeat: Infinity,
            repeatType: "reverse",
            ease: "easeInOut",
          }}
          className="mb-6 p-6 rounded-full bg-gradient-to-br from-purple-400 to-cyan-600 text-white shadow-xl"
        >
          <LinkIcon size={36} />
        </motion.div>

        <h2 className="text-2xl font-semibold mb-2">
          Hi there 👋 Create your first short URL
        </h2>

        <p className="text-gray-500 max-w-md">
          You don’t have any short links yet. Start by shortening your first URL
          and manage everything from this dashboard.
        </p>

        <motion.div
          initial={{ width: 0 }}
          animate={{ width: "120px" }}
          transition={{ delay: 0.4, duration: 0.6 }}
          className="h-1 mt-6 rounded-full bg-gradient-to-r from-cyan-400 to-purple-600"
        />
      </motion.div>
    )}
  </div>
</div>

  );
};

export default ExistingUrls;
