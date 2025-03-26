
import { useState, useEffect, useRef } from 'react';
import { ChevronLeft, ChevronRight } from 'lucide-react';
import { Button } from '@/components/ui/button';

interface Slide {
  image: string;
  title: string;
  description: string;
}

const slides: Slide[] = [
  {
    image: '/dashboard.png',
    title: 'Secure Dashboard',
    description: 'Manage all your files in one secure place with intuitive controls'
  },
  {
    image: '/easy-sharing.png',
    title: 'Simple Sharing',
    description: 'Share files with just a few clicks and control who can access them'
  },
  {
    image: '/detailed-analytics.webp',
    title: 'Detailed Analytics',
    description: 'Track who viewed your files and when with comprehensive analytics'
  }
];

const ImageSlider = () => {
  const [current, setCurrent] = useState(0);
  const [isAuto, setIsAuto] = useState(true);
  const autoPlayRef = useRef<NodeJS.Timeout | null>(null);
  const totalSlides = slides.length;

  const nextSlide = () => {
    setCurrent(current => (current === totalSlides - 1 ? 0 : current + 1));
    setIsAuto(false);
  };

  const prevSlide = () => {
    setCurrent(current => (current === 0 ? totalSlides - 1 : current - 1));
    setIsAuto(false);
  };

  const goToSlide = (index: number) => {
    setCurrent(index);
    setIsAuto(false);
  };

  // Auto-play functionality
  useEffect(() => {
    if (isAuto) {
      autoPlayRef.current = setInterval(() => {
        setCurrent(current => (current === totalSlides - 1 ? 0 : current + 1));
      }, 5000);
    }

    return () => {
      if (autoPlayRef.current) {
        clearInterval(autoPlayRef.current);
      }
    };
  }, [isAuto, totalSlides]);

  return (
    <div className="relative w-full max-w-4xl mx-auto overflow-hidden glass-card">
      <div 
        className="flex transition-transform ease-out duration-500"
        style={{ transform: `translateX(-${current * 100}%)` }}
      >
        {slides.map((slide, index) => (
          <div key={index} className="min-w-full">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8 p-8">
              <div className="flex flex-col justify-center space-y-4 order-2 md:order-1">
                <span className="text-sm font-medium text-primary">SCREENSHOT {index + 1}/{totalSlides}</span>
                <h3 className="text-2xl font-bold">{slide.title}</h3>
                <p className="text-muted-foreground">{slide.description}</p>
              </div>
              <div className="flex items-center justify-center order-1 md:order-2">
                <img 
                  src={slide.image} 
                  alt={slide.title} 
                  className="rounded-lg shadow-lg object-cover max-h-[300px] w-full"
                />
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Navigation Arrows */}
      <Button 
        variant="ghost" 
        size="icon"
        className="absolute left-2 top-1/2 transform -translate-y-1/2 bg-background/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-full shadow-md hover:bg-background dark:hover:bg-gray-800 z-10"
        onClick={prevSlide}
        aria-label="Previous slide"
      >
        <ChevronLeft className="h-5 w-5" />
      </Button>
      
      <Button 
        variant="ghost" 
        size="icon"
        className="absolute right-2 top-1/2 transform -translate-y-1/2 bg-background/80 dark:bg-gray-800/80 backdrop-blur-sm rounded-full shadow-md hover:bg-background dark:hover:bg-gray-800 z-10"
        onClick={nextSlide}
        aria-label="Next slide"
      >
        <ChevronRight className="h-5 w-5" />
      </Button>

      {/* Dots navigation */}
      <div className="absolute bottom-4 left-0 right-0">
        <div className="flex justify-center space-x-2">
          {slides.map((_, index) => (
            <button
              key={index}
              onClick={() => goToSlide(index)}
              className={`w-2 h-2 rounded-full transition-all ${
                index === current 
                  ? 'bg-primary w-6' 
                  : 'bg-gray-300 dark:bg-gray-600'
              }`}
              aria-label={`Go to slide ${index + 1}`}
            />
          ))}
        </div>
      </div>
    </div>
  );
};

export default ImageSlider;
