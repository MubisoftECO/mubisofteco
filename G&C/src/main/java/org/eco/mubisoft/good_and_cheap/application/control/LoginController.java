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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/sign-in")
    public String getLoginForm(HttpServletResponse response) throws IOException {
        log.info("Request login form");
        return "/login/login_form";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Login user out");
        HttpSession session = request.getSession();
        session.removeAttribute("accessToken");
        session.removeAttribute("refreshToken");
        response.sendRedirect("/");
    }

}
