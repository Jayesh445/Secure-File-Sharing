
import { GitPullRequest, Lock, Users } from 'lucide-react';

const HowItWorks = () => {
  return (
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
  );
};

export default HowItWorks;
