package com.biswasakashdev.nexussphere.core.dtos.response;

public record SessionDetails(
        String token,
        long maxAge // Token valid in seconds.
) {
}
