"use client";

import { motion } from "framer-motion";
import {
  ArrowRight,
  Binary,
  Database,
  Download,
  Layers,
  LineChart,
  Sparkles,
  Users,
  Wand2,
} from "lucide-react";

import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import dynamic from "next/dynamic";
import { NexusLogo } from "@/components/nexus-logo";
import Link from "next/link";

// Dynamically import the 3D Canvas to prevent SSR canvas issues
const DataMesh = dynamic(() => import("@/components/home/data-mesh"), {
  ssr: false,
});

export default function NexusSphereHome() {
  // Animation presets for Framer Motion
  const fadeInUp = {
    initial: { opacity: 0, y: 30 },
    animate: { opacity: 1, y: 0 },
    transition: { duration: 0.6, ease: "easeOut" },
  };

  const staggerContainer = {
    animate: { transition: { staggerChildren: 0.1 } },
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-50 overflow-x-hidden relative selection:bg-zinc-800 selection:text-zinc-100">
      {/* Artboard Canvas Background Grid */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#18181b_1px,transparent_1px),linear-gradient(to_bottom,#18181b_1px,transparent_1px)] bg-size-[4rem_4rem] mask-[radial-gradient(ellipse_60%_50%_at_50%_0%,#000_70%,transparent_100%)] pointer-events-none" />

      {/* Navbar Placeholder */}
      <header className="border-b border-zinc-900 bg-zinc-950/80 backdrop-blur sticky top-0 z-50">
        <div className="container mx-auto max-w-7xl h-16 flex items-center justify-between px-4 sm:px-6 lg:px-8">
          <NexusLogo />
          <div className="flex items-center gap-4">
            <Link href={"/auth"}>
              <Button variant="ghost" className="text-zinc-400">
                Sign In
              </Button>
            </Link>

            <Link href={"/auth/sign-up"}>
              <Button className="bg-zinc-50 text-zinc-950 hover:bg-zinc-200">
                Create account
              </Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Hero Section */}
      <section className="container mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 pt-12 md:pt-20 pb-16">
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
          {/* Left Hero Texts */}
          <motion.div
            className="lg:col-span-7 space-y-6 text-left"
            initial="initial"
            animate="animate"
            variants={staggerContainer}
          >
            <motion.div variants={fadeInUp}>
              <Badge
                variant="outline"
                className="border-zinc-800 bg-zinc-900/50 text-zinc-400 px-3 py-1 text-xs tracking-wider uppercase"
              >
                <Sparkles className="h-3 w-3 mr-1.5 text-zinc-400 inline" />{" "}
                Multiplayer Data Canvas
              </Badge>
            </motion.div>

            <motion.h1
              className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight leading-tight lg:leading-none bg-clip-text text-transparent bg-linear-to-b from-zinc-50 to-zinc-400"
              variants={fadeInUp}
            >
              Collaborative business dashboards. Real-time engine.
            </motion.h1>

            <motion.p
              className="text-zinc-400 text-lg sm:text-xl max-w-2xl font-light leading-relaxed"
              variants={fadeInUp}
            >
              Design, structure, and deploy beautifully interactive dashboards
              side-by-side with your team. Connect static logs or streams
              directly into a Figma-style canvas powered by automated AI models.
            </motion.p>

            <motion.div
              className="flex flex-col sm:flex-row gap-4 pt-2"
              variants={fadeInUp}
            >
              <Button
                size="lg"
                className="bg-zinc-50 text-zinc-950 hover:bg-zinc-200 text-base font-medium px-8 group"
              >
                Start Building Together
                <ArrowRight className="ml-2 h-4 w-4 transition-transform group-hover:translate-x-1" />
              </Button>
              <Button
                size="lg"
                variant="outline"
                className="border-zinc-800 bg-foreground  font-medium px-8"
              >
                Watch Engine Demo
              </Button>
            </motion.div>
          </motion.div>

          {/* Right Hero Interactive 3D Canvas */}
          <motion.div
            className="lg:col-span-5 relative flex justify-center items-center"
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.8, ease: "easeOut", delay: 0.2 }}
          >
            <div className="absolute inset-0 bg-radial-gradient from-zinc-800/20 to-transparent blur-3xl pointer-events-none" />
            <DataMesh />
          </motion.div>
        </div>
      </section>

      {/* Feature Section Grid */}
      <section className="border-t border-zinc-900 bg-zinc-900/20 py-24 relative">
        <div className="container mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
          <div className="text-center max-w-3xl mx-auto mb-16 space-y-4">
            <h2 className="text-3xl sm:text-4xl font-bold tracking-tight">
              Engineered for Data-Driven Teams
            </h2>
            <p className="text-zinc-400 text-base sm:text-lg">
              A workspace running complex analytical algorithms completely under
              the hood while you manipulate your components visual properties
              layout-by-layout.
            </p>
          </div>

          {/* Features Cards Grid */}
          <motion.div
            className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
            initial="initial"
            whileInView="animate"
            viewport={{ once: true, margin: "-100px" }}
            variants={staggerContainer}
          >
            {/* Feature 1 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <Layers className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Multi-Page Architecture
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Organize massive enterprise operational workspaces across
                    deep nested sub-pages effortlessly.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>

            {/* Feature 2 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <Database className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Omni Data-Ingestion
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Stream live high-throughput websocket data sources or upload
                    localized CSV, sheets, and historical logs.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>

            {/* Feature 3 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <Users className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Multiplayer Collaboration
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Co-author charts, change telemetry metrics, and build
                    component styling layouts alongside your team in real-time.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>

            {/* Feature 4 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <Download className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Granular Asset Exporting
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Isolate full operational dashboards or specific chart
                    metrics and drop them into target builds via standardized
                    exports.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>

            {/* Feature 5 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <Wand2 className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Generative UI Components
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Missing a specific tracking widget? Describe it directly to
                    the embedded agent layer to synthesize UI parts instantly.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>

            {/* Feature 6 */}
            <motion.div variants={fadeInUp}>
              <Card className="bg-zinc-950 border-zinc-900 h-full hover:border-zinc-800 transition-colors">
                <CardHeader>
                  <LineChart className="h-6 w-6 text-zinc-400 mb-2" />
                  <CardTitle className="text-zinc-100 text-lg">
                    Prompt-to-Dashboard
                  </CardTitle>
                  <CardDescription className="text-zinc-400">
                    Input a raw prompt description to ingest your system logs
                    and deploy optimized visualizations automatically.
                  </CardDescription>
                </CardHeader>
              </Card>
            </motion.div>
          </motion.div>

          {/* Deep Analytics & Data Scientists Callout */}
          <motion.div
            className="mt-16 bg-zinc-950 border border-zinc-900 rounded-xl p-8 lg:p-12 grid grid-cols-1 lg:grid-cols-3 gap-8 items-center"
            initial={{ opacity: 0, y: 40 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            transition={{ duration: 0.6 }}
          >
            <div className="lg:col-span-2 space-y-4">
              <div className="flex items-center gap-2 text-zinc-400">
                <Binary className="h-5 w-5" />
                <span className="text-xs font-bold uppercase tracking-widest">
                  No-Code Data Science Core
                </span>
              </div>
              <h3 className="text-2xl sm:text-3xl font-bold tracking-tight">
                AI Data Scanner Asset
              </h3>
              <p className="text-zinc-400 max-w-2xl font-light">
                Scan anomalies, isolate mission-critical model parameters, and
                refine metrics instantly without writing complex Pandas, NumPy,
                or Matplotlib implementations. Complete slides and presentations
                generate natively from the underlying pipeline.
              </p>
            </div>
            <div className="flex lg:justify-end">
              <Button
                size="lg"
                className="w-full lg:w-auto bg-zinc-900 border border-zinc-800 text-zinc-50 hover:bg-zinc-800"
              >
                Connect Data Engine
              </Button>
            </div>
          </motion.div>
        </div>
      </section>

      {/* Footer */}
      <footer className="border-t border-zinc-900 py-8 text-center text-xs text-zinc-500">
        &copy; {new Date().getFullYear()} NexusSphere Inc. All rights reserved.
        Built with Shadcn System Tokens.
      </footer>
    </div>
  );
}
