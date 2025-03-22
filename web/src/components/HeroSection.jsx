"use client";

import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { Button } from "@/components/ui/button";
import { ChevronRight } from "lucide-react";

const images = [
  "/images/hero-img-1.jpg",
  "/images/hero-img-2.jpg",
  "/images/hero-img-3.jpg",
];

export default function HeroSection() {
//   const router = useRouter();
  const [currentImage, setCurrentImage] = useState(0);
  const [expandArrow, setExpandArrow] = useState(false);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentImage((prev) => (prev + 1) % images.length);
    }, 4000);
    return () => clearInterval(interval);
  }, []);

  const handleGetStarted = () => {
    setExpandArrow(true);
    setTimeout(() => {
    //   router.push("/signup");
    }, 800);
  };

  return (
    <>
    <motion.section
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 1 }}
      className="relative flex flex-col md:flex-row items-center justify-between h-screen px-8 md:px-16 lg:px-24 dark:bg-black bg-gray-200 dark:text-white text-black overflow-hidden"
    >
      {/* Falling Star Effect */}
      <motion.div
        className="absolute inset-0 dark:bg-black bg-gray-200"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ duration: 1.5 }}
      />
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        {[...Array(20)].map((_, i) => (
          <motion.div
            key={i}
            className="absolute dark:w-1 dark:h-1 w-2 h-2 dark:bg-gray-200 bg-black rounded-full opacity-70"
            initial={{ y: -50, x: Math.random() * window.innerWidth }}
            animate={{ y: window.innerHeight, opacity: 0 }}
            transition={{ duration: Math.random() * 5 + 2, repeat: Infinity, delay: Math.random() * 3 }}
          />
        ))}
      </div>

      {/* Left Section: Text & Button */}
      <div className="relative z-10 max-w-lg">
        <motion.h1
          initial={{ x: -50, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ duration: 1 }}
          className="text-5xl md:text-6xl font-bold leading-tight "
        >
          Securely Share <br /> Your Files
        </motion.h1>
        <motion.p
          initial={{ x: -50, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ duration: 1, delay: 0.3 }}
          className="mt-4 text-lg dark:text-gray-300 text-black"
        >
          Protect your files with end-to-end encryption. Share with confidence!
        </motion.p>
        <motion.div className="mt-6 inline-block" initial={{ opacity: 0 }} animate={{ opacity: 1 }} transition={{ duration: 1, delay: 0.6 }}>
          <Button
            className="px-6 py-3 text-lg font-semibold bg-blue-600 hover:bg-blue-500 rounded-full transition-all duration-500 shadow-xl flex items-center gap-3 relative overflow-hidden"
            onClick={handleGetStarted}
          >
            Get Started
            <motion.div
              animate={{ width: expandArrow ? "150px" : "24px", opacity: expandArrow ? 0 : 1 }}
              transition={{ duration: 0.8 }}
              className="bg-blue-700 h-[2px] absolute left-full"
            />
            <motion.div initial={{ x: 0 }} animate={{ x: expandArrow ? 50 : 0 }} transition={{ duration: 0.5 }} className="ml-2">
              <ChevronRight className="text-white" size={24} />
            </motion.div>
          </Button>
        </motion.div>
      </div>

      {/* Right Section: Image Slider */}
      <motion.div
        className="relative w-full md:w-[50%] max-w-xl h-[300px] md:h-[400px] overflow-hidden rounded-xl shadow-lg mt-10 md:mt-0"
        initial={{ x: 50, opacity: 0 }}
        animate={{ x: 0, opacity: 1 }}
        transition={{ duration: 1, delay: 0.5 }}
      >
        {images.map((img, index) => (
          <motion.img
            key={index}
            src={img}
            alt="Secure File Sharing"
            className={`absolute inset-0 w-full h-full object-cover rounded-xl transition-opacity duration-1000 ${index === currentImage ? 'opacity-100' : 'opacity-0'}`}
          />
        ))}
        {/* Image Slider Dots */}
        <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex gap-2">
          {images.map((_, index) => (
            <span
              key={index}
              className={`w-3 h-3 rounded-full transition-all duration-300 ${index === currentImage ? 'bg-white scale-125' : 'bg-gray-500'}`}
            />
          ))}
        </div>
      </motion.div>
    </motion.section>
    <hr />
    </>
  );
}
