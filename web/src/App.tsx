import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { SpeedInsights } from "@vercel/speed-insights/react"

// Pages
import Index from "./pages/Index";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import Dashboard from "./pages/Dashboard";
import NotFound from "./pages/NotFound";
import Forbidden from "./pages/Forbidden";
import ServerError from "./pages/ServerError";
import AdminDashboard from "./pages/Admin";
import About from "./pages/About";
import Features from "./pages/Features";
import ProtectedRoute from "./components/ProtectedRoute";
import FilePreview from "./pages/FilePreview";

const App = () => {
  return (
    // <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <BrowserRouter>
          <Toaster />
          <Sonner />
          <SpeedInsights/>
          <Routes>
            <Route path="/" element={<Index />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/features" element={<Features />} />
            <Route path="/about" element={<About />} />

            <Route element={<ProtectedRoute requiredRole="USER" />}>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/preview/:fileId" element={<FilePreview />}/>
            </Route>

            <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
              <Route path="/admin" element={<AdminDashboard />} />
            </Route>
            <Route path="/403" element={<Forbidden />} />
            <Route path="/500" element={<ServerError />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </TooltipProvider>  
    // </QueryClientProvider>
  );
};

export default App;
