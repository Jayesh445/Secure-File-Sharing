
const SkeletonLoader = () => {
  return (
    <div className="min-h-screen p-6 transition-colors bg-black/80 text-gray-900 dark:bg-gray-900 dark:text-white">
      {/* Tabs */}
      <div className="flex gap-4 mb-6">
        <div className="px-4 py-2 rounded-lg bg-gray-300 dark:bg-gray-700">
          All Files
        </div>
        <div className="px-4 py-2 rounded-lg bg-gray-400 dark:bg-gray-800">
          Shared
        </div>
        <div className="px-4 py-2 rounded-lg bg-gray-400 dark:bg-gray-800">
          Recent
        </div>
      </div>

      {/* Quick Access */}
      <div className="grid grid-cols-4 gap-4 mb-6">
        {Array(4)
          .fill(0)
          .map((_, index) => (
            <div
              key={index}
              className="h-20 rounded-lg animate-pulse bg-gray-300 dark:bg-gray-800"
            ></div>
          ))}
      </div>

      {/* File Grid */}
      <div className="grid grid-cols-4 gap-4">
        {Array(8)
          .fill(0)
          .map((_, index) => (
            <div
              key={index}
              className="h-40 rounded-lg p-3 animate-pulse bg-gray-300 dark:bg-gray-800"
            >
              <div className="h-24 rounded-lg mb-3 bg-gray-400 dark:bg-gray-700"></div>
              <div className="h-4 w-3/4 rounded mb-1 bg-gray-500 dark:bg-gray-600"></div>
              <div className="h-4 w-1/2 rounded bg-gray-500 dark:bg-gray-600"></div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default SkeletonLoader;
