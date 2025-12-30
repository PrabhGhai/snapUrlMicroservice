import React, { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Copy, Share2, X, CheckCircle } from "lucide-react";

const backdropVariants = {
  hidden: { opacity: 0 },
  visible: { opacity: 1 },
};

const cardVariants = {
  hidden: { opacity: 0, scale: 0.9, y: 40 },
  visible: {
    opacity: 1,
    scale: 1,
    y: 0,
    transition: { duration: 0.4, ease: "easeOut" },
  },
  exit: {
    opacity: 0,
    scale: 0.9,
    y: 40,
    transition: { duration: 0.25 },
  },
};

const ShowShortUrlCard = ({ shortUrl, onClose }) => {
  const [copied, setCopied] = useState(false);

  const handleCopy = async () => {
    await navigator.clipboard.writeText(shortUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 1800);
  };

  const handleShare = async () => {
    if (navigator.share) {
      await navigator.share({
        title: "Check out my short URL 🚀",
        text: "I just created a short link, try it out!",
        url: shortUrl,
      });
    } else {
      handleCopy();
    }
  };

  return (
    <AnimatePresence>  {/*Helps to unmount the card from react with some delay*/}
      <motion.div
        className="fixed inset-0 z-50 flex items-center justify-center bg-black/60"
        variants={backdropVariants}
        initial="hidden"
        animate="visible"
        exit="hidden"
      >
        <motion.div
          className="relative w-[90%] max-w-md rounded-2xl bg-white/10
                     backdrop-blur-xl p-6 shadow-2xl"
          variants={cardVariants}
          initial="hidden"
          animate="visible"
          exit="exit"
        >
         
          <button
            onClick={onClose}
            className="absolute right-4 top-4 text-white/70 hover:text-white transition hover:cursor-pointer"
          >
            <X size={22} />
          </button>

          <div className="flex justify-center mb-4">
            <CheckCircle className="text-cyan-400" size={48} />
          </div>

          {/* Heading */}
          <h2 className="text-center text-2xl font-semibold text-white">
            Your Short URL is Ready 🎉
          </h2>

          <p className="text-center text-gray-300 text-sm mt-2">
            Share your exciting new short link with friends and enjoy effortless
            sharing.
          </p>

          <motion.div
            whileHover={{ scale: 1.02 }}
            className="mt-6 bg-black/40 border border-white/10 rounded-xl p-3 text-center"
          >
            <a
              href={shortUrl}
              target="_blank"
              rel="noopener noreferrer"
              className="text-white font-mono break-all hover:underline"
            >
              {shortUrl}
            </a>
          </motion.div>

          <div className="flex gap-3 mt-6">
            <motion.button
              whileTap={{ scale: 0.95 }}
              onClick={handleCopy}
              className="flex-1 flex items-center justify-center gap-2
                         bg-cyan-500 hover:bg-cyan-400 text-black transition-all duration-300 
                         font-medium py-2 rounded-xl hover:cursor-pointer"
            >
              <Copy size={18} />
              {copied ? "Copied!" : "Copy"}
            </motion.button>

            <motion.button
              whileTap={{ scale: 0.95 }}
              onClick={handleShare}
              className="flex-1 flex items-center justify-center gap-2
                         bg-white/20 hover:bg-white/30 text-white
                         py-2 rounded-xl transition hover:cursor-pointer"
            >
              <Share2 size={18} />
              Share
            </motion.button>
          </div>
        </motion.div>
      </motion.div>
    </AnimatePresence>
  );
};

export default ShowShortUrlCard;
