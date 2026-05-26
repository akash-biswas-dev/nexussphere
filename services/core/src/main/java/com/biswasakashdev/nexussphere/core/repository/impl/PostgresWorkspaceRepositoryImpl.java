package com.biswasakashdev.nexussphere.core.repository.impl;

import com.biswasakashdev.nexussphere.core.models.Workspaces;
import com.biswasakashdev.nexussphere.core.repository.WorkspaceRepository;
import com.biswasakashdev.nexussphere.core.repository.r2dbc.WorkspaceReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostgresWorkspaceRepositoryImpl implements WorkspaceRepository {

    private final WorkspaceReactiveRepository workspaceReactiveRepository;

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @Override
    public Mono<Workspaces> save(Workspaces workspaces) {
        String id = UUID.randomUUID().toString();
        workspaces.setId(id);
        return r2dbcEntityTemplate
                .insert(Workspaces.class)
                .using(workspaces);
    }

    @Override
    public Mono<Workspaces> findById(String id) {
        return workspaceReactiveRepository.findById(id);
    }

    @Override
    public Mono<Boolean> existsByName(String name, String userId) {
        return workspaceReactiveRepository
                .existsByNameAndOwnedId(name, userId);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return workspaceReactiveRepository
                .deleteById(id);
    }
}
