"use client";

import React, { useState, useRef, useEffect } from "react";
import {
  Plus,
  Layers,
  Database,
  Sparkles,
  Download,
  MousePointer,
  Hand,
  LineChart,
  HelpCircle,
  FileText,
  Play,
  Check,
} from "lucide-react";
import Panzoom, { PanzoomEvent, PanzoomObject } from "@panzoom/panzoom";

import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import {
  Command,
  CommandGroup,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import { CanvasPage, DataAssetSource } from "@/types/canvas.types";
import { element } from "three/tsl";

export default function WorkspaceCanvasEngine({
  workspaceId,
}: {
  workspaceId: string;
}) {
  // --- Core Application State Matrix ---
  const [activePageId, setActivePageId] = useState<string>("page-1");
  const [pages, setPages] = useState<CanvasPage[]>([
    {
      id: "Page-1",
      name: "Main Operations Overview",
      elements: ["revenue-chart", "active-users"],
    },
    {
      id: "Page-2",
      name: "AI Predictive Analytics",
      elements: ["regression-node"],
    },
  ]);
  const [dataSources, setDataSources] = useState<DataAssetSource[]>([
    { id: "ds-1", name: "live_server_telemetry.stream", type: "stream" },
  ]);

  const [currentTool, setCurrentTool] = useState<"select" | "hand">("select");
  const [newPageName, setNewPageName] = useState("");
  const [isNewPageOpen, setIsNewPageOpen] = useState(false);

  // --- Infinite Canvas Ref Engine ---
  const canvasAreaRef = useRef<HTMLDivElement>(null);
  const panzoomInstance = useRef<PanzoomObject | null>(null);

  // Initialize Panzoom on the target viewport node to simulate a Figma infinite plane
  useEffect(() => {
    if (canvasAreaRef.current) {
      panzoomInstance.current = Panzoom(canvasAreaRef.current, {
        maxScale: 4,
        minScale: 0.25,
        contain: "outside",
        cursor: currentTool === "hand" ? "grab" : "default",
      });

      // Bind trackpad/mouse scroll wheel to modern zoom matrix loops
      const parentElement = canvasAreaRef.current.parentElement;
      if (parentElement) {
        parentElement.addEventListener(
          "wheel",
          panzoomInstance.current.zoomWithWheel,
        );
      }
    }

    return () => {
      if (panzoomInstance.current) {
        panzoomInstance.current.destroy();
      }
    };
  }, []);

  // Update canvas drag parameters whenever active tool state mutates
  useEffect(() => {
    if (panzoomInstance.current) {
      panzoomInstance.current.setOptions({
        disablePan: currentTool !== "hand",
      });
    }
  }, [currentTool]);

  const handleCreatePage = () => {
    if (!newPageName.trim()) return;

    const newPageId = `page-${Date.now()}`;
    const newPage = {
      id: newPageId,
      name: newPageName,
      elements: [],
    };

    setPages([...pages, newPage]);
    setPages((prev) => [...prev, newPage]);
    setActivePageId(newPageId); // Contextually switch target workspace frame
    setNewPageName("");
    setIsNewPageOpen(false);
  };

  const handleAddDataSource = (type: string, name: string) => {
    setDataSources((prev) => [...prev, { id: `ds-${Date.now()}`, name, type }]);
  };

  const activePage = pages.find((p) => p.id === activePageId) || pages[0];

  return (
    <div className="h-screen w-screen bg-zinc-950 text-zinc-50 flex flex-col font-sans overflow-hidden select-none">
      {/* 1. Figmatic System Application Header */}
      <header className="h-12 border-b border-zinc-900 bg-zinc-950 px-4 flex items-center justify-between relative z-50">
        <div className="flex items-center gap-6">
          <div className="flex items-center gap-2 font-bold text-xs tracking-wider uppercase text-zinc-400">
            <span className="h-4 w-4 bg-zinc-50 text-zinc-950 font-black flex items-center justify-center rounded-sm text-[10px]">
              N
            </span>
            <span>NexusSphere Workspace</span>
          </div>

          <div className="h-4 w-[1px] bg-zinc-900" />

          {/* Canvas Mode/Tool Controllers */}
          <div className="flex items-center gap-1 bg-zinc-900/50 border border-zinc-900 p-0.5 rounded-md">
            <Button
              variant={currentTool === "select" ? "secondary" : "ghost"}
              size="icon"
              className="h-7 w-7 rounded"
              onClick={() => setCurrentTool("select")}
            >
              <MousePointer className="h-3.5 w-3.5" />
            </Button>
            <Button
              variant={currentTool === "hand" ? "secondary" : "ghost"}
              size="icon"
              className="h-7 w-7 rounded"
              onClick={() => setCurrentTool("hand")}
            >
              <Hand className="h-3.5 w-3.5" />
            </Button>
          </div>
        </div>

        {/* Dynamic Context Controls: Data Source Injection Layer */}
        <div className="flex items-center gap-3">
          <Popover>
            <PopoverTrigger asChild>
              <Button
                size="sm"
                variant="outline"
                className="border-zinc-800 bg-zinc-950 text-zinc-300 text-xs gap-1.5 h-8"
              >
                <Database className="h-3.5 w-3.5 text-zinc-500" /> Connect
                Source
              </Button>
            </PopoverTrigger>
            <PopoverContent
              className="w-56 bg-zinc-950 border-zinc-900 p-0 text-zinc-300"
              align="center"
            >
              <Command className="bg-transparent">
                <CommandList>
                  <CommandGroup heading="Ingestion Pipelines">
                    <CommandItem
                      className="text-xs cursor-pointer focus:bg-zinc-900 py-2"
                      onSelect={() =>
                        handleAddDataSource("csv", "financial_ledger.csv")
                      }
                    >
                      Upload Static CSV
                    </CommandItem>
                    <CommandItem
                      className="text-xs cursor-pointer focus:bg-zinc-900 py-2"
                      onSelect={() =>
                        handleAddDataSource("excel", "enterprise_kpi.xlsx")
                      }
                    >
                      Link Excel Workbook
                    </CommandItem>
                    <CommandItem
                      className="text-xs cursor-pointer focus:bg-zinc-900 py-2"
                      onSelect={() =>
                        handleAddDataSource("stream", "websocket_telemetry.io")
                      }
                    >
                      Initialize Live Webhook
                    </CommandItem>
                  </CommandGroup>
                </CommandList>
              </Command>
            </PopoverContent>
          </Popover>

          <Button
            size="sm"
            className="bg-zinc-50 text-zinc-950 hover:bg-zinc-200 text-xs font-medium gap-1.5 h-8"
          >
            <Sparkles className="h-3.5 w-3.5" /> AI Dashboard Prompt
          </Button>
        </div>
      </header>

      {/* Main Framework Structural Splitting Area */}
      <div className="flex-1 flex overflow-hidden relative">
        {/* 2. Left Structural Page Matrix Panel Sidebar */}
        <aside className="w-60 border-r border-zinc-900 bg-zinc-950 flex flex-col justify-between relative z-40">
          <div className="p-3 flex-1 flex flex-col overflow-y-auto">
            {/* Pages Subsection Header */}
            <div className="flex items-center justify-between mb-2 px-2">
              <span className="text-[10px] font-bold uppercase tracking-widest text-zinc-500 flex items-center gap-1.5">
                <Layers className="h-3 w-3" /> Canvas Pages
              </span>

              {/* Add New Page Dialog Trigger */}
              <Dialog open={isNewPageOpen} onOpenChange={setIsNewPageOpen}>
                <DialogTrigger asChild>
                  <Button
                    variant="ghost"
                    size="icon"
                    className="h-5 w-5 text-zinc-500 hover:text-zinc-100 p-0"
                  >
                    <Plus className="h-3.5 w-3.5" />
                  </Button>
                </DialogTrigger>
                <DialogContent className="bg-zinc-950 border-zinc-900 text-zinc-200 max-w-xs">
                  <DialogHeader>
                    <DialogTitle className="text-sm font-semibold tracking-tight">
                      Create Artboard Page
                    </DialogTitle>
                  </DialogHeader>
                  <form onSubmit={handleCreatePage} className="space-y-4 pt-2">
                    <input
                      type="text"
                      placeholder="e.g. Marketing Telemetry"
                      value={newPageName}
                      onChange={(e) => setNewPageName(e.target.value)}
                      className="w-full bg-zinc-900 border border-zinc-800 rounded-md py-1.5 px-3 text-xs text-zinc-100 placeholder-zinc-500 focus:outline-none focus:border-zinc-700"
                      autoFocus
                    />
                    <Button
                      type="submit"
                      size="sm"
                      className="w-full bg-zinc-50 text-zinc-950 text-xs font-medium"
                    >
                      Initialize Page
                    </Button>
                  </form>
                </DialogContent>
              </Dialog>
            </div>

            {/* Iterative Active Page Selection Array */}
            <div className="space-y-0.5">
              {pages.map((page) => (
                <button
                  key={page.id}
                  onClick={() => setActivePageId(page.id)}
                  className={`w-full flex items-center justify-between px-2.5 py-1.5 rounded text-xs transition-colors group ${
                    page.id === activePageId
                      ? "bg-zinc-900 text-zinc-50 font-medium"
                      : "text-zinc-400 hover:text-zinc-200 hover:bg-zinc-900/40"
                  }`}
                >
                  <div className="flex items-center gap-2 truncate">
                    <FileText
                      className={`h-3.5 w-3.5 ${page.id === activePageId ? "text-zinc-400" : "text-zinc-600"}`}
                    />
                    <span className="truncate">{page.name}</span>
                  </div>
                  {page.id === activePageId && (
                    <Check className="h-3 w-3 text-zinc-400 shrink-0" />
                  )}
                </button>
              ))}
            </div>

            <div className="h-[1px] bg-zinc-900 my-4" />

            {/* Data Pipeline Asset Sources Visual Stack */}
            <div className="space-y-2 px-2">
              <span className="text-[10px] font-bold uppercase tracking-widest text-zinc-500 block">
                Active Pipelines
              </span>
              <div className="space-y-1">
                {dataSources.map((source) => (
                  <div
                    key={source.id}
                    className="flex items-center gap-2 text-[11px] text-zinc-400 bg-zinc-900/20 border border-zinc-900 rounded p-1.5 px-2 font-mono"
                  >
                    <span className="h-1.5 w-1.5 rounded-full bg-emerald-500 animate-pulse" />
                    <span className="truncate">{source.name}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* Sidebar System Telemetry Footer */}
          <div className="p-3 border-t border-zinc-900 bg-zinc-950/40 text-[10px] text-zinc-500 font-mono flex items-center justify-between">
            <span>Cluster: Node_0x4</span>
            <span className="text-emerald-500 flex items-center gap-1">
              <span className="h-1 w-1 bg-emerald-500 rounded-full" /> Connected
            </span>
          </div>
        </aside>

        {/* 3. The Infinite Viewport Stage Canvas Block */}
        <main className="flex-1 bg-zinc-900/20 relative overflow-hidden cursor-crosshair">
          {/* Subtle Viewport Blueprint Orientation Origin Coordinates Indicator */}
          <div className="absolute top-4 left-4 z-30 pointer-events-none bg-zinc-950/80 border border-zinc-900 rounded px-2 py-1 text-[9px] text-zinc-500 font-mono">
            PAGE_REF: {activePage.id}
            {/* // AXIS_X: 0px // AXIS_Y: 0px */}
          </div>

          {/* Bound Frame for Panzoom Transform Loops */}
          <div
            ref={canvasAreaRef}
            className="w-[10000px] h-[10000px] absolute top-0 left-0 bg-[radial-gradient(#27272a_1px,transparent_1px)] [background-size:16px_16px] origin-top-left"
            style={{ top: "-4500px", left: "-4500px" }} // Centering workspace frame geometry natively on target view
          >
            {/* Dynamic Rendering Matrix corresponding directly to active contextual page state configuration */}
            <div className="absolute top-[5000px] left-[5000px] translate-x-[-50%] translate-y-[-50%] p-12 min-w-[1200px] min-h-[800px] border border-dashed border-zinc-800/80 rounded-xl relative">
              <span className="absolute -top-6 left-0 text-[10px] font-mono text-zinc-600 uppercase tracking-wider">
                {activePage.name} Canvas Field Grid
              </span>

              {/* Dynamic Workspace Elements Mock Block Content Frame */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 p-6">
                {activePage.elements.length === 0 ? (
                  <div className="col-span-2 h-64 border border-zinc-900 bg-zinc-950/40 rounded-xl flex flex-col items-center justify-center p-8 text-center border-dashed">
                    <LineChart className="h-6 w-6 text-zinc-600 mb-2" />
                    <p className="text-xs text-zinc-400 font-medium">
                      Empty Canvas Node Frame
                    </p>
                    <p className="text-[11px] text-zinc-500 mt-0.5 max-w-xs font-light">
                      Use the top generative AI prompt bar or inject connected
                      data pipelines into the sidebar layout matrix to construct
                      widgets.
                    </p>
                  </div>
                ) : (
                  activePage.elements.map((widget: string) => (
                    <div
                      key={widget}
                      className="h-64 border border-zinc-900 bg-zinc-950 p-4 rounded-xl flex flex-col justify-between hover:border-zinc-800 transition-colors cursor-default"
                      onClick={(e) => e.stopPropagation()} // Block infinite background mouse selection drag bubbles
                    >
                      <div className="flex items-center justify-between text-xs text-zinc-400 font-mono">
                        <span>{`[widget::${widget}]`}</span>
                        <span className="text-[10px] text-emerald-500 bg-emerald-500/5 border border-emerald-500/20 px-1 rounded">
                          STREAM_OK
                        </span>
                      </div>

                      {/* Geometric Mock Analytics Bars Graph Matrix layout line */}
                      <div className="flex-1 flex items-end gap-3 px-6 py-4">
                        <div className="bg-zinc-800 h-1/2 w-full rounded-sm" />
                        <div className="bg-zinc-700 h-4/5 w-full rounded-sm" />
                        <div className="bg-zinc-50 h-2/3 w-full rounded-sm" />
                        <div className="bg-zinc-800 h-full w-full rounded-sm" />
                      </div>

                      <div className="text-[11px] text-zinc-500 font-sans flex justify-between items-center border-t border-zinc-900/60 pt-2">
                        <span>Operational Telemetry Dashboard Metrics</span>
                        <span className="text-[10px] text-zinc-400 underline cursor-pointer hover:text-zinc-50">
                          Refine parameters
                        </span>
                      </div>
                    </div>
                  ))
                )}
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}
