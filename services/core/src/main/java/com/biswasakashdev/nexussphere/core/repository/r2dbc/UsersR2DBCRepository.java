package com.biswasakashdev.nexussphere.core.repository.r2dbc;

import com.biswasakashdev.nexussphere.core.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersR2DBCRepository extends ReactiveCrudRepository<User, String> {

    Mono<User> findByEmailIgnoreCase(String email);

    Mono<Boolean> existsByEmailIgnoreCase(String email);
}
