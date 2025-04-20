import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import path from "path";
import vercel from 'vite-plugin-vercel';

// https://vitejs.dev/config/
export default defineConfig(() => ({
  base:"/",
  server: {
    cors:{
      origin: "https://secure-file-share-2svw.onrender.com"
    }
  },
  plugins: [
    react(),
    vercel()
  ].filter(Boolean),
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  build: {
    chunkSizeWarningLimit: 1024,
    outDir: "dist",
    manifest:true,
  }
}));
