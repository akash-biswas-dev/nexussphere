import { Folder, History } from "lucide-react";
import { Button } from "../ui/button";

export default function Sidebar() {
  return (
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
  );
}
