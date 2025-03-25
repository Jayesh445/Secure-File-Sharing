
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { ArrowLeft, ServerCrash } from "lucide-react";
import { motion } from "framer-motion";

const ServerError = () => {
  // Animation variants
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: { 
      opacity: 1,
      transition: { 
        when: "beforeChildren",
        staggerChildren: 0.2,
        duration: 0.3
      }
    }
  };
  
  const itemVariants = {
    hidden: { y: 20, opacity: 0 },
    visible: { 
      y: 0, 
      opacity: 1,
      transition: { duration: 0.5 }
    }
  };
  
  const iconVariants = {
    hidden: { scale: 0.8, opacity: 0, rotate: 15 },
    visible: { 
      scale: 1, 
      opacity: 1, 
      rotate: 0,
      transition: { 
        duration: 0.5,
        type: "spring",
        stiffness: 200
      }
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-background px-4">
      <motion.div 
        className="text-center"
        variants={containerVariants}
        initial="hidden"
        animate="visible"
      >
        <motion.div 
          className="flex justify-center mb-6"
          variants={iconVariants}
        >
          <motion.div 
            className="h-24 w-24 rounded-full bg-destructive/10 flex items-center justify-center"
            animate={{ 
              boxShadow: [
                "0 0 0 0 rgba(239, 68, 68, 0)",
                "0 0 0 15px rgba(239, 68, 68, 0.2)",
                "0 0 0 0 rgba(239, 68, 68, 0)"
              ]
            }}
            transition={{ 
              repeat: Infinity,
              duration: 2
            }}
          >
            <ServerCrash className="h-12 w-12 text-destructive" />
          </motion.div>
        </motion.div>
        
        <motion.h1 
          className="text-5xl md:text-7xl font-bold text-foreground mb-4"
          variants={itemVariants}
        >
          500
        </motion.h1>
        
        <motion.p 
          className="text-xl text-muted-foreground mb-8"
          variants={itemVariants}
        >
          Server Error! Something went wrong on our end.
        </motion.p>
        
        <motion.div variants={itemVariants}>
          <Button 
            asChild 
            size="lg"
            className="transition-all duration-300 hover:scale-105"
          >
            <Link to="/">
              <ArrowLeft className="mr-2 h-4 w-4 transition-transform group-hover:-translate-x-1" />
              Return Home
            </Link>
          </Button>
        </motion.div>
      </motion.div>
    </div>
  );
};

export default ServerError;
