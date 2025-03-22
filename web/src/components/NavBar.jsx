"use client";

import { useState, useEffect } from "react";
import { motion } from "framer-motion";
import { useTheme } from "next-themes";
import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Sun, Moon, Menu } from "lucide-react";

export default function Navbar() {
  const { theme, setTheme } = useTheme();
  const [isOpen, setIsOpen] = useState(false);
  const [showNavbar, setShowNavbar] = useState(true);

  // Handle Navbar Auto-Hide on Scroll
  useEffect(() => {
    let lastScrollY = window.scrollY;
    const handleScroll = () => {
      const currentScrollY = window.scrollY;
      setShowNavbar(currentScrollY < lastScrollY || currentScrollY < 50);
      lastScrollY = currentScrollY;
    };
    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return (
    <motion.nav
      initial={{ y: -100, opacity: 0 }}
      animate={{ y: showNavbar ? 0 : -100, opacity: showNavbar ? 1 : 0 }}
      transition={{ duration: 0.5, ease: "easeInOut" }}
      className={cn(
        "w-full fixed top-0 left-0 z-50 transition-all duration-500 shadow-lg backdrop-blur-md",
        "bg-white/70 dark:bg-black/80"
      )}
      data-theme={theme} // Ensures full UI transition
    >
      <div className="container mx-auto flex justify-between items-center p-4">
        {/* Logo & Name */}
        <motion.div
          initial={{ x: -20, opacity: 0 }}
          animate={{ x: 0, opacity: 1 }}
          transition={{ delay: 0.2 }}
          className="flex items-center space-x-2"
        >
          <div className="bg-gradient-to-r from-blue-500 to-purple-600 p-2 rounded-lg">
            <span className="text-white font-bold text-lg">SFS</span>
          </div>
          <span className="text-xl font-semibold dark:text-white">
            Secure File Share
          </span>
        </motion.div>

        {/* Menu (Desktop) */}
        <div className="hidden md:flex space-x-6">
          {["Home", "About Us", "Contact Us"].map((item, index) => (
            <motion.a
              key={index}
              href={`#${item.toLowerCase().replace(" ", "-")}`}
              whileHover={{ scale: 1.1 }}
              className="text-gray-700 dark:text-gray-200 hover:text-blue-500 dark:hover:text-blue-400 transition"
            >
              {item}
            </motion.a>
          ))}
        </div>

        {/* Right Side: Login, Signup, Theme Toggle */}
        <div className="hidden md:flex items-center space-x-4">
          <Button variant="outline">Login</Button>
          <Button>Signup</Button>
          <Button
            variant="ghost"
            onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
            className="transition-all duration-500"
          >
            {theme === "dark" ? <Sun className="text-yellow-400" /> : <Moon />}
          </Button>
        </div>

        {/* Mobile Menu Button */}
        <motion.button
          className="md:hidden text-gray-700 dark:text-gray-200"
          whileTap={{ scale: 0.9 }}
          onClick={() => setIsOpen(!isOpen)}
        >
          <Menu />
        </motion.button>
      </div>

      {/* Mobile Menu */}
      {isOpen && (
        <motion.div
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -10 }}
          className="md:hidden bg-white/80 dark:bg-gray-900/80 border-t shadow-md py-3 backdrop-blur-md"
        >
          <div className="flex flex-col space-y-4 items-center">
            {["Home", "About Us", "Contact Us"].map((item, index) => (
              <a
                key={index}
                href={`#${item.toLowerCase().replace(" ", "-")}`}
                className="text-gray-700 dark:text-gray-200 hover:text-blue-500 dark:hover:text-blue-400 transition"
              >
                {item}
              </a>
            ))}
            <Button variant="outline">Login</Button>
            <Button>Signup</Button>
            <Button
              variant="ghost"
              onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
              className="transition-all duration-500"
            >
              {theme === "dark" ? <Sun className="text-yellow-400" /> : <Moon />}
            </Button>
          </div>
        </motion.div>
      )}
    </motion.nav>
  );
}
