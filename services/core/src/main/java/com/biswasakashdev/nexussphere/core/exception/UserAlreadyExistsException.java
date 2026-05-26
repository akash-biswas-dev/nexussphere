package com.biswasakashdev.nexussphere.core.exception;


import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String email;
    public UserAlreadyExistsException(String email, String message) {
        super(message);
        this.email = email;
    }
}
