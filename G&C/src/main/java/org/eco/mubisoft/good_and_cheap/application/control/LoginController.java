package org.eco.mubisoft.good_and_cheap.application.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p><b>Login Controller</b></p>
 * <p>Controller for the login operations. It's duty is to provide the different forms for logging in
 * into the application and logout the user from the application.</p>
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    /**
     * <p><b>GET LOGIN FORM</b></p>
     * <p>Get the route for the login page through the route "/login/sign-in".</p>
     * @return The route to the login page.
     */
    @GetMapping("/sign-in")
    public String getLoginForm() {
        log.info("Request login form");
        return "/login/login_form";
    }

    /**
     * <p><b>LOGOUT</b></p>
     * <p>Remove the user tokens from the session, logging the user out of the application.</p>
     * @param request HttpRequest to the server.
     * @param response HttpResponse from the server.
     * @throws IOException Response thrown if the server was not able to redirect the user to
     * another page.
     */
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Login user out");
        HttpSession session = request.getSession();
        session.removeAttribute("accessToken");
        session.removeAttribute("refreshToken");
        response.sendRedirect("/");
    }

}
