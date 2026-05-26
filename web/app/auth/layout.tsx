"use client";
import { Navbar } from "@/components/auth/navbar";

export default function AuthLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <main className="min-h-screen w-screen flex items-center relative">
      <Navbar />
      {children}
    </main>
  );
}
