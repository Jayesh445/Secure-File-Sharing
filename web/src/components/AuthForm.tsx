import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Eye, EyeOff, Info } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import Logo from "./Logo";
import apiClient from "@/lib/axios";
import useAuthStore from "@/store/useAuthStore";
import { FcGoogle } from "react-icons/fc";
import { FaGithub } from "react-icons/fa";

interface AuthFormProps {
  mode: "login" | "signup";
  backgroundImage?: string;
}

const AuthForm = ({ mode, backgroundImage }: AuthFormProps) => {
  const navigate = useNavigate();
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const {login} = useAuthStore();

  const API_URL = "/auth"; // Base URL is already set in `apiClient`

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError(null);

    console.log(import.meta.env.VITE_BACKEND_URL);

    const endpoint = mode === "login" ? "/login" : "/register";
    const requestBody =
      mode === "login" ? { email, password } : { name, email, password };

    try {
      const { data } = await apiClient.post(
        `${API_URL}${endpoint}`,
        requestBody
      );

      if (data.userDto) {
        login(data.userDto, data.token);
      }
      navigate("/dashboard");
    } catch (err: any) {
      console.log(err);
      setError(err.response?.data?.message || "Something went wrong");
    } finally {
      setIsLoading(false);
    }
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <>
      <div className="flex min-h-screen w-full max-h-screen">
        {/* Left side: Form */}
        <div
          className={`w-full md:w-1/2 flex flex-col justify-center p-4 sm:p-8 md:p-12 lg:p-16 xl:p-24 ${
            mode === "signup" ? "order-2 md:order-1" : "order-2 md:order-2"
          }`}
        >
          <div className="mx-auto w-full max-w-sm">
            <div className="mb-8 flex flex-col items-center md:items-start">
              <Logo />
              <h1 className="mt-5 text-3xl font-bold">
                {mode === "login" ? "Welcome back" : "Create an account"}
              </h1>
              <p className="mt-2 text-sm text-muted-foreground">
                {mode === "login"
                  ? "Enter your credentials to access your account"
                  : "Enter your information to create your account"}
              </p>
            </div>

            <Tabs defaultValue="email" className="w-full">
              <TabsList className="grid w-full grid-cols-2 mb-6">
                <TabsTrigger value="email">Email</TabsTrigger>
                <TabsTrigger value="google">Google</TabsTrigger>
              </TabsList>
              <TabsContent value="email">
                <form onSubmit={handleSubmit} className="space-y-5">
                  {mode === "signup" && (
                    <div className="space-y-2">
                      <Label htmlFor="name">Full Name</Label>
                      <Input
                        id="name"
                        placeholder="Enter your name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                        className="h-11"
                      />
                    </div>
                  )}

                  <div className="space-y-2">
                    <Label htmlFor="email">Email</Label>
                    <Input
                      id="email"
                      type="email"
                      placeholder="name@example.com"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      required
                      className="h-11"
                    />
                  </div>

                  <div className="space-y-2">
                    <div className="flex items-center justify-between">
                      <Label htmlFor="password">Password</Label>
                      {mode === "login" && (
                        <Link
                          to="/forgot-password"
                          className="text-sm text-primary hover:text-primary/90 underline-offset-4 hover:underline"
                        >
                          Forgot password?
                        </Link>
                      )}
                    </div>

                    <div className="relative">
                      <Input
                        id="password"
                        type={showPassword ? "text" : "password"}
                        placeholder="••••••••"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        className="h-11 pr-10"
                      />
                      <button
                        type="button"
                        onClick={togglePasswordVisibility}
                        className="absolute inset-y-0 right-0 flex items-center px-3 text-gray-400 hover:text-gray-600"
                      >
                        {showPassword ? (
                          <EyeOff className="h-4 w-4" />
                        ) : (
                          <Eye className="h-4 w-4" />
                        )}
                      </button>
                    </div>
                  </div>

                  {mode === "signup" && (
                    <div className="flex items-start space-x-2 text-sm">
                      <Info className="h-4 w-4 text-muted-foreground mt-0.5" />
                      <p className="text-muted-foreground">
                        By creating an account, you agree to our Terms of
                        Service and Privacy Policy.
                      </p>
                    </div>
                  )}

                  <Button
                    type="submit"
                    className="w-full h-11"
                    disabled={isLoading}
                  >
                    {isLoading ? (
                      <>
                        <svg
                          className="animate-spin -ml-1 mr-2 h-4 w-4 text-white"
                          xmlns="http://www.w3.org/2000/svg"
                          fill="none"
                          viewBox="0 0 24 24"
                        >
                          <circle
                            className="opacity-25"
                            cx="12"
                            cy="12"
                            r="10"
                            stroke="currentColor"
                            strokeWidth="4"
                          ></circle>
                          <path
                            className="opacity-75"
                            fill="currentColor"
                            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                          ></path>
                        </svg>
                        {mode === "login"
                          ? "Signing in..."
                          : "Creating account..."}
                      </>
                    ) : (
                      <>{mode === "login" ? "Sign in" : "Create account"}</>
                    )}
                  </Button>
                </form>
              </TabsContent>
              <TabsContent value="google">
                <div className="space-y-5">
                  <Button
                    variant="outline"
                    className="w-full h-11"
                    onClick={() =>
                      window.location.href="https://adequate-maris-ltce-575357fb.koyeb.app/oauth2/authorization/google"
                    }
                  >
                    <FcGoogle />
                    Continue with Google
                  </Button>

                  <Button
                    variant="outline"
                    className="w-full h-11"
                    onClick={() => 
                      window.location.href="https://adequate-maris-ltce-575357fb.koyeb.app/oauth2/authorization/github"
                    }
                  >
                    <FaGithub />
                    Continue with Github
                  </Button>
                </div>
              </TabsContent>
            </Tabs>

            <div className="mt-8 text-center text-sm">
              {mode === "login" ? (
                <p>
                  Don&apos;t have an account?{" "}
                  <Link
                    to="/signup"
                    className="text-primary hover:text-primary/90 underline-offset-4 hover:underline font-medium"
                  >
                    Sign up
                  </Link>
                </p>
              ) : (
                <p>
                  Already have an account?{" "}
                  <Link
                    to="/login"
                    className="text-primary hover:text-primary/90 underline-offset-4 hover:underline font-medium"
                  >
                    Sign in
                  </Link>
                </p>
              )}
            </div>
          </div>
        </div>

        {/* Right side: Image */}
        <div
          className={`hidden md:block md:w-1/2 bg-cover bg-center ${
            mode === "signup" ? "order-1 md:order-2" : "order-1 md:order-1"
          }`}
          style={{
            backgroundImage: `url(${backgroundImage || "/placeholder.svg"})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        >
          <div className="h-full w-full bg-gradient-to-br from-primary/30 to-primary/5 backdrop-blur-sm flex items-center justify-center p-12">
            <div className="max-w-md text-white">
              <div className="rounded-xl bg-white/10 backdrop-blur-md p-6 border border-white/20">
                <h3 className="text-xl font-bold mb-2">Secure File Sharing</h3>
                <p className="opacity-90">
                  Share files with confidence using end-to-end encryption and
                  granular access controls.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default AuthForm;
