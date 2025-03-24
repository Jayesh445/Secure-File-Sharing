
import { useRef } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import Navbar from '@/components/Navbar';
import Hero3D from '@/components/Hero3D';
import ImageSlider from '@/components/ImageSlider';
import FeatureCard from '@/components/FeatureCard';
import { ArrowRight, Shield, Zap, Lock, Eye, FileText, Clock, Users, GitPullRequest } from 'lucide-react';

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
      <section ref={featuresRef} id="features" className="py-20 px-4 bg-muted/30">
        <div className="container mx-auto">
          <div className="text-center mb-16">
            <span className="inline-block text-sm font-medium text-primary mb-2">FEATURES</span>
            <h2 className="text-3xl md:text-4xl font-bold mb-4">Everything You Need for Secure Sharing</h2>
            <p className="text-muted-foreground max-w-2xl mx-auto">
              SecureShare combines powerful security features with an intuitive interface to make sharing files both safe and simple.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <FeatureCard
              icon={<Shield className="h-6 w-6 text-primary" />}
              title="End-to-End Encryption"
              description="Your files are encrypted before they leave your device and can only be decrypted by intended recipients."
            />
            
            <FeatureCard
              icon={<Lock className="h-6 w-6 text-primary" />}
              title="Password Protection"
              description="Add an extra layer of security with password-protected file sharing links."
            />
            
            <FeatureCard
              icon={<Clock className="h-6 w-6 text-primary" />}
              title="Expiring Links"
              description="Set links to expire after a specific time or number of downloads for temporary access."
            />
            
            <FeatureCard
              icon={<Eye className="h-6 w-6 text-primary" />}
              title="Access Control"
              description="Control who can view, download, or edit your shared files with granular permissions."
            />
            
            <FeatureCard
              icon={<FileText className="h-6 w-6 text-primary" />}
              title="File Versioning"
              description="Keep track of changes with automatic versioning for all your important documents."
            />
            
            <FeatureCard
              icon={<Zap className="h-6 w-6 text-primary" />}
              title="Lightning Fast"
              description="Our optimized infrastructure ensures rapid uploads and downloads of files of any size."
            />
          </div>
          
          <div className="mt-16 text-center">
            <Button asChild size="lg">
              <Link to="/signup">
                Get Started <ArrowRight className="ml-2 h-4 w-4" />
              </Link>
            </Button>
          </div>
        </div>
      </section>
      
      {/* How It Works Section */}
      <section className="py-20 px-4">
        <div className="container mx-auto">
          <div className="text-center mb-16">
            <span className="inline-block text-sm font-medium text-primary mb-2">WORKFLOW</span>
            <h2 className="text-3xl md:text-4xl font-bold mb-4">How It Works</h2>
            <p className="text-muted-foreground max-w-2xl mx-auto">
              Sharing files securely has never been easier. Follow these simple steps to get started.
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
            <div className="flex flex-col items-center text-center p-6">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <GitPullRequest className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">1. Upload</h3>
              <p className="text-muted-foreground">
                Drag and drop your files or select them from your device to upload them securely.
              </p>
            </div>
            
            <div className="flex flex-col items-center text-center p-6">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <Lock className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">2. Configure</h3>
              <p className="text-muted-foreground">
                Set permissions, add passwords, and configure expiration settings for your files.
              </p>
            </div>
            
            <div className="flex flex-col items-center text-center p-6">
              <div className="h-16 w-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
                <Users className="h-8 w-8 text-primary" />
              </div>
              <h3 className="text-xl font-medium mb-2">3. Share</h3>
              <p className="text-muted-foreground">
                Share your secure link via email, messaging apps, or copy it directly to the clipboard.
              </p>
            </div>
          </div>
        </div>
      </section>
      
      {/* CTA Section */}
      <section className="py-20 px-4 bg-primary text-white">
        <div className="container mx-auto text-center">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">Ready to Share Securely?</h2>
          <p className="max-w-2xl mx-auto mb-8 text-white/80">
            Join thousands of users who trust SecureShare for their secure file sharing needs.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button asChild size="lg" variant="secondary" className="bg-white text-primary hover:bg-white/90">
              <Link to="/signup">
                Create Free Account
              </Link>
            </Button>
            <Button asChild size="lg" variant="outline" className="border-white/20 hover:bg-white/10">
              <Link to="/#features">
                Learn More
              </Link>
            </Button>
          </div>
        </div>
      </section>
      
      {/* Footer */}
      <footer className="py-12 px-4 bg-muted/50 border-t">
        <div className="container mx-auto">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div>
              <Link to="/" className="flex items-center gap-2 mb-4">
                <div className="h-8 w-8 bg-primary rounded-md flex items-center justify-center">
                  <GitPullRequest className="h-5 w-5 text-white" />
                </div>
                <span className="font-bold text-xl">SecureShare</span>
              </Link>
              <p className="text-muted-foreground text-sm">
                Secure file sharing made simple. Share with confidence.
              </p>
            </div>
            
            <div>
              <h3 className="font-medium mb-4">Product</h3>
              <ul className="space-y-2">
                <li><Link to="/#features" className="text-muted-foreground text-sm hover:text-primary">Features</Link></li>
                <li><Link to="/#pricing" className="text-muted-foreground text-sm hover:text-primary">Pricing</Link></li>
                <li><Link to="/#about" className="text-muted-foreground text-sm hover:text-primary">About</Link></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-medium mb-4">Resources</h3>
              <ul className="space-y-2">
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Documentation</a></li>
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Help Center</a></li>
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Blog</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-medium mb-4">Legal</h3>
              <ul className="space-y-2">
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Privacy Policy</a></li>
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Terms of Service</a></li>
                <li><a href="#" className="text-muted-foreground text-sm hover:text-primary">Cookie Policy</a></li>
              </ul>
            </div>
          </div>
          
          <div className="mt-12 pt-6 border-t border-border text-center text-sm text-muted-foreground">
            <p>&copy; {new Date().getFullYear()} SecureShare. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Index;
