package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.models.User;
import reactor.core.publisher.Mono;


public interface UsersRepository {

    Mono<User> saveUser(User user);
}
