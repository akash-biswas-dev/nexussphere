package com.biswasakashdev.nexussphere.core.services;

import com.biswasakashdev.nexussphere.core.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.core.models.Users;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Users> validateUser(UserCredentials userCredentials);
}
