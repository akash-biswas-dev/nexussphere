"use client";

import { LayoutGrid } from "lucide-react";

export function EmptyWorkspaceMessage() {
  return (
    <div className="flex flex-col w-full h-full items-center justify-center  p-8 text-center animate-in fade-in duration-300">
      {/* Low-profile Minimal Anchor Icon */}
      <div className="h-10 w-10 rounded-lg border border-zinc-900 bg-zinc-900/20 flex items-center justify-center mb-4">
        <LayoutGrid className="h-4 w-4 text-zinc-500" />
      </div>

      {/* Core Feedback Copy */}
      <div className="space-y-1 max-w-xs">
        <h3 className="text-zinc-200 font-medium text-sm tracking-tight">
          No workspaces found
        </h3>
        <p className="text-zinc-500 text-xs font-light leading-normal">
          This workspace cluster has no active design canvases. Initialize one
          to start building.
        </p>
      </div>
    </div>
  );
}
