package ru.itis.sem_1.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.model.User;
import ru.itis.sem_1.security.detail.UserDetailImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
public class TokenService {

    @Value("${default.secret}")
    private String secretKey;
    private final ObjectMapper objectMapper;
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String BEARER = "Bearer ";

    public TokenService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }


    public Map<String, String> buildToken(String subject, String authority, String issue){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String accessToken = JWT.create()
                .withSubject(subject)
                .withIssuer(issue)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*5))
                .withClaim("role", authority)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(subject)
                .withIssuer(issue)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*24*15))
                .withClaim("role", authority)
                .sign(algorithm);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("refreshToken", refreshToken);
        tokenMap.put("accessToken", accessToken);

        return tokenMap;
    }

    public Authentication buildAuthentication(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String email = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("role").asString();

        return new UsernamePasswordAuthenticationToken(new UserDetailImpl(
                User.builder()
                        .email(email)
                        .role(User.Role.valueOf(role))
                        .build()
        ),
                null,
                Collections.singleton(new SimpleGrantedAuthority(role)));
    }


    public boolean hasAuthorizationToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return header != null && header.startsWith(BEARER);
    }

    public boolean hasSessionAuthorizationToken(HttpServletRequest request) {
        if (request.getCookies() == null) return false;
        return Arrays.stream(request.getCookies()).anyMatch((cookie) -> cookie.getName().equals("refresh"));
    }

    public String getToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        return authorizationHeader.substring(BEARER.length());
    }

    public String getSessionToken(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("refresh"))
                .map(Cookie::getValue)
                .findFirst().get();
    }

    public boolean hasAuthorizationBody(HttpServletRequest request){
        if(request.getHeader("Content-type") == null
                || !request.getHeader("Content-type").equals("application/json")) return false;
        return true;
    }

    public UserSignRequest getAccountRequest(HttpServletRequest request){
        try {
            return objectMapper.readValue(request.getReader(), UserSignRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
