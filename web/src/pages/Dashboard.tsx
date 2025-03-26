import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import FileCard, { FileData } from "@/components/FileCard";
import ShareModal from "@/components/ShareModal";
import ThemeToggle from "@/components/ThemeToggle";
import {
  FileSymlink,
  Search,
  Grid3X3,
  List,
  Plus,
  ChevronRight,
  Eye,
  DownloadCloud,
  Clock,
  File,
} from "lucide-react";
import UserDropdown from "@/components/UserDropdown";
import apiClient from "@/lib/axios";
import useAuthStore from "@/store/useAuthStore";
import SkeletonLoader from "@/components/SkeletonLoader";
import UploadModal from "@/components/UploadModal";


const Dashboard = () => {
  const [files, setFiles] = useState<FileData[]>([]);
  const [view, setView] = useState<"grid" | "list">("grid");
  const [showShareModal, setShowShareModal] = useState(false);
  const [selectedFile, setSelectedFile] = useState<FileData | null>(null);
  const [searchQuery, setSearchQuery] = useState("");
  const { user } = useAuthStore();
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    if (!user) return;
    setLoading(true);
    (async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) throw new Error("No authentication token found");

        const userFiles = await apiClient.get(`/files/user/${user.userId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        const sharedFilesResponse = await apiClient.get(
          `/files/shared/${user.userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const sharedFiles = sharedFilesResponse.data.content.map(
          (file: File) => ({
            ...file,
            shared: true,
          })
        );

        setFiles([...userFiles.data.content, ...sharedFiles]);
        console.log(userFiles.data.content);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching files:", error);
      }
    })();
  }, [user  ]);

  const handleShare = (file: FileData) => {
    setSelectedFile(file);
    setShowShareModal(true);
  };

  const handleDelete = (id: string) => {
    if (window.confirm("Are you sure you want to delete this file?")) {
      setFiles(files.filter((file) => file.fileId !== id));
    }
  };

  const filteredFiles = searchQuery
    ? files.filter((file) =>
        file.fileName.toLowerCase().includes(searchQuery.toLowerCase())
      )
    : files;

  return (
    <div className="min-h-screen bg-background flex flex-col">
      {/* Header */}
      <header className="border-b border-border sticky top-0 z-10 bg-background">
        <div className="flex items-center justify-between px-4 py-3">
          <div className="flex items-center gap-2">
            <Link to="/" className="flex items-center gap-2 text-primary">
              <FileSymlink className="h-6 w-6" />
              <span className="font-bold">SecureShare</span>
            </Link>

            <div className="ml-8 hidden md:flex">
              <div className="relative">
                <Search className="h-4 w-4 absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground" />
                <Input
                  placeholder="Search files..."
                  className="w-80 pl-9"
                  value={searchQuery}
                  onChange={(e) => setSearchQuery(e.target.value)}
                />
              </div>
            </div>
          </div>

          <div className="flex items-center gap-3">
            <div className="flex gap-2">
              <ThemeToggle />

              <UserDropdown />
            </div>
          </div>
        </div>
      </header>

      {/* Mobile Search */}
      <div className="md:hidden p-4 bg-background">
        <div className="relative">
          <Search className="h-4 w-4 absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground" />
          <Input
            placeholder="Search files..."
            className="w-full pl-9"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
          />
        </div>
      </div>

      {loading ? (
        <SkeletonLoader />
      ) : (
        <main className="flex-1 px-4 py-6">
          <div className="max-w-7xl mx-auto">
            {/* Tabs */}
            <Tabs defaultValue="all">
              <div className="flex justify-between items-center mb-6">
                <TabsList>
                  <TabsTrigger value="all">All Files</TabsTrigger>
                  <TabsTrigger value="shared">Shared</TabsTrigger>
                  <TabsTrigger value="recent">Recent</TabsTrigger>
                </TabsList>

                <div className="flex items-center gap-3">
                  <div className="flex border rounded-md overflow-hidden">
                    <Button
                      variant={view === "grid" ? "default" : "ghost"}
                      size="icon"
                      className="rounded-none h-9 w-9"
                      onClick={() => setView("grid")}
                    >
                      <Grid3X3 className="h-4 w-4" />
                    </Button>
                    <Button
                      variant={view === "list" ? "default" : "ghost"}
                      size="icon"
                      className="rounded-none h-9 w-9"
                      onClick={() => setView("list")}
                    >
                      <List className="h-4 w-4" />
                    </Button>
                  </div>

                  <UploadModal/>
                </div>
              </div>

              <TabsContent value="all" className="mt-0">
                {/* Quick Access Section */}
                <div className="mb-8 animate-fade-in">
                  <div className="flex items-center justify-between mb-4">
                    <h2 className="text-lg font-medium">Quick Access</h2>
                    <Button variant="link" size="sm" className="text-primary">
                      View all <ChevronRight className="h-4 w-4" />
                    </Button>
                  </div>

                  <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                    <div className="border rounded-lg p-4 hover:shadow-sm hover:border-primary/30 transition-all hover-scale">
                      <div className="flex items-center gap-3">
                        <div className="p-2 rounded-md bg-primary/10">
                          <Eye className="h-5 w-5 text-primary" />
                        </div>
                        <div>
                          <h3 className="font-medium">Recently Viewed</h3>
                          <p className="text-xs text-muted-foreground">
                            3 files
                          </p>
                        </div>
                      </div>
                    </div>

                    <div className="border rounded-lg p-4 hover:shadow-sm hover:border-primary/30 transition-all hover-scale">
                      <div className="flex items-center gap-3">
                        <div className="p-2 rounded-md bg-primary/10">
                          <DownloadCloud className="h-5 w-5 text-primary" />
                        </div>
                        <div>
                          <h3 className="font-medium">Recent Downloads</h3>
                          <p className="text-xs text-muted-foreground">
                            5 files
                          </p>
                        </div>
                      </div>
                    </div>

                    <div className="border rounded-lg p-4 hover:shadow-sm hover:border-primary/30 transition-all hover-scale">
                      <div className="flex items-center gap-3">
                        <div className="p-2 rounded-md bg-primary/10">
                          <Clock className="h-5 w-5 text-primary" />
                        </div>
                        <div>
                          <h3 className="font-medium">Expiring Soon</h3>
                          <p className="text-xs text-muted-foreground">
                            2 files
                          </p>
                        </div>
                      </div>
                    </div>

                    <div className="border rounded-lg p-4 hover:shadow-sm hover:border-primary/30 transition-all hover-scale">
                      <div className="flex items-center gap-3">
                        <div className="p-2 rounded-md bg-primary/10">
                          <Plus className="h-5 w-5 text-primary" />
                        </div>
                        <div>
                          <h3 className="font-medium">New Folder</h3>
                          <p className="text-xs text-muted-foreground">
                            Create
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Files Section */}
                <div>
                  <div className="flex items-center justify-between mb-4">
                    <h2 className="text-lg font-medium">Your Files</h2>
                    <div className="text-sm text-muted-foreground">
                      {filteredFiles.length}{" "}
                      {filteredFiles.length === 1 ? "file" : "files"}
                    </div>
                  </div>

                  {filteredFiles.length === 0 ? (
                    <div className="flex flex-col items-center justify-center py-12 text-center">
                      <div className="h-16 w-16 rounded-full bg-muted flex items-center justify-center mb-4">
                        <File className="h-8 w-8 text-muted-foreground" />
                      </div>
                      <h3 className="text-lg font-medium mb-2">
                        No files found
                      </h3>
                      <p className="text-muted-foreground mb-4">
                        {searchQuery
                          ? `No files match "${searchQuery}"`
                          : "Upload your first file to get started"}
                      </p>
                      {!searchQuery && (
                        <UploadModal/>
                      )}
                    </div>
                  ) : view === "grid" ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 animate-fade-in">
                      {filteredFiles.map((file) => (
                        <FileCard
                          key={file.fileId}
                          file={file}
                          onShare={handleShare}
                          onDelete={handleDelete}
                        />
                      ))}
                    </div>
                  ) : (
                    <div className="border rounded-lg divide-y animate-fade-in">
                      {filteredFiles.map((file) => (
                        <FileCard
                          key={file.fileId}
                          file={file}
                          onShare={handleShare}
                          onDelete={handleDelete}
                          compact={true}
                        />
                      ))}
                    </div>
                  )}
                </div>
              </TabsContent>

              <TabsContent value="shared" className="mt-0">
                <div className="flex flex-col items-center justify-center py-12 text-center">
                  <div className="h-16 w-16 rounded-full bg-muted flex items-center justify-center mb-4">
                    <File className="h-8 w-8 text-muted-foreground" />
                  </div>
                  <h3 className="text-lg font-medium mb-2">Shared Files</h3>
                  <p className="text-muted-foreground mb-4">
                    Files shared with you will appear here
                  </p>
                </div>
              </TabsContent>

              <TabsContent value="recent" className="mt-0">
                <div className="flex flex-col items-center justify-center py-12 text-center">
                  <div className="h-16 w-16 rounded-full bg-muted flex items-center justify-center mb-4">
                    <Clock className="h-8 w-8 text-muted-foreground" />
                  </div>
                  <h3 className="text-lg font-medium mb-2">Recent Files</h3>
                  <p className="text-muted-foreground mb-4">
                    Your recently accessed files will appear here
                  </p>
                </div>
              </TabsContent>
            </Tabs>
          </div>
        </main>
      )}

      <ShareModal
        file={selectedFile}
        isOpen={showShareModal}
        onClose={() => {
          setShowShareModal(false);
          setSelectedFile(null);
        }}
      />
    </div>
  );
};

export default Dashboard;
