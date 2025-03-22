import React from "react";
import Navbar from "@/components/NavBar";
import HeroSection from "@/components/HeroSection";
import FeaturesSection from "@/components/FeaturesSection";
import Footer from "@/components/Footer";

const Home = () => {
  return (
    <>
      <Navbar />
      <HeroSection />
      <FeaturesSection />
      <Footer />
    </>
  );
};

export default Home;
