package org.eco.mubisoft.good_and_cheap.user.control.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.RoleService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("pageTitle", "user_form");
        return "user/user_form";
    }

    @PostMapping("/save")
    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AppUser user = new AppUser();

        user.setName(request.getParameter("name"));
        user.setSecondName(request.getParameter("secondName"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(passwordEncoder.encode(
                request.getParameter("password")
        ));
        roleService.setUserRole(user.getUsername(), "ROLE_USER");
        userService.saveUser(user);

        // Send response
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.sendRedirect(response.encodeRedirectURL("/"));
    }

    @GetMapping("/view")
    public String getView() {
        return "user/user_list";
    }

    @GetMapping("/view/{userID}")
    public ResponseEntity<AppUser> getUser(@PathVariable("userID") Long id) {
        return ResponseEntity.created(
                URI.create(
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/view").toUriString()
                )
        ).body(userService.getUser(id));

    }

}
