
import React from 'react';
import { Link } from 'react-router-dom';
import { FileSymlink } from 'lucide-react';

interface LogoProps {
  variant?: 'default' | 'light';
}

const Logo: React.FC<LogoProps> = ({ variant = 'default' }) => {
  return (
    <Link 
      to="/" 
      className={`flex items-center gap-2 transition-all hover:opacity-90 ${variant === 'light' ? 'text-white' : 'text-primary'}`}
    >
      <FileSymlink className="w-8 h-8" />
      <span className="font-bold text-xl tracking-tight">SecureShare</span>
    </Link>
  );
};

export default Logo;
