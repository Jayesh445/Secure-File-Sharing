
import { useRef } from 'react';
import Navbar from '@/components/Navbar';
import Hero3D from '@/components/Hero3D';
import ImageSlider from '@/components/ImageSlider';
import FeatureSection from '@/components/FeatureSection';
import HowItWorks from '@/components/HowItWorks';
import CTASection from '@/components/CTASection';
import Footer from '@/components/Footer';

const Index = () => {
  const featuresRef = useRef<HTMLDivElement>(null);
  
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      
      {/* Hero section with 3D background */}
      <Hero3D />
      
      {/* Image Slider Section */}
      <section className="py-20 px-4">
        <div className="container mx-auto">
          <div className="text-center mb-12">
            <span className="inline-block text-sm font-medium text-primary mb-2">PREVIEW</span>
            <h2 className="text-3xl md:text-4xl font-bold mb-4">See SecureShare in Action</h2>
            <p className="text-muted-foreground max-w-2xl mx-auto">
              Take a glimpse at our intuitive interface designed for secure and efficient file sharing.
            </p>
          </div>
          
          <ImageSlider />
        </div>
      </section>
      
      {/* Features Section */}
      <div ref={featuresRef}>
        <FeatureSection />
      </div>
      
      {/* How It Works Section */}
      <HowItWorks />
      
      {/* CTA Section */}
      <CTASection />
      
      {/* Footer */}
      <Footer />
    </div>
  );
};

export default Index;
