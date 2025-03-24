
import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import { Shield, Users, ClipboardList, ChevronDown, UserCog, UserX, Pencil, Search, Eye } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from "@/components/ui/alert-dialog";
import { Separator } from "@/components/ui/separator";
import { useToast } from "@/hooks/use-toast";

// Mock data for users
const mockUsers = [
  { id: 1, name: "John Doe", email: "john@example.com", role: "Admin", createdAt: "2023-01-15" },
  { id: 2, name: "Jane Smith", email: "jane@example.com", role: "User", createdAt: "2023-02-20" },
  { id: 3, name: "Robert Johnson", email: "robert@example.com", role: "User", createdAt: "2023-03-10" },
  { id: 4, name: "Emily Davis", email: "emily@example.com", role: "User", createdAt: "2023-04-05" },
  { id: 5, name: "Michael Wilson", email: "michael@example.com", role: "User", createdAt: "2023-05-12" },
];

// Mock audit logs
const mockLogs = [
  { id: 1, user: "John Doe", action: "File Upload", details: "Uploaded document.pdf", timestamp: "2023-05-15 14:30:45" },
  { id: 2, user: "Jane Smith", action: "File Share", details: "Shared report.xlsx with robert@example.com", timestamp: "2023-05-15 15:22:18" },
  { id: 3, user: "System", action: "User Created", details: "New user michael@example.com", timestamp: "2023-05-12 09:45:12" },
  { id: 4, user: "John Doe", action: "Admin Action", details: "Promoted Jane Smith to admin", timestamp: "2023-05-10 11:15:30" },
  { id: 5, user: "Robert Johnson", action: "File Download", details: "Downloaded presentation.pptx", timestamp: "2023-05-08 16:40:22" },
  { id: 6, user: "System", action: "Maintenance", details: "System backup completed", timestamp: "2023-05-01 02:00:00" },
];

interface User {
  id: number;
  name: string;
  email: string;
  role: string;
  createdAt: string;
}

interface LogEntry {
  id: number;
  user: string;
  action: string;
  details: string;
  timestamp: string;
}

