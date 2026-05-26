"use client";

import { Button } from "@/components/ui/button";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { NexusLogo } from "../nexus-logo";

export function Navbar() {
  const pathName = usePathname();

  const currentView: "signin" | "signup" =
    pathName === "/auth" ? "signin" : "signup";
  return (
    <header className="fixed top-0 left-0 right-0 z-50 border-b bg-background/95 backdrop-blur supports-backdrop-filter:bg-background/60">
      <div className="flex h-16 items-center justify-between px-4 md:px-10">
        {/* Site Logo */}
        <div className="flex items-center font-bold text-xl tracking-tight cursor-pointer">
          <Link href={"/"}>
            <NexusLogo />
          </Link>
        </div>

        {/* Dynamic Navigation Button */}
        <nav>
          {currentView === "signin" ? (
            <Link href={"/auth/sign-up"}>
              <Button variant="ghost">Create an account</Button>
            </Link>
          ) : (
            <Link href={"/auth"}>
              <Button variant="ghost">Sign In</Button>
            </Link>
          )}
        </nav>
      </div>
    </header>
  );
}
