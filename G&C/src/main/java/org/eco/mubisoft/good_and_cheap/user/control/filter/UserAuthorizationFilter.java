package org.eco.mubisoft.good_and_cheap.user.control.filter;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.data.MilliTime;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class UserAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().startsWith("/role") ||
            request.getServletPath().startsWith("/user/view") ||
            request.getServletPath().startsWith("/user/delete") ||
                request.getServletPath().startsWith("/recipe/create")) {

            HttpSession session = request.getSession();
            TokenService tokenService = new TokenService();
            String accessToken = (String) session.getAttribute("accessToken");

            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                try {
                    UsernamePasswordAuthenticationToken authToken = tokenService.getUserPasswordToken(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } catch (Exception accessException) {
                    log.info("Access token not found, trying to refresh");
                    String refreshToken = (String) session.getAttribute("refreshToken");

                    try {
                        UsernamePasswordAuthenticationToken authToken = tokenService.getUserPasswordToken(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        log.info("Refresh token found, refreshing access token.");

                        // Generate a new access and refresh token for the user.
                        User user = tokenService.getUserFromToken(refreshToken);
                        tokenService.setTokenOnSession(
                                tokenService.generateToken(user, request.getRequestURL().toString(), MilliTime.FIVE_MINUTES.time),
                                tokenService.generateToken(user, request.getRequestURL().toString(), MilliTime.SIX_HOUR.time),
                                request.getSession()
                        );
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
