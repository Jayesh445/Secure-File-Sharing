import { useState } from "react";
import { Upload, CheckCircle, Loader2 } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import apiClient from "@/lib/axios";
import FileUpload from "./FileUpload";
import useAuthStore from "@/store/useAuthStore";

const UploadModal = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [files, setFiles] = useState<File[]>([]);
  const [loading, setLoading] = useState(false);
  const [uploadSuccess, setUploadSuccess] = useState(false);
  const {user} = useAuthStore();

  const handleUpload = async () => {
    if (files.length === 0) return;

    const formData = new FormData();
    setLoading(true);
    setUploadSuccess(false);

    try {
      if (files.length === 1) {
        files.forEach((file) => formData.append("file", file));
        await apiClient.post(`/files/upload?userId=${user.userId}`, formData,{
            headers:{
                "Content-Type":"multipart/form-data",
                "Authorization":`Bearer ${localStorage.getItem("token")}`
            }
        });
      } else {
        files.forEach((file) => formData.append("files", file));
        await apiClient.post(`/files/upload-multiple?userId=${user.userId}`, formData,{
            headers:{
                "Content-Type":"multipart/form-data",
                "Authorization":`Bearer ${localStorage.getItem("token")}`
            }
        });
      }
      
      setUploadSuccess(true);
      setTimeout(() => {
        setIsOpen(false);
        setUploadSuccess(false);
        setFiles([]);
      }, 2000); // Close modal after success delay
    } catch (error) {
      console.error("Upload failed:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Button onClick={() => setIsOpen(true)}>
        <Upload className="h-4 w-4 mr-2" />
        Upload
      </Button>

      <Dialog open={isOpen} onOpenChange={setIsOpen}>
        <DialogContent className="bg-white dark:bg-gray-900 p-6 rounded-lg shadow-lg">
          <DialogHeader>
            <DialogTitle>Upload Files</DialogTitle>
          </DialogHeader>

          <FileUpload onFileChange={(selectedFiles) => setFiles(Array.from(selectedFiles || []))} />

          {loading && (
            <div className="flex items-center justify-center mt-4 text-primary">
              <Loader2 className="h-5 w-5 animate-spin mr-2" />
              Uploading...
            </div>
          )}

          {uploadSuccess && (
            <div className="flex items-center justify-center mt-4 text-green-500">
              <CheckCircle className="h-5 w-5 mr-2" />
              Upload Successful!
            </div>
          )}

          <div className="flex justify-end mt-4 space-x-2">
            <Button variant="outline" onClick={() => setIsOpen(false)} disabled={loading}>
              Cancel
            </Button>
            <Button onClick={handleUpload} disabled={files.length === 0 || loading}>
              {loading ? (
                <>
                  <Loader2 className="h-4 w-4 animate-spin mr-2" />
                  Uploading...
                </>
              ) : (
                "Upload"
              )}
            </Button>
          </div>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default UploadModal;
