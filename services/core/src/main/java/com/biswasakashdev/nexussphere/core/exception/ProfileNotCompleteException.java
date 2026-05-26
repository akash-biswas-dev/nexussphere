package com.biswasakashdev.nexussphere.core.exception;

import lombok.Getter;

@Getter
public class ProfileNotCompleteException extends RuntimeException {
    private final String userId;
    public ProfileNotCompleteException(String userId) {
        super();
        this.userId = userId;
    }

}
