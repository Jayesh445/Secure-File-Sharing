
import { useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { ArrowRight, ShieldCheck, Zap, Lock } from 'lucide-react';

const Hero3D = () => {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!canvasRef.current) return;

    // Initialize Three.js scene
    if (typeof window.THREE === 'undefined') {
      console.error('THREE is not defined - add the script tag to index.html');
      return;
    }
    
    const THREE = window.THREE;
    
    // Create a scene with basic elements when THREE is available
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(
      75,
      window.innerWidth / window.innerHeight,
      0.1,
      1000
    );
    
    const renderer = new THREE.WebGLRenderer({
      canvas: canvasRef.current,
      alpha: true,
      antialias: true
    });
    
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.setPixelRatio(Math.min(window.devicePixelRatio, 2));
    
    // Add ambient light
    const ambientLight = new THREE.AmbientLight(0x404040, 2);
    scene.add(ambientLight);
    
    // Add directional light
    const directionalLight = new THREE.DirectionalLight(0xffffff, 2);
    directionalLight.position.set(1, 1, 1);
    scene.add(directionalLight);
    
    // Create floating file objects
    const createFileObject = (x: number, y: number, z: number, color: number) => {
      const geometry = new THREE.BoxGeometry(1, 1.4, 0.1);
      const material = new THREE.MeshStandardMaterial({
        color: color,
        metalness: 0.1,
        roughness: 0.5,
      });
      
      const fileMesh = new THREE.Mesh(geometry, material);
      fileMesh.position.set(x, y, z);
      fileMesh.userData = {
        rotationSpeed: Math.random() * 0.005,
        floatSpeed: 0.001 + Math.random() * 0.002,
        floatDistance: Math.random() * 0.1,
        initialY: y,
      };
      
      return fileMesh;
    };
    
    // Add multiple file objects
    const files = [];
    const colors = [0x4285f4, 0x34a853, 0xfbbc05, 0xea4335, 0x5f6caf, 0x00acc1];
    
    for (let i = 0; i < 20; i++) {
      const x = (Math.random() - 0.5) * 15;
      const y = (Math.random() - 0.5) * 8;
      const z = (Math.random() - 0.5) * 10 - 5; // Position files behind camera
      const color = colors[Math.floor(Math.random() * colors.length)];
      
      const file = createFileObject(x, y, z, color);
      file.rotation.x = Math.random() * Math.PI;
      file.rotation.y = Math.random() * Math.PI;
      
      scene.add(file);
      files.push(file);
    }
    
    // Position camera
    camera.position.z = 5;
    
    // Handle window resize
    const handleResize = () => {
      if (!containerRef.current) return;
      
      const width = containerRef.current.clientWidth;
      const height = containerRef.current.clientHeight;
      
      camera.aspect = width / height;
      camera.updateProjectionMatrix();
      renderer.setSize(width, height);
    };
    
    window.addEventListener('resize', handleResize);
    handleResize();
    
    // Animation loop
    const animate = () => {
      requestAnimationFrame(animate);
      
      // Animate each file
      files.forEach((file) => {
        file.rotation.x += file.userData.rotationSpeed * 0.5;
        file.rotation.y += file.userData.rotationSpeed;
        
        // Floating animation
        const floatOffset = Math.sin(Date.now() * file.userData.floatSpeed) * file.userData.floatDistance;
        file.position.y = file.userData.initialY + floatOffset;
      });
      
      renderer.render(scene, camera);
    };
    
    animate();
    
    // Cleanup
    return () => {
      window.removeEventListener('resize', handleResize);
      renderer.dispose();
      
      files.forEach((file) => {
        scene.remove(file);
        file.geometry.dispose();
        (file.material as THREE.Material).dispose();
      });
    };
  }, []);

  return (
    <div ref={containerRef} className="relative h-screen w-full overflow-hidden bg-gradient-to-b from-primary/90 to-blue-800 dark:from-gray-900 dark:to-gray-800">
      {/* Canvas for 3D animation */}
      <canvas ref={canvasRef} className="absolute inset-0 w-full h-full z-0" />
      
      {/* Hero content */}
      <div className="absolute inset-0 z-10">
        <div className="container mx-auto px-4 h-full flex flex-col justify-center items-center text-center">
          <div className="max-w-3xl mx-auto pt-20">
            <div className="inline-block animate-fade-in">
              <span className="inline-flex items-center rounded-full px-3 py-1 text-sm font-medium bg-white/20 text-white backdrop-blur-sm mb-4">
                <ShieldCheck className="w-4 h-4 mr-1" /> Secure File Sharing Made Simple
              </span>
            </div>
            
            <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold text-white mb-6 tracking-tight leading-tight animate-fade-in" style={{ animationDelay: '0.1s' }}>
              Share Files with 
              <span className="text-gradient bg-gradient-to-r from-blue-200 to-white"> Complete Confidence</span>
            </h1>
            
            <p className="text-lg md:text-xl text-white/80 mb-8 max-w-2xl mx-auto animate-fade-in" style={{ animationDelay: '0.2s' }}>
              SecureShare provides end-to-end encrypted file sharing with intuitive 
              controls for permissions, expiration, and tracking.
            </p>
            
            <div className="flex flex-col sm:flex-row gap-4 justify-center animate-fade-in" style={{ animationDelay: '0.3s' }}>
              <Button asChild size="lg" className="font-medium text-base transition-all duration-300 hover:scale-105">
                <Link to="/signup">
                  Get Started <ArrowRight className="ml-2 h-4 w-4 transition-transform duration-300 group-hover:translate-x-1" />
                </Link>
              </Button>
              <Button asChild variant="outline" size="lg" className="font-medium text-base bg-white/10 text-white border-white/20 hover:bg-white/20 transition-all duration-300 hover:scale-105">
                <Link to="/#features">
                  Learn More
                </Link>
              </Button>
            </div>
          </div>
          
          {/* Key feature highlights */}
          <div className="mt-16 w-full max-w-4xl animate-fade-in" style={{ animationDelay: '0.4s' }}>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-white/10 backdrop-blur-md rounded-xl p-5 border border-white/20 transition-all duration-300 hover:bg-white/20 hover:scale-105 hover:shadow-lg">
                <div className="flex items-center mb-3">
                  <div className="p-2 rounded-lg bg-primary/20">
                    <Lock className="h-5 w-5 text-white" />
                  </div>
                  <h3 className="ml-3 text-lg font-medium text-white">End-to-End Encryption</h3>
                </div>
                <p className="text-white/70 text-sm">Files are encrypted before they leave your device</p>
              </div>
              
              <div className="bg-white/10 backdrop-blur-md rounded-xl p-5 border border-white/20 transition-all duration-300 hover:bg-white/20 hover:scale-105 hover:shadow-lg animate-fade-in" style={{ animationDelay: '0.5s' }}>
                <div className="flex items-center mb-3">
                  <div className="p-2 rounded-lg bg-primary/20">
                    <Zap className="h-5 w-5 text-white" />
                  </div>
                  <h3 className="ml-3 text-lg font-medium text-white">Lightning Fast</h3>
                </div>
                <p className="text-white/70 text-sm">Transfer large files quickly and efficiently</p>
              </div>
              
              <div className="bg-white/10 backdrop-blur-md rounded-xl p-5 border border-white/20 transition-all duration-300 hover:bg-white/20 hover:scale-105 hover:shadow-lg animate-fade-in" style={{ animationDelay: '0.6s' }}>
                <div className="flex items-center mb-3">
                  <div className="p-2 rounded-lg bg-primary/20">
                    <ShieldCheck className="h-5 w-5 text-white" />
                  </div>
                  <h3 className="ml-3 text-lg font-medium text-white">Privacy First</h3>
                </div>
                <p className="text-white/70 text-sm">Full control over access and permissions</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      {/* Gradient overlay to fade the 3D scene */}
      <div className="absolute bottom-0 left-0 right-0 h-48 bg-gradient-to-t from-background to-transparent z-10"></div>
    </div>
  );
};

export default Hero3D;
