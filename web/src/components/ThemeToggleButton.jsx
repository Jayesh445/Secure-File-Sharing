import { useTheme } from "next-themes";
import { Sun, Moon } from "lucide-react";
import { Button } from "@/components/ui/button";
const ThemeToggleButton = () => {
  const { theme, setTheme } = useTheme();

  return (
    <Button
      variant="outline"
      size="icon"
      onClick={() => setTheme(theme === "dark" ? "light" : "dark")}
    >
      <Sun className="w-5 h-5 dark:hidden" />
      <Moon className="w-5 h-5 hidden dark:block" />
    </Button>
  );
};

export default ThemeToggleButton;
