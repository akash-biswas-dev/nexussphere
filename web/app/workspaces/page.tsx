"use client";

import { Folder, History, LogOut, Plus, Search, User } from "lucide-react";
import { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";

import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import WorkspaceCard from "@/components/workspace/workspace-card";
import { Workspace, WorkspaceResponse } from "@/types/workspace.types";

// Mock API Call simulating paginated backend action
const fetchWorkspacesPage = async (
  page: number,
): Promise<WorkspaceResponse> => {
  await new Promise((resolve) => setTimeout(resolve, 600));
  const limit = 6; // Balanced count for a 3-column responsive grid
  const start = (page - 1) * limit;

  const mockDb: Workspace[] = Array.from({ length: 30 }).map((_, i) => ({
    id: `ws-${i + 1}`,
    name: `NexusSphere Production Cluster ${String.fromCharCode(65 + (i % 6))}${Math.floor(i / 6) || ""}`,
    ownedBy: i % i === 0 || i === 0 ? "You" : "alex.rivers@nexus.io",
    lastActive: `${i + 1}h ago`,
    joinedOn: "2026-02-14",
    userCount: Math.floor(Math.random() * 8) + 2,
    pageCount: Math.floor(Math.random() * 12) + 1,
  }));

  const slicedData = mockDb.slice(start, start + limit);
  const nextPage = start + limit < mockDb.length ? page + 1 : null;

  return { workspaces: slicedData, nextPage };
};

export default function Dashboard() {
  const [workspaces, setWorkspaces] = useState<Workspace[]>([]);
  const [page, setPage] = useState<number>(1);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const { ref, inView } = useInView({ threshold: 0.1 });

  useEffect(() => {
    const loadMoreWorkspaces = async () => {
      if (isLoading || !hasMore) return;
      setIsLoading(true);

      try {
        const data = await fetchWorkspacesPage(page);
        setWorkspaces((prev) => [...prev, ...data.workspaces]);
        if (data.nextPage) {
          setPage(data.nextPage);
        } else {
          setHasMore(false);
        }
      } catch (error) {
        console.error("Failed to fetch workspaces payload:", error);
      } finally {
        setIsLoading(false);
      }
    };
    if (inView && hasMore) {
      loadMoreWorkspaces();
    }
  }, [inView, hasMore, isLoading, page]);

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-50 flex flex-col font-sans selection:bg-zinc-800">
      {/* Top Navbar */}
      <header className="border-b border-zinc-900 bg-zinc-950/80 backdrop-blur sticky top-0 z-40 h-14 flex items-center justify-between px-6">
        <div className="flex items-center gap-2 font-bold text-sm tracking-tight">
          <div className="h-5 w-5 rounded bg-zinc-50 text-zinc-950 flex items-center justify-center font-black text-xs">
            N
          </div>
          <span>NexusSphere Engine</span>
        </div>

        <div className="flex items-center gap-4">
          <div className="relative w-48 hidden sm:block">
            <Search className="absolute left-2.5 top-2.5 h-3.5 w-3.5 text-zinc-500" />
            <input
              type="text"
              placeholder="Quick search..."
              className="w-full bg-zinc-900/50 border border-zinc-800 rounded-md py-1.5 pl-8 pr-3 text-xs text-zinc-300 placeholder-zinc-500 focus:outline-none focus:border-zinc-700 transition-colors"
            />
          </div>

          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="ghost"
                className="relative h-8 w-8 rounded-full border border-zinc-800 p-0 hover:bg-zinc-900"
              >
                <Avatar className="h-7 w-7">
                  <AvatarFallback className="bg-zinc-900 text-zinc-400 text-xs font-medium">
                    SE
                  </AvatarFallback>
                </Avatar>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent
              className="w-56 bg-zinc-950 border-zinc-900 text-zinc-300"
              align="end"
            >
              <DropdownMenuLabel className="font-normal text-xs text-zinc-500">
                <div className="flex flex-col space-y-1">
                  <p className="text-sm font-medium text-zinc-200">
                    Software Engineer
                  </p>
                  <p className="text-xs leading-none">dev@nexussphere.io</p>
                </div>
              </DropdownMenuLabel>
              <DropdownMenuSeparator className="bg-zinc-900" />
              <DropdownMenuItem className="focus:bg-zinc-900 focus:text-zinc-50 cursor-pointer text-xs">
                <User className="mr-2 h-3.5 w-3.5 text-zinc-500" /> Profile
                Settings
              </DropdownMenuItem>
              <DropdownMenuSeparator className="bg-zinc-900" />
              <DropdownMenuItem className="focus:bg-zinc-900 focus:text-zinc-50 text-red-400 focus:text-red-300 cursor-pointer text-xs">
                <LogOut className="mr-2 h-3.5 w-3.5" /> Log out
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </header>

      {/* Main Container Layout */}
      <div className="flex-1 flex overflow-hidden">
        {/* Left Sidebar */}
        <aside className="w-60 border-r border-zinc-900 bg-zinc-950/50 hidden md:flex flex-col p-4 justify-between">
          <div className="space-y-6">
            <div className="space-y-1">
              <span className="px-3 text-[10px] font-bold uppercase tracking-widest text-zinc-500 block mb-2">
                Navigation
              </span>
              <Button
                variant="secondary"
                className="w-full justify-start bg-zinc-900 text-zinc-100 hover:bg-zinc-900 text-xs h-9"
              >
                <Folder className="mr-2 h-4 w-4 text-zinc-400" /> Workspaces
              </Button>
            </div>

            <div className="space-y-1">
              <span className="px-3 text-[10px] font-bold uppercase tracking-widest text-zinc-500 flex items-center gap-1.5 mb-2">
                <History className="h-3 w-3" /> Recents
              </span>
              <div className="space-y-0.5 px-1">
                {[
                  "Core Matching Node",
                  "SaaS Finance V4",
                  "Telemetry Analytics",
                ].map((recentName, i) => (
                  <button
                    key={i}
                    className="w-full text-left px-2 py-1.5 rounded text-xs text-zinc-400 hover:text-zinc-200 hover:bg-zinc-900/50 transition-colors truncate block"
                  >
                    /{recentName}
                  </button>
                ))}
              </div>
            </div>
          </div>
        </aside>

        {/* Workspaces Dynamic Content Wrapper */}
        <main className="flex-1 overflow-y-auto px-6 py-8">
          <div className="max-w-6xl mx-auto space-y-6">
            {/* Header section */}
            <div className="flex items-center justify-between">
              <div>
                <h1 className="text-xl font-bold tracking-tight text-zinc-100">
                  Workspaces
                </h1>
                <p className="text-xs text-zinc-400 mt-0.5">
                  Select a viewport canvas grid to launch real-time multi-tenant
                  execution loops.
                </p>
              </div>
              <Button
                size="sm"
                className="bg-zinc-50 text-zinc-950 hover:bg-zinc-200 text-xs font-medium gap-1.5"
              >
                <Plus className="h-3.5 w-3.5" /> Create Canvas
              </Button>
            </div>

            {/* Grid Area */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
              {workspaces.map((ws) => (
                <WorkspaceCard workspace={ws} key={ws.id} />
              ))}
            </div>

            {/* Infinite Scroll Detection Sentinel */}
            <div
              ref={ref}
              className="w-full py-8 flex items-center justify-center"
            >
              {isLoading && (
                <div className="flex items-center gap-2 text-zinc-500 text-xs font-mono">
                  <span className="h-3 w-3 rounded-full border border-zinc-700 border-t-zinc-400 animate-spin" />
                  Streaming fresh canvas payload metadata...
                </div>
              )}
              {!hasMore && workspaces.length > 0 && (
                <span className="text-zinc-600 text-xs font-mono tracking-tight">
                  End of canvas registry catalog.
                </span>
              )}
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}
