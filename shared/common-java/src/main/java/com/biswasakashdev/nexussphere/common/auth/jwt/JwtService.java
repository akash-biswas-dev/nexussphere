package com.biswasakashdev.nexussphere.common.auth.jwt;


import com.biswasakashdev.nexussphere.common.auth.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import java.time.Duration;
import java.util.Map;

public interface JwtService {

    String buildToken(String userId, Duration expiry, TokenType tokenType, Map<String,Object> extraClaims);

    String getUserId(String token) throws ExpiredJwtException, MalformedJwtException;

    Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException;

    <T> T extractClaim(String token, String key, Class<T> type);
}
