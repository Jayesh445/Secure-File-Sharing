import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useFileStore, { FileData } from "@/store/useFileStore";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import { Download, Trash } from "lucide-react";
import apiClient from "@/lib/axios";
import useAuthStore from "@/store/useAuthStore";

const FilePreview = () => {
  const { files, deleteFile, isLoading } = useFileStore.getState();
  const { token } = useAuthStore();
  const [fileUrl, setFileUrl] = useState<string>("");
  const { fileId } = useParams<{ fileId: string }>();
  const [file, setFile] = useState<FileData | null>(null);

  useEffect(() => {
    (async () => {
      try {
        if (!token) return;
        const selectedFile = files.find((f) => f.fileId === fileId);
        setFile(selectedFile);
        // console.log(selectedFile);
        const response = await apiClient.get(
          `/files/preview?fileId=${selectedFile.fileId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (response.data) {
          setFileUrl(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    })();
  }, [files, fileId]);

  if (isLoading || !file)
    return <Skeleton className="w-full h-[300px] rounded-lg" />;

  //   const isImage = file.fileType.startsWith("image/");
  //   const isPDF = file.fileType === "application/pdf";

  return (
    <div className="flex flex-col items-center justify-center min-h-screen px-6 py-12 bg-background text-primary">
      <Card className="w-full max-w-2xl p-4 shadow-lg rounded-2xl bg-card">
        <h2 className="text-2xl font-bold text-primary">{file.fileName}</h2>

        {/* File Preview */}
        <div className="mt-4">
          <img
            src={fileUrl}
            alt={file.fileName}
            className="rounded-lg w-full"
          />
        </div>

        {/* File Details */}
        <div className="mt-4 text-sm text-muted-foreground">
          <p>
            <strong>Type:</strong> {file.fileType}
          </p>
          <p>
            <strong>Size:</strong> {file.fileSize}
          </p>
          <p>
            <strong>Uploaded:</strong>{" "}
            {new Date(file.createdAt).toLocaleString()}
          </p>
        </div>

        {/* Actions */}
        <div className="mt-4 flex gap-4">
          <a
            href={file.filePath}
            target="_blank"
            rel="noopener noreferrer"
            download
          >
            <Button className="gap-2">
              <Download className="w-5 h-5" /> Download
            </Button>
          </a>
          <Button
            className="gap-2 bg-red-500"
            onClick={() => deleteFile(file.fileId)}
          >
            <Trash className="w-5 h-5" /> Delete
          </Button>
        </div>
      </Card>
    </div>
  );
};

export default FilePreview;
