package ru.itis.sem_1.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.sem_1.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@AllArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {
    private final String AUTH_URL = "/auth";

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(AUTH_URL)){
            filterChain.doFilter(request, response);
        } else {
            if (tokenService.hasAuthorizationToken(request)) {
                String token = tokenService.getToken(request);
                try {
                    Authentication authentication = tokenService.buildAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
