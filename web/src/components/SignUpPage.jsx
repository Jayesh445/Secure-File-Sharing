import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { BottomGradient } from "./signup-form-demo";

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
    <div className="bg-red-400 h-screen w-screen flex items-center justify-center">
      <div className="mx-auto p-6 bg-white shadow-md rounded-lg">
        <h2 className="text-2xl font-bold mb-4 text-center text-black">Sign Up</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Name Field */}
          <div>
            <Label htmlFor="name" className="block font-medium">
              Name
            </Label>
            <Input
              type="text"
              id="name"
              name="name"
              placeholder="Enter your name"
              className="w-full p-2 border rounded-md"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
  
          {/* Email Field */}
          <div>
            <Label htmlFor="email" className="block font-medium">
              Email
            </Label>
            <Input
              type="email"
              id="email"
              name="email"
              placeholder="Enter your email"
              className="w-full p-2 border rounded-md"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
  
          {/* Password Field */}
          <div>
            <Label htmlFor="password" className="block font-medium">
              Password
            </Label>
            <Input
              type="password"
              id="password"
              name="password"
              placeholder="Enter your password"
              className="w-full p-2 border rounded-md"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
  
          {/* Submit Button */}
          <button
          className="group/btn relative block h-10 w-full rounded-md bg-gradient-to-br from-black to-neutral-600 font-medium text-white shadow-[0px_1px_0px_0px_#ffffff40_inset,0px_-1px_0px_0px_#ffffff40_inset] dark:bg-zinc-800 dark:from-zinc-900 dark:to-zinc-900 dark:shadow-[0px_1px_0px_0px_#27272a_inset,0px_-1px_0px_0px_#27272a_inset]"
          type="submit">
          Sign up &rarr;
          <BottomGradient />
        </button>
        </form>
      </div>
    </div>
  );
};

export default SignupPage;
