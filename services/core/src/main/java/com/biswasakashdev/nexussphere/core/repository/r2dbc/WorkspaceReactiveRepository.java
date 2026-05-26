package com.biswasakashdev.nexussphere.core.repository.r2dbc;

import com.biswasakashdev.nexussphere.core.models.Workspaces;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface WorkspaceReactiveRepository extends ReactiveCrudRepository<Workspaces, String> {

    Mono<Boolean> existsByNameAndOwnedId(String name, String ownedId);
}
