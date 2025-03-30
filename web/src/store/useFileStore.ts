import { create } from "zustand";
import apiClient from "@/lib/axios";
import useAuthStore from "./useAuthStore";
import { toast } from "sonner";

export interface FileData {
    fileId: string;
    fileName: string;
    fileType: string;
    fileSize: string;
    filePath: string;
    folderPath: string;
    createdAt: Date;
    shared: boolean;
}

interface FileState {
    files: FileData[];
    isLoading: boolean;
    fetchFiles: () => Promise<void>;
    deleteFile: (id: string) => Promise<void>;
}

const useFileStore = create<FileState>((set) => ({
    files: [],
    isLoading: false,

    fetchFiles: async () => {
        console.log("calling....")
        const { user, token } = useAuthStore.getState();
        const { files } = useFileStore.getState();
        if (!user) return;
        if (files.length !== 0) return;

        set({ isLoading: true });

        try {
            if (!token) throw new Error("No authentication token found");

            const userFilesResponse = await apiClient.get(`/files/user/${user.userId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            const sharedFilesResponse = await apiClient.get(`/files/shared/${user.userId}`, {
                headers: { Authorization: `Bearer ${token}` },
            });

            // Mark shared files
            const sharedFiles = sharedFilesResponse.data.content.map((file: FileData) => ({
                ...file,
                shared: true,
            }));

            console.log(userFilesResponse)
            console.log(sharedFilesResponse)

            set({ files: [...userFilesResponse.data.content, ...sharedFiles], isLoading: false });
        } catch (error) {
            console.error("Error fetching files:", error);
            set({ isLoading: false });
        }
        console.log("done")
    },

    deleteFile: async (id: string) => {
        const { files } = useFileStore.getState();

        if (window.confirm("Are you sure you want to delete this file?")) {
            try {
                const token = localStorage.getItem("token");
                if (!token) throw new Error("No authentication token found");

                const response = await apiClient.delete(`/files/${id}`, {
                    headers: { Authorization: `Bearer ${token}` },
                });
                console.log(response);

                // Update state by filtering out the deleted file
                set({ files: files.filter((file) => file.fileId !== id) });
            } catch (error) {
                console.error("Error deleting file:", error);
                alert("Failed to delete file.");
            }
        }
    }

}));

export default useFileStore;
