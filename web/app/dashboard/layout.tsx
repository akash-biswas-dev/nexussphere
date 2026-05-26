import { UserContextProvider } from "@/context/user.context";

export default function DashBoardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <main>
      <UserContextProvider>{children}</UserContextProvider>
    </main>
  );
}
