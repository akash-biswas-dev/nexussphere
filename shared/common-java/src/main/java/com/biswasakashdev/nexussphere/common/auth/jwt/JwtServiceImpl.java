package com.biswasakashdev.nexussphere.common.auth.jwt;

import com.biswasakashdev.nexussphere.common.auth.TokenType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtServiceImpl implements JwtService {

    //    In minutes.
    private static final int DEFAULT_EXPIRY_IN_MINUTES = 1440;

    //    Jwt secret.
    private final String secret;
    private final String issuer;
    private final Duration expiry;


    public JwtServiceImpl(
            String secret,
            String issuer) {
        this.secret = secret;
        this.issuer = issuer;
        this.expiry = Duration.ofMinutes(DEFAULT_EXPIRY_IN_MINUTES);
    }

    //    Constructor to create a JwtService with custom authorization expiry.
    public JwtServiceImpl(
            String secret,
            String issuer,
            Duration duration) {
        this.secret = secret;
        this.issuer = issuer;
        this.expiry = duration;
    }


    private String createToken(String id, long windowSize, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + windowSize))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String buildToken(String userId, Duration expiry, TokenType tokenType, Map<String, Object> extraClaims) {
        Map<String , Object> claims = new HashMap<>(extraClaims);
        claims.put("token_type", tokenType.name());
        return createToken(userId, expiry.toMillis(), claims);
    }


    @Override
    public String getUserId(String token) throws ExpiredJwtException, MalformedJwtException {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T extractClaim(String token, String key, Class<T> type) {
        final Claims allClaims = extractAllClaims(token);
        Function<Claims, T> claimsResolvers = (claims) -> claims.get(key, type);
        return claimsResolvers.apply(allClaims);
    }
}
