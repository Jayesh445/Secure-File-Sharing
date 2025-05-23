import { LogOut, Settings, User } from "lucide-react"
import { Button } from "./ui/button"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from "./ui/dropdown-menu"
import useAuthStore from "@/store/useAuthStore"
import  {useNavigate} from "react-router-dom";
import apiClient from "@/lib/axios";

const UserDropdown = () => {
  const {logout,token} = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = (e:React.MouseEvent) => {
    e.preventDefault();
    if(confirm("Are you sure you want to log out?")){
      apiClient.post("/api/auth/logout",{},{
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
      logout();
      navigate("/");
    }

  }
  return (
    <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button size="icon" variant="ghost" className="rounded-full h-10 w-10">
                    <User className="h-5 w-5" />
                    <span className="sr-only">User menu</span>
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end">
                  <DropdownMenuItem>
                    <User className="h-4 w-4 mr-2" />
                    Profile
                  </DropdownMenuItem>
                  <DropdownMenuItem>
                    <Settings className="h-4 w-4 mr-2" />
                    Settings
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={handleLogout}>
                    <LogOut className="h-4 w-4 mr-2" />
                    Log out
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
  )
}

export default UserDropdown;