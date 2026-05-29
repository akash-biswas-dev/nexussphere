import { logout } from "@/app/auth/action";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import useUserContext from "@/context/user.context";
import { ChevronDown, LogOut, Settings, User } from "lucide-react";
import Link from "next/link";
import { NexusLogo } from "../nexus-logo";

export function TopNavbar() {
  const { authorization } = useUserContext();

  const { firstName, lastName, email } = authorization.user;

  const initials = firstName[0] + lastName[0];

  return (
    <header className="h-13 border-b border-border bg-background flex items-center justify-between px-5 shrink-0 z-10">
      <Link href="/home">
        <NexusLogo size={24} />
      </Link>

      {/* Right — Actions + Avatar */}
      <div className="flex items-center gap-2">
        {/* If anything need to add on the right side of the navbar */}

        <DropdownMenu>
          {/* Dropdown with username and initials */}
          <DropdownMenuTrigger asChild>
            <div className="flex items-center gap-2 pl-1 pr-2.5 py-1 rounded-full border border-border hover:bg-accent cursor-pointer transition-colors">
              <Avatar className="h-6 w-6">
                <AvatarFallback className="text-[10px] bg-blue-100 text-blue-700 font-medium">
                  {initials}
                </AvatarFallback>
              </Avatar>
              <ChevronDown className="h-3 w-3 text-muted-foreground" />
            </div>
          </DropdownMenuTrigger>

          <DropdownMenuContent align="end" className="w-fit max-w-56">
            <DropdownMenuLabel>
              <p className="text-xs text-muted-foreground font-normal">
                {email}
              </p>
            </DropdownMenuLabel>
            <DropdownMenuSeparator />

            <DropdownMenuItem>
              <Link href={"/home/profile"} className="flex gap-4 items-center">
                <User className="h-3.5 w-3.5 shrink-0" />
                Profile
              </Link>
            </DropdownMenuItem>
            <DropdownMenuItem>
              <Settings className="mr-2 h-3.5 w-3.5" />
              Settings
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem
              className="text-destructive focus:text-destructive"
              onClick={logout}
            >
              <LogOut className="mr-2 h-3.5 w-3.5" />
              Log out
            </DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </header>
  );
}
