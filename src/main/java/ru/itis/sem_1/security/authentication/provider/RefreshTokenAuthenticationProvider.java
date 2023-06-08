package ru.itis.sem_1.security.authentication.provider;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.sem_1.security.authentication.RefreshTokenAuthentication;
import ru.itis.sem_1.service.TokenService;


@Component
@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private final TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshToken = authentication.getCredentials().toString();
        try {
            return tokenService.buildAuthentication(refreshToken);
        } catch (JWTVerificationException e){
            throw new AccountExpiredException("");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthentication.class.isAssignableFrom(authentication);
    }
}
