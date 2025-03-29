import { useState } from "react";
import {
  File,
  MoreHorizontal,
  Download,
  Share,
  Trash,
  Eye,
  EyeOff,
  Clock,
} from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { cn } from "@/lib/utils";
import { FileData } from "@/store/useFileStore";

interface FileCardProps {
  file: FileData;
  onShare: (file: FileData) => void;
  onDelete: (fileId: string) => void;
  compact?: boolean;
}

const FileCard = ({
  file,
  onShare,
  onDelete,
  compact = false,
}: FileCardProps) => {
  const [isHovering, setIsHovering] = useState(false);

  const CLOUD_URL = import.meta.env.VITE_CLOUD_URL;

  const fileTypeIcon = () => {
    switch (file.fileType.split("/")[0]) {
      case "image":
        return (
          <img
            src={CLOUD_URL + "/" + file.filePath || "/placeholder.svg"}
            alt={file.fileName}
            className="w-full h-full object-cover rounded-t-lg"
          />
        );
      default:
        return <File className="w-12 h-12 text-primary opacity-60" />;
    }
  };

  return (
    <Card
      className={cn(
        "overflow-hidden transition-all duration-200 hover:shadow-md",
        "border border-border bg-card hover:border-primary/20",
        compact ? "w-full" : "w-full"
      )}
      onMouseEnter={() => setIsHovering(true)}
      onMouseLeave={() => setIsHovering(false)}
    >
      {!compact && (
        <div className="relative h-36 flex items-center justify-center bg-muted/40">
          {fileTypeIcon()}

          {isHovering && (
            <div className="absolute inset-0 bg-black/40 flex items-center justify-center gap-2 animate-fade-in">
              <TooltipProvider>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button variant="secondary" size="icon" className="h-8 w-8">
                      <Eye className="h-4 w-4" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>Preview</p>
                  </TooltipContent>
                </Tooltip>
              </TooltipProvider>

              <TooltipProvider>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button
                      variant="secondary"
                      size="icon"
                      className="h-8 w-8"
                      onClick={() => onShare(file)}
                    >
                      <Share className="h-4 w-4" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>Share</p>
                  </TooltipContent>
                </Tooltip>
              </TooltipProvider>

              <TooltipProvider>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button variant="secondary" size="icon" className="h-8 w-8">
                      <Download className="h-4 w-4" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>Download</p>
                  </TooltipContent>
                </Tooltip>
              </TooltipProvider>
            </div>
          )}
        </div>
      )}

      <div
        className={cn(
          "flex items-center justify-between p-3",
          compact ? "border-l-2 border-primary/70" : ""
        )}
      >
        <div className="flex items-center space-x-3 overflow-hidden">
          {compact && (
            <div className="flex-shrink-0">
              <File className="h-5 w-5 text-primary opacity-80" />
            </div>
          )}

          <div className="overflow-hidden">
            <p className="font-medium truncate" title={file.fileName}>
              {file.fileName}
            </p>
            <div className="flex items-center text-xs text-muted-foreground space-x-2">
              <span>{file.fileSize}</span>
              <span>•</span>
              <span className="flex items-center">
                <Clock className="h-3 w-3 mr-1" />
                {file.createdAt.toLocaleString()}
              </span>
            </div>
          </div>
        </div>

        <div className="flex items-center space-x-1">
          {file.shared && (
            <span className="text-xs py-0.5 px-1.5 rounded-full bg-primary/10 text-primary mr-1">
              Shared
            </span>
          )}

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                size="icon"
                className="h-8 w-8 rounded-full"
              >
                <MoreHorizontal className="h-4 w-4" />
                <span className="sr-only">Open menu</span>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end" className="w-48">
              <DropdownMenuItem
                onClick={() =>
                  window.alert("Download feature would be implemented here")
                }
              >
                <Download className="h-4 w-4 mr-2" />
                Download
              </DropdownMenuItem>
              <DropdownMenuItem onClick={() => onShare(file)}>
                <Share className="h-4 w-4 mr-2" />
                Share
              </DropdownMenuItem>
              <DropdownMenuItem>
                {file.shared ? (
                  <>
                    <EyeOff className="h-4 w-4 mr-2" />
                    Make private
                  </>
                ) : (
                  <>
                    <Eye className="h-4 w-4 mr-2" />
                    Make public
                  </>
                )}
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                className="text-destructive focus:text-destructive"
                onClick={() => onDelete(file.fileId)}
              >
                <Trash className="h-4 w-4 mr-2" />
                Delete
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
    </Card>
  );
};

export default FileCard;
