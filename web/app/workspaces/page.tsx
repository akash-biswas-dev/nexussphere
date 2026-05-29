"use client";

import { Plus } from "lucide-react";
import { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";

import { Button } from "@/components/ui/button";
import { EmptyWorkspaceMessage } from "@/components/workspace/empty-workspace";
import { TopNavbar } from "@/components/workspace/navbar";
import WorkspaceCard from "@/components/workspace/workspace-card";
import useUserContext from "@/context/user.context";
import { Workspace } from "@/types/workspace.types";
import { useRouter } from "next/navigation";

export default function Dashboard() {
  const [workspaces, setWorkspaces] = useState<Workspace[]>([]);
  const [page, setPage] = useState<number>(1);
  const [hasMore, setHasMore] = useState<boolean>(true);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const router = useRouter();
  const [isCreating, setIsCreating] = useState(false);

  const { axios } = useUserContext();

  const { ref, inView } = useInView({ threshold: 0.1 });

  useEffect(() => {
    const loadMoreWorkspaces = async () => {
      if (isLoading || !hasMore) return;
      setIsLoading(true);

      try {
        const res = await axios.get("/api/v1/workspaces", {
          params: {
            page,
          },
        });

        const { status, data } = res;

        console.log(status, data);

        if (status !== 200) {
          // Add a error message here.
          setWorkspaces([]);
        }
        setWorkspaces((prev) => [...prev, ...data.content]);
        if (data.totalPages > page) {
          setPage(data.page + 1);
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
  }, [inView, hasMore, isLoading, page, axios]);

  const createWorkspace = async () => {
    if (isCreating) return;
    setIsCreating(true);

    try {
      // 1. Fire the POST request to your backend orchestration layer
      const response = await axios.post("/api/v1/workspaces");

      const { status, data } = response;

      if (status !== 201) {
        throw new Error("Failed to initialize server workspace cluster node.");
      }

      // 2. Extract the fresh target ID from your backend response payload
      // Expecting response JSON format: { id: "ws-99ab4..." }
      const newWorkspaceId = data.id;

      // 3. Seamlessly route the browser viewport directly to the new Figma canvas layout matrix
      router.push(`/workspaces/${newWorkspaceId}`);
    } catch (error) {
      console.error("Error creating workspace target:", error);
      setIsCreating(false); // Reset state only on failure so the user can retry
    }
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-50 flex flex-col font-sans selection:bg-zinc-800">
      <TopNavbar />
      {/* Main Container Layout */}
      <div className="flex-1 flex overflow-hidden">
        {/* Left Sidebar */}
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
                onClick={createWorkspace}
              >
                <Plus className="h-3.5 w-3.5" /> Create Canvas
              </Button>
            </div>

            {/* Grid Area */}
            {workspaces.length === 0 ? (
              <EmptyWorkspaceMessage />
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                {workspaces.map((ws) => (
                  <WorkspaceCard workspace={ws} key={ws.id} />
                ))}
              </div>
            )}

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
