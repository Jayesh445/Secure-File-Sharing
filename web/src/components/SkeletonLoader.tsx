import { useState, useEffect } from "react";

const SkeletonLoader = () => {
  
  return (
    <div className="bg-gray-900 min-h-screen p-6 text-white">
      {/* Tabs */}
      <div className="flex gap-4 mb-6">
        <div className="bg-gray-700 px-4 py-2 rounded-lg">All Files</div>
        <div className="bg-gray-800 px-4 py-2 rounded-lg">Shared</div>
        <div className="bg-gray-800 px-4 py-2 rounded-lg">Recent</div>
      </div>

      {/* Quick Access */}
      <div className="grid grid-cols-4 gap-4 mb-6">
        {Array(4)
          .fill(0)
          .map((_, index) => (
            <div key={index} className="bg-gray-800 h-20 rounded-lg animate-pulse"></div>
          ))}
      </div>

      {/* File Grid */}
      <div className="grid grid-cols-4 gap-4">
        {Array(8)
          .fill(0)
          .map((_, index) => (
            <div key={index} className="bg-gray-800 h-40 rounded-lg p-3 animate-pulse">
              <div className="bg-gray-700 h-24 rounded-lg mb-3"></div>
              <div className="bg-gray-600 h-4 w-3/4 rounded mb-1"></div>
              <div className="bg-gray-600 h-4 w-1/2 rounded"></div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default SkeletonLoader;
