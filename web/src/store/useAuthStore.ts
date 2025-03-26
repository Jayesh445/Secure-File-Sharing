import { create } from 'zustand';

interface User {
    userId: string;
    name: string;
    email: string;
    roleType?: string;
    createdAt: Date;
}

interface AuthState {
    user: User | null;
    isAuthenticated: boolean;
    login: (userData: User) => void;
    logout: () => void;
    updateUser: (newUserData: Partial<User>) => void;
}

const useAuthStore = create<AuthState>((set) => ({
    user: null,
    isAuthenticated: false, 
    token:null,

    login: (userData) => set({ user: userData, isAuthenticated: true }),

    logout: () => set({ user: null, isAuthenticated: false }),

    updateUser: (newUserData) =>
        set((state) => ({
            user: state.user ? { ...state.user, ...newUserData } : null,
        })),
}));

export default useAuthStore;
