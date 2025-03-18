import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { BottomGradient } from "./signup-form-demo";
import ThemeToggleButton from "./ThemeToggleButton"; // Import theme toggle button


const SignupPage = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });

  // Handle input change
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Signup Data:", formData);
    alert("Form Submitted! Check console for data.");
  };

  return (
    <div className="h-screen w-screen flex items-center justify-center bg-white dark:bg-gray-900 relative">
      {/* toggle button */}
      <div className="absolute top-4 right-4">
        <ThemeToggleButton />
      </div>

      
      <div className="w-[400px] p-8 bg-white dark:bg-gray-800 shadow-lg rounded-xl">
        
        <h2 className="text-3xl font-bold mb-6 text-center text-black dark:text-white">
          Sign Up
        </h2>
        <form onSubmit={handleSubmit} className="space-y-5">
          

          <div>
            <Label htmlFor="name" className="block text-lg font-medium dark:text-white">
              Name
            </Label>

            <Input
              type="text"
              id="name"
              name="name"
              placeholder="Enter your name"
              className="w-full p-3 border rounded-lg bg-white dark:bg-gray-700 dark:text-white dark:border-gray-600"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <Label htmlFor="email" className="block text-lg font-medium dark:text-white">
              Email
            </Label>
            <Input
              type="email"
              id="email"
              name="email"
              placeholder="Enter your email"
              className="w-full p-3 border rounded-lg bg-white dark:bg-gray-700 dark:text-white dark:border-gray-600"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div>
            <Label htmlFor="password" className="block text-lg font-medium dark:text-white">
              Password
            </Label>
            <Input
              type="password"
              id="password"
              name="password"
              placeholder="Enter your password"
              className="w-full p-3 border rounded-lg bg-white dark:bg-gray-700 dark:text-white dark:border-gray-600"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>

          <button
            className="relative block h-12 w-full rounded-lg bg-gradient-to-br from-black to-neutral-600 font-medium text-white text-lg shadow-lg dark:bg-gray-700 dark:text-white dark:from-gray-800 dark:to-gray-900"
            type="submit"
          >
            Sign up &rarr;
            <BottomGradient />
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignupPage;
