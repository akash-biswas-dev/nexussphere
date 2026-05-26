package com.biswasakashdev.nexussphere.core.config;


import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class JwtConfig {
    private final Environment environment;

    @Bean
    JwtService jwtService (){
        String secret = environment.getProperty("jwt.secret");
        String issuer = environment.getProperty("jwt.issuer");
        if(secret == null  ||  issuer == null){
            throw new IllegalStateException("Jwt properties cant be null");
        }
        return new JwtServiceImpl(
                secret,
                issuer
        );
    }
}
