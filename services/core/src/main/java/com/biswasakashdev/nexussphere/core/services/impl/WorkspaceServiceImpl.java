package com.biswasakashdev.nexussphere.core.services.impl;

import com.biswasakashdev.nexussphere.common.exceptions.DataSourceOperationFailedException;
import com.biswasakashdev.nexussphere.common.response.Page;
import com.biswasakashdev.nexussphere.core.dtos.response.UsersOnWorkspaceDTO;
import com.biswasakashdev.nexussphere.core.dtos.response.WorkspaceResponse;
import com.biswasakashdev.nexussphere.core.models.UsersOnWorkspace;
import com.biswasakashdev.nexussphere.core.models.Workspaces;
import com.biswasakashdev.nexussphere.core.repository.UsersOnWorkspaceRepository;
import com.biswasakashdev.nexussphere.core.repository.WorkspaceRepository;
import com.biswasakashdev.nexussphere.core.services.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UsersOnWorkspaceRepository usersOnWorkspaceRepository;

    @Override
    @Transactional
    public Mono<Workspaces> createWorkspace(
            String userId
    ) {
        Workspaces workspaces = Workspaces.builder()
                .name("Untitled Workspace")
                .ownedId(userId)
                .createdOn(LocalDate.now())
                .build();

        return workspaceRepository
                .save(workspaces)
                .flatMap((savedWorkspace) -> {

                    UsersOnWorkspace usersOnWorkspace =
                            UsersOnWorkspace.builder()
                                    .userId(userId)
                                    .workspaceId(savedWorkspace.getId())
                                    .lastActive(LocalDateTime.now())
                                    .joinedOn(LocalDate.now())
                                    .writePermission(true)
                                    .build();

                    return usersOnWorkspaceRepository.save(usersOnWorkspace)
                            .then(Mono.just(savedWorkspace));
                })
                .onErrorResume(DataSourceOperationFailedException.class, (err) ->
                        Mono.error(new RuntimeException("Failed to create workspaces."))
                );
    }

    @Override
    public Mono<Boolean> isWorkspaceNameExists(String workspaceId, String userId) {
        return workspaceRepository
                .existsByName(workspaceId, userId);
    }

    @Override
    public Mono<Page<UsersOnWorkspaceDTO>> findAllUsersInWorkspace(
            String workspaceId,
            Page.PageDetails pageDetails
    ) {

        return usersOnWorkspaceRepository
                .findAllUsersByWorkspaceId(workspaceId, pageDetails);
    }

    @Override
    public Mono<Page<WorkspaceResponse>> findAllWorkspace(
            String userId,
            Page.PageDetails pageDetails
    ) {
        return usersOnWorkspaceRepository
                .findAllWorkspacesByUserId(userId, pageDetails);
    }
}
