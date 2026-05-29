import { Workspace } from "@/types/workspace.types";
import { Card, CardContent, CardFooter, CardHeader } from "../ui/card";
import { Clock, FileText, MoreHorizontal, Users } from "lucide-react";
import Link from "next/link";

export default function WorkspaceCard({ workspace }: { workspace: Workspace }) {
  const { id, name, lastActive, userCount, pageCount } = workspace;
  return (
    <Link href={`/workspaces/${id}`}>
      <Card
        key={id}
        className="bg-zinc-950 border-zinc-900 hover:border-zinc-800 transition-all duration-200 flex flex-col group cursor-pointer overflow-hidden relative gap-3"
      >
        {/* Visual Mock Canvas Canvas Background Frame */}
        <div className="h-32 bg-zinc-900/40 border-b border-zinc-900/80 relative flex items-center justify-center overflow-hidden">
          {/* Architectural Canvas Dot Matrix Grid pattern simulating an infinite project artboard */}
          <div className="absolute inset-0 bg-[radial-gradient(#27272a_1px,transparent_1px)] [background-size:12px_12px] opacity-70 group-hover:scale-105 transition-transform duration-300" />

          {/* Visual canvas schematic indicator layout line */}
          <div className="absolute border border-dashed border-zinc-800 rounded-sm p-2 w-4/5 h-3/4 flex flex-col justify-between text-[10px] text-zinc-600 font-mono">
            <div className="flex justify-between items-center">
              <span>[artboard_node_x]</span>
              <span>0x7F2A</span>
            </div>
            <div className="h-px w-full bg-zinc-800/40 border-dashed" />
            <div className="flex justify-between items-center">
              <span className="text-[9px] bg-zinc-900/80 px-1 border border-zinc-800">
                LIVE ENGINE STREAM
              </span>
              <MoreHorizontal className="h-3 w-3 opacity-30" />
            </div>
          </div>
        </div>

        <CardHeader className="px-4 py-2 space-y-1">
          <div className="flex items-start justify-between gap-2">
            <h3 className="font-medium text-sm text-zinc-100 tracking-tight line-clamp-1 group-hover:text-zinc-50">
              {name}
            </h3>
          </div>
        </CardHeader>

        <CardContent className="px-4 py-2 pt-0 flex-1 flex items-center gap-3">
          <div className="flex items-center gap-1 text-zinc-400 text-xs">
            <Users className="h-3 w-3 text-zinc-600" />
            <span className="font-mono bg-zinc-900/60 border border-zinc-900 px-1.5 py-0.5 rounded text-[10px]">
              {userCount} developers
            </span>
          </div>
          <div className="flex items-center gap-1 text-zinc-400 text-xs">
            <FileText className="h-3 w-3 text-zinc-600" />
            <span className="font-mono bg-zinc-900/60 border border-zinc-900 px-1.5 py-0.5 rounded text-[10px]">
              {pageCount} pages
            </span>
          </div>
        </CardContent>

        <CardFooter className="px-4 py-2 pt-0 border-t border-zinc-900/50 bg-zinc-900/10 flex items-center justify-between text-[10px] text-zinc-500 font-mono">
          <div className="flex items-center gap-1">
            <Clock className="h-2.5 w-2.5 text-zinc-600" />
            <span>Updated {lastActive}</span>
          </div>
          <span className="opacity-0 group-hover:opacity-100 text-zinc-400 transition-opacity flex items-center gap-1 text-[9px] uppercase tracking-wider font-sans font-bold">
            Open Editor &rarr;
          </span>
        </CardFooter>
      </Card>
    </Link>
  );
}
