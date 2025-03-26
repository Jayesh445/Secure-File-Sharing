import { useState } from "react";
import { UploadCloud } from "lucide-react";

const FileUpload = ({ onFileChange }: { onFileChange: (files: FileList | null) => void }) => {
  const [files, setFiles] = useState<File[]>([]);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      const selectedFiles = Array.from(event.target.files);
      setFiles(selectedFiles);
      onFileChange(event.target.files);
    }
  };

  const handleDragOver = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
  };

  const handleDrop = (event: React.DragEvent<HTMLLabelElement>) => {
    event.preventDefault();
    if (event.dataTransfer.files) {
      const droppedFiles = Array.from(event.dataTransfer.files);
      setFiles(droppedFiles);
      onFileChange(event.dataTransfer.files);
    }
  };

  return (
    <div className="flex flex-col items-center gap-4">
      <label
        className="w-full max-w-lg flex flex-col items-center justify-center border-2 border-dashed border-gray-300 rounded-lg p-6 cursor-pointer hover:border-primary transition-all bg-gray-50 dark:bg-gray-800 dark:border-gray-600"
        onDragOver={handleDragOver}
        onDrop={handleDrop}
      >
        <UploadCloud className="h-10 w-10 text-gray-500 dark:text-gray-400 mb-2" />
        <span className="text-gray-700 dark:text-gray-300 font-medium">Drag & Drop files here</span>
        <span className="text-gray-500 text-sm mt-1">or</span>
        <span className="bg-primary text-white px-4 py-2 rounded-md mt-2 text-sm font-semibold shadow-md hover:bg-primary/90">
          Browse Files
        </span>
        <input type="file" multiple onChange={handleFileChange} className="hidden" />
      </label>

      {/* File List Preview */}
      {files.length > 0 && (
        <ul className="w-full max-w-lg bg-gray-100 dark:bg-gray-700 p-3 rounded-lg">
          {files.map((file, index) => (
            <li key={index} className="text-gray-700 dark:text-gray-300 text-sm py-1">
              {file.name}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default FileUpload;
