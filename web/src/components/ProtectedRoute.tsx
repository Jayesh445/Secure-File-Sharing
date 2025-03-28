import { Navigate, Outlet } from "react-router-dom";
import useAuthStore from "@/store/useAuthStore";

interface ProtectedRouteProps {
  requiredRole?: "ADMIN" | "USER";
}

const ProtectedRoute = ({ requiredRole }: ProtectedRouteProps) => {
  const { isAuthenticated, user } = useAuthStore();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (requiredRole && user?.roleType !== requiredRole) {
    return <Navigate to="/403" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
