import { motion } from "framer-motion";
import { Link } from "react-router-dom";
import { ArrowRight, Link2, Zap, Shield, BarChart3 } from "lucide-react";

const HeroSection = () => {
  const features = [
    {
      icon: Zap,
      title: "Lightning Fast",
      description: "Create short links in milliseconds",
    },
    {
      icon: Shield,
      title: "Secure & Reliable",
      description: "Enterprise-grade security for your links",
    },
    {
      icon: BarChart3,
      title: "Analytics",
      description: "Track clicks and engagement metrics",
    },
  ];

  return (
    <section className="relative min-h-screen gradient-hero overflow-hidden">
      {/* Background decorations */}
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <motion.div
          animate={{ y: [0, -20, 0], rotate: [0, 5, 0] }}
          transition={{ duration: 6, repeat: Infinity, ease: "easeInOut" }}
          className="absolute top-20 left-10 w-72 h-72 bg-cyan-50 rounded-full blur-3xl"
        />
        <motion.div
          animate={{ y: [0, 20, 0], rotate: [0, -5, 0] }}
          transition={{ duration: 8, repeat: Infinity, ease: "easeInOut" }}
          className="absolute bottom-20 right-10 w-96 h-96 bg-violet-100 rounded-full blur-3xl"
        />

      </div>

      <div className="container mx-auto px-4 sm:px-6 lg:px-8 pt-32 pb-20 relative z-10">
        <div className="max-w-4xl mx-auto text-center">
          {/* Badge */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
            className="inline-flex items-center gap-2 px-4 py-2 rounded-full bg-white border border-gray-200 mb-8"
          >
            <span className="relative flex h-2 w-2">
              <span className="animate-ping absolute inline-flex h-full w-full rounded-full  opacity-75"></span>
              <span className="relative inline-flex rounded-full h-2 w-2 bg-cyan-500"></span>
            </span>
            <span className="text-sm font-medium text-gray-500">
              Trusted by 10,000+ users worldwide
            </span>
          </motion.div>

          {/* Main heading */}
          <motion.h1
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.1 }}
            className="text-4xl sm:text-5xl md:text-6xl lg:text-7xl font-bold tracking-tight mb-6"
          >
            Shorten Links,{" "}
            <span className="gradient-text">Amplify Reach</span>
          </motion.h1>

          {/* Subtitle */}
          <motion.p
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.2 }}
            className="text-lg sm:text-xl text-muted-foreground max-w-2xl mx-auto mb-10"
          >
            Transform long, ugly URLs into clean, memorable short links.
            Track clicks, analyze performance, and grow your brand with Shortify.
          </motion.p>

          {/* CTA buttons */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.3 }}
            className="flex flex-col sm:flex-row gap-4 justify-center items-center mb-16"
          >
            <Link to="/signup">
              <button className="group gradient-bg text-white flex items-center justify-center px-6 py-4 gap-4 font-bold text-xl rounded-lg hover:cursor-pointer ">
                Get Started Free
                <ArrowRight className="w-5 h-5 transition-transform group-hover:translate-x-1" />
              </button>
            </Link>
            <Link to="/login">
              <button className=" bg-white border border-gray-300 px-6 py-4 gap-4 hover:shadow  text-xl rounded-lg hover:cursor-pointer">
                Sign in to Dashboard
              </button>
            </Link>
          </motion.div>

          {/* URL Input Preview */}
          <motion.div
            initial={{ opacity: 0, y: 40 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6, delay: 0.4 }}
            className="relative max-w-2xl mx-auto"
          >
            <div className="bg-white rounded-2xl  p-6 backdrop-blur-sm shadow-xl">
              <div className="flex flex-col sm:flex-row gap-3">
                <div className="flex-1 relative">
                  <Link2 className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                  <input
                    type="text"
                    placeholder="Paste your long URL here..."
                    className="w-full h-12 pl-12 pr-4 rounded-xl border border-gray-300 bg-gray-100 outline-none transition-all text-sm"
                    disabled
                  />
                </div>
                <button className="gradient-bg px-6 py-2 rounded-xl text-white" disabled>
                  Shorten
                </button>
              </div>
              <p className="text-xs text-gray-500  mt-3 text-center">
                Sign up to start shortening URLs • No credit card required
              </p>
            </div>

            {/* Floating elements */}
            <motion.div
              animate={{ y: [0, -8, 0] }}
              transition={{ duration: 3, repeat: Infinity, ease: "easeInOut" }}
              className="absolute -top-4 -right-4  bg-white rounded-xl shadow-soft  px-4 py-2 hidden md:block"
            >
              <span className="text-2xl font-bold gradient-text ">2.5M+</span>
              <p className="text-xs text-muted-foreground">Links created</p>
            </motion.div>

            <motion.div
              animate={{ y: [0, 8, 0] }}
              transition={{ duration: 4, repeat: Infinity, ease: "easeInOut", delay: 1 }}
              className="absolute -bottom-4 -left-4 bg-white rounded-xl shadow-soft  px-4 py-2 hidden md:block"
            >
              <span className="text-2xl font-bold gradient-text">99.9%</span>
              <p className="text-xs text-muted-foreground">Uptime</p>
            </motion.div>
          </motion.div>
        </div>

        {/* Features Grid */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.5, delay: 0.6 }}
          className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-4xl mx-auto mt-24"
        >
          {features.map((feature, index) => (
            <motion.div
              key={feature.title}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.7 + index * 0.1 }}
              whileHover={{ y: -4, transition: { duration: 0.2 } }}
              className="group p-6 rounded-2xl bg-white backdrop-blur-sm hover:cursor-pointer  hover:shadow-soft transition-all duration-300"
            >
              <div className="w-12 h-12 rounded-xl gradient-bg flex items-center justify-center mb-4 group-hover:scale-110 transition-transform duration-300">
                <feature.icon className="w-6 h-6 text-primary-foreground text-white" />
              </div>
              <h3 className="text-lg font-semibold mb-2 ">{feature.title}</h3>
              <p className="text-sm text-muted-foreground">
                {feature.description}
              </p>
            </motion.div>
          ))}
        </motion.div>
      </div>
    </section>
  );
};

export default HeroSection;
