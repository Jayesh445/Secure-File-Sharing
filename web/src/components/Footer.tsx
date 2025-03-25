
import { Link } from 'react-router-dom';
import { GitPullRequest } from 'lucide-react';

const Footer = () => {
  return (
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
              <li><Link to="/about" className="text-muted-foreground text-sm hover:text-primary">About</Link></li>
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
  );
};

export default Footer;
