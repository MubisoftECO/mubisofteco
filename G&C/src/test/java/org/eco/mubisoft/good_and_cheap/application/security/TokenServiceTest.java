package org.eco.mubisoft.good_and_cheap.application.security;

import org.eco.mubisoft.good_and_cheap.application.data.MilliTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TokenServiceTest {

    private final TokenService tokenService = new TokenService();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void tokenOperations() {
        assertNotNull(passwordEncoder);
        User user = new User(
                "test.user", "test.password",
                Collections.singletonList(
                        new SimpleGrantedAuthority("TEST_ROLE")
                ));

        String token = generateToken(user);

        // Get basic user elements from the token.
        testGetUsernameFromToken(user, token);
        testGetUserFromToken(token);
        testGetRolesFromToken(token);

        // Get UserPasswordToken from the token.
        testGetUsernamePasswordToken(token);
    }

    private String generateToken(User user) {
        // Test getting username from the token.
        String token = "Bearer " + tokenService.generateToken(user, "/test", MilliTime.TEN_MINUTES.getTime());
        assertNotNull(token);
        return token;
    }

    private void testGetUsernamePasswordToken(String token) {
        // Test getting a UsernamePasswordAuthenticationToken object from the token.
        assertNotNull(tokenService.getUserPasswordToken(token));
    }

    private void testGetUsernameFromToken(User user, String token) {
        // Test getting data from the token.
        assertEquals(
                user.getUsername(), tokenService.getUsernameFromToken(token)
        );
    }

    private void testGetUserFromToken(String token) {
        // Test getting the user from the token.
        User nonPasswordUser = new User(
                "test.user", "null",
                Collections.singletonList(new SimpleGrantedAuthority("TEST_ROLE"))
        );
        assertEquals(
                nonPasswordUser, tokenService.getUserFromToken(token)
        );
    }

    private void testGetRolesFromToken(String token) {
        // Test getting the user from the token.
        assertEquals(
                Collections.singletonList("TEST_ROLE"),
                tokenService.getRolesFromToken(token)
        );
    }

    @Test
    void setTokenOnSession() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpSession session = Objects.requireNonNull(request.getSession());

        tokenService.setTokenOnSession("access-token-mock", "refresh-token-mock", session);
        assertEquals("Bearer access-token-mock", session.getAttribute("accessToken"));
        assertEquals("Bearer refresh-token-mock", session.getAttribute("refreshToken"));
    }

}