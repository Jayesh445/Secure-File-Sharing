
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';

const CTASection = () => {
  return (
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
          <Button asChild size="lg" variant="outline" className="border-white/20 dark:hover:bg-white/50 dark:hover:border-white/70 dark:hover:text-gray-800 text-blue-600">
            <Link to="/features">
              Learn More
            </Link>
          </Button>
        </div>
      </div>
    </section>
  );
};

export default CTASection;
