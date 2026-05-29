package com.biswasakashdev.nexussphere.core.services;

import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.dtos.response.UsersOnWorkspaceDTO;
import com.biswasakashdev.nexussphere.core.dtos.response.WorkspaceResponse;
import com.biswasakashdev.nexussphere.core.models.Workspaces;
import reactor.core.publisher.Mono;

public interface WorkspaceService {

    Mono<Workspaces> createWorkspace(String userId);

    Mono<Boolean> isWorkspaceNameExists(String userId, String workspaceName);

    Mono<Page<UsersOnWorkspaceDTO>> findAllUsersInWorkspace(String workspaceId, Page.PageDetails pageDetails);

    Mono<Page<WorkspaceResponse>> findAllWorkspace(String userId, Page.PageDetails pageDetails);
}
