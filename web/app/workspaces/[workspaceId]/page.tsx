import WorkspaceCanvasEngine from "@/components/workspace/workspace-canvas";

export default async function WorkSpacePage({
  params,
}: {
  params: Promise<{ workspaceId: string }>;
}) {
  const { workspaceId } = await params;

  return <WorkspaceCanvasEngine workspaceId={workspaceId} />;
}
