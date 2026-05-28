export interface Workspace {
  id: string;
  name: string;
  ownedBy: string;
  lastActive: string;
  joinedOn: string;
  userCount: number;
  pageCount: number;
}

export interface WorkspaceResponse {
  workspaces: Workspace[];
  nextPage: number | null;
}
