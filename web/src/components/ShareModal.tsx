import { useEffect, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Switch } from "@/components/ui/switch";
import { FileData } from "./FileCard";
import { Check, Copy, Mail } from "lucide-react";
import apiClient from "@/lib/axios";
import useAuthStore from "@/store/useAuthStore";

interface ShareModalProps {
  file: FileData | null;
  isOpen: boolean;
  onClose: () => void;
}

const ShareModal = ({ file, isOpen, onClose }: ShareModalProps) => {
  const [shareLink, setShareLink] = useState("");
  const [copied, setCopied] = useState(false);
  const [email, setEmail] = useState("");
  const [expiresAfter, setExpiresAfter] = useState("never");
  const [requirePassword, setRequirePassword] = useState(false);
  const [password, setPassword] = useState("");
  const [allowDownload, setAllowDownload] = useState(true);
  const [isSharing, setIsSharing] = useState(false);
  const { user, token } = useAuthStore();

  const getExpiryDate = (value: string): Date | null => {
    const now = new Date();
    switch (value) {
      case "never":
        return new Date(now.setDate(now.getFullYear() + 1));
      case "1day":
        return new Date(now.setDate(now.getDate() + 1));
      case "7days":
        return new Date(now.setDate(now.getDate() + 7));
      case "30days":
        return new Date(now.setDate(now.getDate() + 30));
      default:
        return new Date(now.setDate(now.getTime() + 15));
    }
  };

  useEffect(() => {
    (async () => {
      if (!file) return;
      const expiryTime = getExpiryDate(expiresAfter).toISOString();
      const permissionType = allowDownload ? "DOWNLOAD" : "VIEW";
      const response = await apiClient.post(
        "/share/generate-link",
        {
          fileId: file.fileId,
          permissionType,
          expiryTime,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.data) {
        setShareLink(response.data);
      }
    })();
  }, [isOpen, expiresAfter, allowDownload]);

  const handleCopyLink = () => {
    navigator.clipboard.writeText(shareLink);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const handleShareByEmail = async () => {
    if (!email) return;

    setIsSharing(true);
    if (!file || !email) return;
    try {
      const permissionType = allowDownload ? "DOWNLOAD" : "VIEW";
      const response = await apiClient.post(
        `/share/email/${email}`,
        {
          fileId: file.fileId,
          permissionType,
          userId: user.userId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert(response.data || "Email sent successfully!");
    } catch (error) {
      console.error("Error sharing file via email:", error);

      if (error.response) {
        alert(
          `Error: ${error.response.data || "Failed to send email"}`
        );
      } else if (error.request) {
        alert("No response from the server. Please try again.");
      } else {
        alert("Something went wrong. Please try again.");
      }
    } finally {
      setIsSharing(false);
      setEmail("");
      onClose();
    }
  };

  if (!file) return null;

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle>Share "{file.fileName}"</DialogTitle>
          <DialogDescription>
            Choose how you want to share this file
          </DialogDescription>
        </DialogHeader>

        <Tabs defaultValue="link" className="w-full">
          <TabsList className="grid w-full grid-cols-2 mb-4">
            <TabsTrigger value="link">Share Link</TabsTrigger>
            <TabsTrigger value="email">Email</TabsTrigger>
          </TabsList>

          <TabsContent value="link" className="space-y-4">
            <div className="flex items-center space-x-2">
              <div className="grid flex-1 gap-2">
                <Label htmlFor="link" className="sr-only">
                  Link
                </Label>
                <Input id="link" value={shareLink} readOnly className="h-9" />
              </div>
              <Button size="sm" className="px-3 gap-1" onClick={handleCopyLink}>
                {copied ? (
                  <Check className="h-4 w-4" />
                ) : (
                  <Copy className="h-4 w-4" />
                )}
                {copied ? "Copied" : "Copy"}
              </Button>
            </div>

            <div className="space-y-4">
              <div className="space-y-2">
                <Label>Expires after</Label>
                <select
                  value={expiresAfter}
                  onChange={(e) => setExpiresAfter(e.target.value)}
                  className="w-full p-2 rounded-md border border-input bg-background"
                >
                  <option value="never">Never</option>
                  <option value="1day">1 day</option>
                  <option value="7days">7 days</option>
                  <option value="30days">30 days</option>
                </select>
              </div>

              <div className="flex items-center justify-between">
                <Label htmlFor="require-password" className="cursor-pointer">
                  Require password
                </Label>
                <Switch
                  id="require-password"
                  checked={requirePassword}
                  onCheckedChange={setRequirePassword}
                />
              </div>

              {requirePassword && (
                <div className="space-y-2">
                  <Label htmlFor="password">Password</Label>
                  <Input
                    id="password"
                    type="password"
                    placeholder="Enter a password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </div>
              )}

              <div className="flex items-center justify-between">
                <Label htmlFor="allow-download" className="cursor-pointer">
                  Allow download
                </Label>
                <Switch
                  id="allow-download"
                  checked={allowDownload}
                  onCheckedChange={setAllowDownload}
                />
              </div>
            </div>
          </TabsContent>

          <TabsContent value="email" className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="email">Email address</Label>
              <Input
                id="email"
                type="email"
                placeholder="Enter recipient's email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>

            <div className="flex items-center justify-between">
              <Label htmlFor="email-allow-download" className="cursor-pointer">
                Allow download
              </Label>
              <Switch
                id="email-allow-download"
                checked={allowDownload}
                onCheckedChange={setAllowDownload}
              />
            </div>

            <Button
              type="button"
              className="w-full gap-2"
              disabled={!email || isSharing}
              onClick={handleShareByEmail}
            >
              {isSharing ? (
                <>
                  <svg
                    className="animate-spin h-4 w-4 mr-1"
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
                  Sending...
                </>
              ) : (
                <>
                  <Mail className="h-4 w-4" />
                  Send Email
                </>
              )}
            </Button>
          </TabsContent>
        </Tabs>

        <DialogFooter className="sm:justify-start">
          <Button variant="secondary" onClick={onClose}>
            Close
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

export default ShareModal;
