import { Shield, GitPullRequest, Users, Zap, FileText } from "lucide-react";
import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import FeatureSection from "@/components/FeatureSection";
import CTASection from "@/components/CTASection";

const About = () => {
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />

      {/* About Hero Section */}
      <section className="pt-32 pb-20 px-4 bg-gradient-to-b from-primary/90 to-blue-800 dark:from-gray-900 dark:to-gray-800 text-white">
        <div className="container mx-auto text-center">
          <h1 className="text-4xl md:text-5xl font-bold mb-6">
            About SecureShare
          </h1>
          <p className="text-xl text-white/80 max-w-2xl mx-auto">
            Protecting your data with industry-leading security while making
            file sharing simple.
          </p>
        </div>
      </section>

      {/* Mission Section */}
      <section className="py-20 px-4">
        <div className="container mx-auto">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
            <div>
              <span className="inline-block text-sm font-medium text-primary mb-2">
                OUR MISSION
              </span>
              <h2 className="text-3xl md:text-4xl font-bold mb-4">
                Secure Sharing for Everyone
              </h2>
              <p className="text-muted-foreground mb-6">
                At SecureShare, we believe that everyone should be able to share
                files without compromising their security or privacy. Our
                mission is to provide a platform that combines enterprise-grade
                security with consumer-grade simplicity.
              </p>
              <p className="text-muted-foreground">
                Founded in 2023, we've quickly grown to become a trusted partner
                for thousands of individuals and businesses who need to share
                sensitive information securely.
              </p>
            </div>
            <div className="bg-muted rounded-lg overflow-hidden">
              <img
                src="/login-side-img.png"
                alt="Our Mission"
                className="w-full h-full object-cover"
              />
            </div>
          </div>
        </div>
      </section>

      {/* Team Values */}
      <section className="py-20 px-4 bg-muted/30">
        <div className="container mx-auto">
          <div className="text-center mb-16">
            <span className="inline-block text-sm font-medium text-primary mb-2">
              OUR VALUES
            </span>
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              What We Stand For
            </h2>
            <p className="text-muted-foreground max-w-2xl mx-auto">
              Our company culture is built on these core principles that guide
              everything we do.
            </p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
            <div className="flex flex-col items-center text-center p-6 bg-card rounded-lg border border-border">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <Shield className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">Security First</h3>
              <p className="text-muted-foreground">
                We build with security as our foundation, never as an
                afterthought.
              </p>
            </div>

            <div className="flex flex-col items-center text-center p-6 bg-card rounded-lg border border-border">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <Users className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">User Privacy</h3>
              <p className="text-muted-foreground">
                We respect your data and give you complete control over your
                information.
              </p>
            </div>

            <div className="flex flex-col items-center text-center p-6 bg-card rounded-lg border border-border">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <Zap className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">Simplicity</h3>
              <p className="text-muted-foreground">
                We design intuitive experiences that anyone can use without
                technical expertise.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* Reusing the FeatureSection component */}
      <FeatureSection />

      {/* Reusing the CTA Section component */}
      <CTASection />

      {/* Footer */}
      <Footer />
    </div>
  );
};

export default About;
