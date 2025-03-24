
import { ReactNode } from 'react';
import { cn } from '@/lib/utils';

interface FeatureCardProps {
  icon: ReactNode;
  title: string;
  description: string;
  className?: string;
  iconBackground?: string;
}

const FeatureCard = ({
  icon,
  title,
  description,
  className,
  iconBackground = 'bg-primary/10 dark:bg-primary/20'
}: FeatureCardProps) => {
  return (
    <div className={cn(
      "group relative overflow-hidden rounded-2xl border p-6 transition-all hover-scale",
      "bg-background dark:bg-gray-900/60 backdrop-blur-sm",
      "border-border dark:border-gray-800",
      className
    )}>
      <div className="mb-5">
        <div className={cn(
          "flex h-12 w-12 items-center justify-center rounded-lg",
          iconBackground
        )}>
          {icon}
        </div>
      </div>
      
      <div>
        <h3 className="mb-2 text-xl font-medium">{title}</h3>
        <p className="text-muted-foreground">{description}</p>
      </div>
      
      {/* Subtle gradient accent */}
      <div className="absolute -bottom-2 -right-2 h-24 w-24 rounded-full bg-primary/5 blur-2xl group-hover:bg-primary/10 transition-all duration-500"></div>
    </div>
  );
};

export default FeatureCard;
