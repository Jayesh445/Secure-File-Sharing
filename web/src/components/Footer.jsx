"use client";

import { motion } from "framer-motion";
import { Mail, FileText, ShieldCheck, Twitter, Linkedin, Github } from "lucide-react";

export default function Footer() {
  return (
    <motion.footer
      initial={{ opacity: 0, y: 30 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.8, ease: "easeOut" }}
      className="w-full bg-gray-900/80 dark:bg-gray-800/80 text-white dark:text-gray-300 backdrop-blur-md shadow-lg border-t border-gray-700"
    >
      <div className="container mx-auto px-6 py-8 flex flex-col md:flex-row justify-between items-center space-y-6 md:space-y-0">
        {/* Left Side - Contact Info */}
        <div className="text-center md:text-left">
          <motion.p
            whileHover={{ scale: 1.05 }}
            className="text-lg font-semibold"
          >
            Contact Us
          </motion.p>
          <motion.a
            href="mailto:support@example.com"
            whileHover={{ scale: 1.05, color: "#4F46E5" }}
            className="flex items-center justify-center md:justify-start space-x-2 text-gray-400 hover:text-blue-400 transition"
          >
            <Mail size={18} />
            <span>support@example.com</span>
          </motion.a>
        </div>

        {/* Middle - Links */}
        <div className="flex space-x-6">
          <motion.a
            href="/privacy-policy"
            whileHover={{ scale: 1.05, color: "#4F46E5" }}
            className="flex items-center space-x-2 text-gray-400 hover:text-blue-400 transition"
          >
            <FileText size={18} />
            <span>Privacy Policy</span>
          </motion.a>
          <motion.a
            href="/terms-of-service"
            whileHover={{ scale: 1.05, color: "#4F46E5" }}
            className="flex items-center space-x-2 text-gray-400 hover:text-blue-400 transition"
          >
            <ShieldCheck size={18} />
            <span>Terms of Service</span>
          </motion.a>
        </div>

        {/* Right Side - Social Media Links */}
        <div className="flex space-x-4">
          <motion.a
            href="https://twitter.com"
            target="_blank"
            rel="noopener noreferrer"
            whileHover={{ scale: 1.1, color: "#1DA1F2" }}
            className="text-gray-400 hover:text-blue-500 transition"
          >
            <Twitter size={24} />
          </motion.a>
          <motion.a
            href="https://linkedin.com"
            target="_blank"
            rel="noopener noreferrer"
            whileHover={{ scale: 1.1, color: "#0A66C2" }}
            className="text-gray-400 hover:text-blue-500 transition"
          >
            <Linkedin size={24} />
          </motion.a>
          <motion.a
            href="https://github.com"
            target="_blank"
            rel="noopener noreferrer"
            whileHover={{ scale: 1.1, color: "#F5F5F5" }}
            className="text-gray-400 hover:text-gray-300 transition"
          >
            <Github size={24} />
          </motion.a>
        </div>
      </div>

      {/* Bottom Copyright */}
      <div className="text-center text-sm text-gray-500 dark:text-gray-400 py-4 border-t border-gray-700">
        © {new Date().getFullYear()} Secure File Share. All Rights Reserved.
      </div>
    </motion.footer>
  );
}