const AdminDashboard = () => {
  const [users, setUsers] = useState<User[]>(mockUsers);
  const [logs, setLogs] = useState<LogEntry[]>(mockLogs);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedUser, setSelectedUser] = useState<User | null>(null);
  const { toast } = useToast();

  const filteredUsers = users.filter(user => 
    user.name.toLowerCase().includes(searchTerm.toLowerCase()) || 
    user.email.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handlePromoteUser = (userId: number) => {
    setUsers(prevUsers => 
      prevUsers.map(user => 
        user.id === userId ? { ...user, role: "Admin" } : user
      )
    );
    
    toast({
      title: "User promoted to Admin",
      description: "User has been successfully granted admin privileges.",
      variant: "default",
    });
  };

  const handleDemoteUser = (userId: number) => {
    setUsers(prevUsers => 
      prevUsers.map(user => 
        user.id === userId ? { ...user, role: "User" } : user
      )
    );
    
    toast({
      title: "Admin privileges revoked",
      description: "User's admin privileges have been revoked.",
      variant: "default",
    });
  };

  // Animation variants
  const containerVariants = {
    hidden: { opacity: 0 },
    visible: { 
      opacity: 1,
      transition: { 
        staggerChildren: 0.1
      }
    }
  };
  
  const itemVariants = {
    hidden: { y: 20, opacity: 0 },
    visible: { 
      y: 0, 
      opacity: 1,
      transition: { 
        type: "spring",
        stiffness: 300,
        damping: 24
      }
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <motion.div
        initial={{ y: -20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ duration: 0.5 }}
        className="flex items-center justify-between mb-8"
      >
        <div className="flex items-center">
          <Shield className="h-8 w-8 text-primary mr-2" />
          <h1 className="text-3xl font-bold">Admin Dashboard</h1>
        </div>
      </motion.div>

      <Tabs defaultValue="users" className="w-full">
        <TabsList className="grid w-full grid-cols-2 mb-8">
          <TabsTrigger value="users" className="text-lg">
            <Users className="mr-2 h-5 w-5" />
            User Management
          </TabsTrigger>
          <TabsTrigger value="logs" className="text-lg">
            <ClipboardList className="mr-2 h-5 w-5" />
            Audit Logs
          </TabsTrigger>
        </TabsList>

        <TabsContent value="users">
          <motion.div
            variants={containerVariants}
            initial="hidden"
            animate="visible"
          >
            <motion.div 
              variants={itemVariants}
              className="mb-6 flex justify-between items-center"
            >
              <div className="relative w-full max-w-md">
                <Search className="absolute top-2.5 left-3 h-4 w-4 text-muted-foreground" />
                <Input
                  placeholder="Search users by name or email..."
                  className="pl-10 w-full"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>
            </motion.div>

            <motion.div 
              variants={itemVariants}
              className="bg-card rounded-lg shadow-md overflow-hidden"
            >
              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-muted/50">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        User
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Role
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Created At
                      </th>
                      <th className="px-6 py-3 text-right text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Actions
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-border">
                    <AnimatePresence>
                      {filteredUsers.map((user) => (
                        <motion.tr 
                          key={user.id}
                          initial={{ opacity: 0, y: 20 }}
                          animate={{ opacity: 1, y: 0 }}
                          exit={{ opacity: 0, scale: 0.95 }}
                          transition={{ duration: 0.3 }}
                          className="bg-card hover:bg-muted/30 transition-colors"
                        >
                          <td className="px-6 py-4 whitespace-nowrap">
                            <div className="flex items-center">
                              <div className="h-10 w-10 flex-shrink-0 rounded-full bg-primary/10 flex items-center justify-center">
                                <span className="text-primary font-medium">{user.name.charAt(0)}</span>
                              </div>
                              <div className="ml-4">
                                <div className="text-sm font-medium text-foreground">{user.name}</div>
                                <div className="text-sm text-muted-foreground">{user.email}</div>
                              </div>
                            </div>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap">
                            <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                              user.role === "Admin" 
                                ? "bg-primary/10 text-primary" 
                                : "bg-secondary text-secondary-foreground"
                            }`}>
                              {user.role}
                            </span>
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap text-sm text-muted-foreground">
                            {user.createdAt}
                          </td>
                          <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                            <div className="flex justify-end space-x-2">
                              {user.role !== "Admin" ? (
                                <AlertDialog>
                                  <AlertDialogTrigger asChild>
                                    <Button variant="outline" size="sm" className="flex items-center">
                                      <UserCog className="h-4 w-4 mr-1" />
                                      Make Admin
                                    </Button>
                                  </AlertDialogTrigger>
                                  <AlertDialogContent>
                                    <AlertDialogHeader>
                                      <AlertDialogTitle>Grant Admin Privileges</AlertDialogTitle>
                                      <AlertDialogDescription>
                                        Are you sure you want to make {user.name} an admin? This will grant them full control over the platform.
                                      </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                                      <AlertDialogAction onClick={() => handlePromoteUser(user.id)}>
                                        Confirm
                                      </AlertDialogAction>
                                    </AlertDialogFooter>
                                  </AlertDialogContent>
                                </AlertDialog>
                              ) : (
                                <AlertDialog>
                                  <AlertDialogTrigger asChild>
                                    <Button variant="outline" size="sm" className="flex items-center">
                                      <UserX className="h-4 w-4 mr-1" />
                                      Remove Admin
                                    </Button>
                                  </AlertDialogTrigger>
                                  <AlertDialogContent>
                                    <AlertDialogHeader>
                                      <AlertDialogTitle>Remove Admin Privileges</AlertDialogTitle>
                                      <AlertDialogDescription>
                                        Are you sure you want to remove admin privileges from {user.name}? They will no longer have access to the admin dashboard.
                                      </AlertDialogDescription>
                                    </AlertDialogHeader>
                                    <AlertDialogFooter>
                                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                                      <AlertDialogAction onClick={() => handleDemoteUser(user.id)}>
                                        Confirm
                                      </AlertDialogAction>
                                    </AlertDialogFooter>
                                  </AlertDialogContent>
                                </AlertDialog>
                              )}
                            </div>
                          </td>
                        </motion.tr>
                      ))}
                    </AnimatePresence>
                  </tbody>
                </table>
              </div>
              {filteredUsers.length === 0 && (
                <div className="py-10 text-center">
                  <p className="text-muted-foreground">No users found matching your search criteria.</p>
                </div>
              )}
            </motion.div>
          </motion.div>
        </TabsContent>

        <TabsContent value="logs">
          <motion.div
            variants={containerVariants}
            initial="hidden"
            animate="visible"
          >
            <motion.div variants={itemVariants}>
              <Card className="overflow-hidden">
                <CardHeader>
                  <CardTitle className="text-2xl flex items-center">
                    <ClipboardList className="h-6 w-6 mr-2" />
                    System Activity Logs
                  </CardTitle>
                  <CardDescription>
                    Comprehensive history of all user and system activities
                  </CardDescription>
                </CardHeader>
                <CardContent className="p-0">
                  <div className="max-h-[600px] overflow-y-auto">
                    <div className="p-6 grid gap-4">
                      {logs.map((log) => (
                        <motion.div
                          key={log.id}
                          initial={{ opacity: 0, y: 10 }}
                          animate={{ opacity: 1, y: 0 }}
                          transition={{ duration: 0.3 }}
                          className="border-b border-border pb-4 last:border-0 last:pb-0"
                        >
                          <div className="flex justify-between mb-2">
                            <div className="flex items-center">
                              <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                                log.action.includes("Admin") 
                                  ? "bg-primary/10 text-primary" 
                                  : log.action.includes("System") 
                                    ? "bg-orange-100 text-orange-800 dark:bg-orange-900/30 dark:text-orange-400" 
                                    : "bg-secondary text-secondary-foreground"
                              }`}>
                                {log.action}
                              </span>
                              <span className="ml-2 text-sm text-muted-foreground">{log.user}</span>
                            </div>
                            <span className="text-xs text-muted-foreground">{log.timestamp}</span>
                          </div>
                          <p className="text-sm">{log.details}</p>
                        </motion.div>
                      ))}
                    </div>
                  </div>
                </CardContent>
              </Card>
            </motion.div>
          </motion.div>
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default AdminDashboard;
