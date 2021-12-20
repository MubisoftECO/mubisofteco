package org.eco.mubisoft.good_and_cheap.user.control.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.security.TokenChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

@Slf4j
public class UserAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getServletPath().equals("/") &&
            !request.getServletPath().startsWith("/login") &&
            !request.getServletPath().equals("/user/create") &&
            !request.getServletPath().equals("/user/save")) {

            HttpSession session = request.getSession();
            TokenChecker tokenChecker = new TokenChecker();
            String accessToken = (String) session.getAttribute("accessToken");

            log.info(accessToken);

            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                try {
                    UsernamePasswordAuthenticationToken authToken = tokenChecker.getUserPasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception accessException) {
                    log.info("Access token not found, redirecting to refresh");
                    String refreshToken = (String) session.getAttribute("refreshToken");

                    try {
                        UsernamePasswordAuthenticationToken authToken = tokenChecker.getUserPasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } catch (Exception refreshException) {
                        response.setHeader("error", refreshException.getMessage());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
