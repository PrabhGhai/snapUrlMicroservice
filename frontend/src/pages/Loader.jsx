import { motion } from "framer-motion";

const Loader = () => {
  return (
    <div className="fixed inset-0 flex items-center justify-center w-full h-screen text-white z-50">
      <div className="flex flex-col items-center gap-6">
        <motion.h1
          className="text-4xl md:text-5xl font-bold tracking-wide gradient-text bg-clip-text text-transparent"
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.6 }}
        >
          Snap URL
        </motion.h1>

        <div className="relative w-40 h-1 bg-gray-700 rounded overflow-hidden">
          <motion.div
            className="absolute left-0 top-0 h-full w-1/3 bg-cyan-600"
            animate={{ x: ["-100%", "300%"] }}
            transition={{
              repeat: Infinity,
              duration: 1.2,
              ease: "easeInOut",
            }}
          />
        </div>

        <motion.p
          className="text-sm text-gray-800"
          animate={{ opacity: [0.4, 1, 0.4] }}
          transition={{ repeat: Infinity, duration: 1.5 }}
        >
          Securing your links…
        </motion.p>
      </div>
    </div>
  );
};

export default Loader;
