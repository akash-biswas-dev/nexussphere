export interface DataAssetSource {
  id: string;
  name: string;
  type: string;
}

export interface CanvasPage {
  id: string;
  name: string;
  elements: any[]; // UI metrics, widgets, layouts
}

export interface WorkspaceState {
  id: string;
  name: string;
  activePageId: string;
  pages: CanvasPage[];
  dataSources: DataAssetSource[];
}
