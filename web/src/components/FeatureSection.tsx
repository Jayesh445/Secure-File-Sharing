
import { Shield, Zap, Lock, Eye, FileText, Clock } from 'lucide-react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { ArrowRight } from 'lucide-react';
import FeatureCard from '@/components/FeatureCard';

const FeatureSection = () => {
  return (
    <section id="features" className="py-20 px-4 bg-muted/30">
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
  );
};

export default FeatureSection;
