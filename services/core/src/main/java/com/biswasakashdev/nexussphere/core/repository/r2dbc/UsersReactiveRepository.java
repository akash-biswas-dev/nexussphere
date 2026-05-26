package com.biswasakashdev.nexussphere.core.repository.r2dbc;

import com.biswasakashdev.nexussphere.core.models.Users;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersReactiveRepository extends ReactiveCrudRepository<Users, String> {

    Mono<Users> findByEmailIgnoreCase(String email);

    Mono<Boolean> existsByEmailIgnoreCase(String email);
}
