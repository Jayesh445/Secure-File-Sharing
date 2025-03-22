"use client";

import { useEffect, useState } from "react";
import { motion } from "framer-motion";
import { Card, CardContent } from "@/components/ui/card";
import { Lock, CloudUpload, ShieldCheck, MonitorSmartphone } from "lucide-react";

const features = [
  {
    title: "End-to-End Encryption",
    description: "Your files are always secure with top-tier encryption.",
    icon: <Lock size={40} />,
  },
  {
    title: "Fast & Secure Uploads",
    description: "Upload and share large files instantly without delays.",
    icon: <CloudUpload size={40} />,
  },
  {
    title: "Access Controls",
    description: "Set file permissions to control how others interact with them.",
    icon: <ShieldCheck size={40} />,
  },
  {
    title: "Multi-Platform",
    description: "Works seamlessly across web, mobile, and desktop devices.",
    icon: <MonitorSmartphone size={40} />,
  },
];

export default function FeaturesSection() {
  const [mousePos, setMousePos] = useState({ x: 0, y: 0 });

  useEffect(() => {
    const updateMousePosition = (e) => {
      setMousePos({ x: e.clientX, y: e.clientY });
    };
    window.addEventListener("mousemove", updateMousePosition);
    return () => window.removeEventListener("mousemove", updateMousePosition);
  }, []);

  return (
    <section className="relative py-16 px-4 sm:px-8 bg-gradient-to-b from-gray-100 to-gray-300 dark:from-gray-900 dark:to-black text-black dark:text-white overflow-hidden">
      {/* Mouse-following Shine Effect */}
      <motion.div
        className="absolute inset-0 pointer-events-none"
        animate={{ x: mousePos.x, y: mousePos.y }}
        transition={{ type: "tween", ease: "easeOut", duration: 0.2 }}
      >
        <div className="w-[100px] h-[100px] rounded-full border border-gray-400/40 dark:border-gray-600/50 absolute -translate-x-1/2 -translate-y-1/2 blur-md opacity-30" />
      </motion.div>

      <div className="container mx-auto text-center">
        {/* Title */}
        <motion.h2
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ duration: 1 }}
          className="text-4xl font-extrabold bg-clip-text text-transparent bg-gradient-to-r from-gray-700 to-gray-900 dark:from-gray-200 dark:to-gray-500"
        >
          Why Choose Us?
        </motion.h2>

        <motion.p
          initial={{ y: 20, opacity: 0 }}
          animate={{ y: 0, opacity: 1 }}
          transition={{ duration: 1, delay: 0.3 }}
          className="text-lg text-gray-600 dark:text-gray-300 max-w-xl mx-auto mt-4"
        >
          Experience secure, seamless, and lightning-fast file sharing.
        </motion.p>

        {/* Features Grid */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mt-10">
          {features.map((feature, index) => (
            <motion.div
              key={index}
              whileHover={{ scale: 1.05, boxShadow: "0px 10px 30px rgba(255, 255, 255, 0.1)" }}
              className="relative flex justify-center"
            >
              <Card className="relative w-full max-w-[280px] sm:max-w-none border border-gray-300 dark:border-gray-700 shadow-md dark:shadow-gray-800 rounded-2xl overflow-hidden bg-gray-200 dark:bg-gray-950 transition-all duration-500 group">

              <motion.div
                className="absolute w-20 h-20 border-t-2 top-7 border-gray-400 dark:border-gray-600 rounded-full left-1/2 transform -translate-x-1/2"
                animate={{ rotate: 360 }}
                transition={{ duration: 3, repeat: Infinity, ease: "linear" }}
              />

                {/* Hover Glow Effect */}
                <motion.div
                  className="absolute inset-0 w-full h-full bg-gradient-to-br from-transparent to-gray-300 dark:to-gray-800 opacity-0 group-hover:opacity-20 transition-all duration-500"
                />

                <CardContent className="relative z-10 flex flex-col items-center space-y-4 p-6">
                  {/* Icon (Aligned with Silver Line) */}
                  <div className="relative p-4 bg-gray-300 dark:bg-gray-900 rounded-full shadow-md group-hover:shadow-lg transition-all duration-500 hover:border-gray-900 dark:hover:border-gray-400 hover:border-2">
                    {feature.icon}
                  </div>
                  {/* Title */}
                  <h3 className="text-lg font-semibold text-gray-900 dark:text-white">
                    {feature.title}
                  </h3>
                  {/* Description */}
                  <p className="text-gray-600 dark:text-gray-300 text-center">{feature.description}</p>
                </CardContent>
              </Card>
            </motion.div>
          ))}
        </div>
      </div>
    </section>
  );
}
