import { create } from "zustand";

interface User {
    userId: string;
    name: string;
    email: string;
    roleType?: string;
    createdAt: Date;
}

interface AuthState {
    user: User | null;
    token: string | null;
    isAuthenticated: boolean;
    login: (userData: User, token: string) => void;
    logout: () => void;
    updateUser: (newUserData: Partial<User>) => void;
}

// Load user and token from localStorage
const storedUser = localStorage.getItem("user");
const storedToken = localStorage.getItem("token");

const useAuthStore = create<AuthState>((set) => ({
    user: storedUser ? JSON.parse(storedUser) : null,
    token: storedToken || null,
    isAuthenticated: !!storedToken, 

    login: (userData, token) => {
        localStorage.setItem("user", JSON.stringify(userData));
        localStorage.setItem("token", token);
        set({ user: userData, token, isAuthenticated: true });
    },

    logout: () => {
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        set({ user: null, token: null, isAuthenticated: false });
    },

    updateUser: (newUserData) =>
        set((state) => {
            const updatedUser = state.user ? { ...state.user, ...newUserData } : null;
            if (updatedUser) localStorage.setItem("user", JSON.stringify(updatedUser));
            return { user: updatedUser };
        }),
}));

export default useAuthStore;
