package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.models.Workspaces;
import reactor.core.publisher.Mono;

public interface WorkspaceRepository {

    Mono<Workspaces> save(Workspaces workspaces);

    Mono<Workspaces> findById(String id);

    Mono<Boolean> existsByName(String name, String userId);

    Mono<Void> deleteById(String id);

}
