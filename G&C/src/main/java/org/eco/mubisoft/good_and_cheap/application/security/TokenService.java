package org.eco.mubisoft.good_and_cheap.application.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.data.MilliTime;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
public class TokenService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public TokenService() {
        algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
        verifier = JWT.require(algorithm).build();
    }

    public String generateToken(User user, String requestURL, int durationTime) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + durationTime))
                .withIssuer(requestURL)
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public void setTokenOnSession(String accessToken, String refreshToken, HttpSession session) {
        session.setAttribute("accessToken", "Bearer " + accessToken);
        session.setAttribute("refreshToken", "Bearer " + refreshToken);
        log.info("AccessToken: {} | RefreshToken: {}", accessToken, refreshToken);
    }

    public UsernamePasswordAuthenticationToken getUserPasswordToken(String authToken) throws JWTVerificationException {
        String token = authToken.substring("Bearer ".length());
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
    }

    public User getUserFromToken(String authToken) {
        String token = authToken.substring("Bearer ".length());
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        Collection<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new User(username, "null", authorities);
    }

    public String getUsernameFromToken (String authToken) {
        try {
            // 1. Get the token.
            String token = authToken.substring("Bearer ".length());

            // 2. Decode the token.
            DecodedJWT decodedJWT = verifier.verify(token);

            // 3. Return the subject of the token.
            return decodedJWT.getSubject();
        } catch (JWTVerificationException | NullPointerException e) {
            return null;
        }
    }

    public Collection<String> getRolesFromToken(String authToken) throws JWTVerificationException{
        String token = authToken.substring("Bearer ".length());
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("roles").asList(String.class);
    }

}
