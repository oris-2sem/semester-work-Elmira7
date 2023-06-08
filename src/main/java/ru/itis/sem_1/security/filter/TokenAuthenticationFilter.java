package ru.itis.sem_1.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.security.authentication.RefreshTokenAuthentication;
import ru.itis.sem_1.security.detail.UserDetailImpl;
import ru.itis.sem_1.service.TokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Component
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final String AUTH_URL = "/auth";
    private final ObjectMapper objectMapper;

    private final TokenService tokenService;


    public TokenAuthenticationFilter(ObjectMapper objectMapper,
                                     AuthenticationConfiguration authenticationConfiguration,
                                     TokenService tokenService) throws Exception {
        super(authenticationConfiguration.getAuthenticationManager());
        super.setFilterProcessesUrl(AUTH_URL);
        super.setUsernameParameter("email");
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (tokenService.hasSessionAuthorizationToken(request)){
            String refreshToken = tokenService.getSessionToken(request);
            RefreshTokenAuthentication tokenAuthentication = new RefreshTokenAuthentication(refreshToken);
            return super.getAuthenticationManager().authenticate(tokenAuthentication);
        }
        else if (tokenService.hasAuthorizationBody(request)){
            UserSignRequest accountRequest = tokenService.getAccountRequest(request);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(accountRequest.getEmail(), accountRequest.getPassword());
            return super.getAuthenticationManager().authenticate(authentication);
        }
        else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        response.setContentType("application/json");

        String username = ((UserDetailImpl)authResult.getPrincipal()).getUsername();
        String role = authResult.getAuthorities().stream().findFirst().orElseThrow().getAuthority();
        String issuer = request.getRequestURL().toString();

        Map<String, String> tokens = tokenService.buildToken(username, role, issuer);

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        Cookie cookie = new Cookie("refresh", tokens.get("refreshToken"));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*24*30);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        Cookie cookie = new Cookie("refresh", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
