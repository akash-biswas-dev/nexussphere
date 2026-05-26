package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.dtos.response.UsersOnWorkspaceDTO;
import com.biswasakashdev.nexussphere.core.dtos.response.WorkspaceResponse;
import com.biswasakashdev.nexussphere.core.models.UsersOnWorkspace;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UsersOnWorkspaceRepository {

    Mono<UsersOnWorkspace> save(UsersOnWorkspace usersOnWorkspace);

    Mono<Page<UsersOnWorkspaceDTO>> findAllUsersByWorkspaceId(String workspaceId, Page.PageDetails pageInfo);

    Mono<Page<WorkspaceResponse>> findAllWorkspacesByUserId(String userId, Page.PageDetails pageInfo);

}
