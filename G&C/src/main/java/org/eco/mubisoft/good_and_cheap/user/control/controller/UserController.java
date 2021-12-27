package org.eco.mubisoft.good_and_cheap.user.control.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.security.TokenChecker;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.RoleService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

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
    public String getView(Model model) {
        List<AppUser> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
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

    @GetMapping("/delete/{userID}")
    public void deleteUser (@PathVariable("userID") Long id, HttpServletResponse response) throws IOException {
        userService.deleteUser(userService.getUser(id));
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(response.encodeRedirectURL("/user/view/"));
    }

    @GetMapping("/edit")
    public String editUser(Model model, HttpServletRequest request, HttpServletResponse response){
        AppUser loggedUser = getLoggedUser(request);
        model.addAttribute("user", loggedUser);
        return "user/user_edit";
    }

    @GetMapping("/changepassword")
    public String editPassword(){
        //igual hay que poner un @PreAutorized con los roles
        return "user/user_password_edit";
    }

    @PostMapping("/changepassword")
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String old = request.getParameter("old");
        String newPassword = request.getParameter("new");
        String repeat = request.getParameter("repeat");

        AppUser user = getLoggedUser(request);

        if (!old.equals(newPassword) && newPassword.equals(repeat) && userService.checkPassword(user.getUsername(), old)){
            userService.updatePassword(user.getUsername(), newPassword);
            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/");
        } else{
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        //Mandar a la pantalla de user SIN IMPLEMENTAR
    }

    @PostMapping("/update")
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenChecker tokenChecker = new TokenChecker();
        String username = tokenChecker.getUsernameFromToken(accessToken);
        AppUser user = userService.getUser(username);
        userService.updateUser(user.getId(), request.getParameter("name"), request.getParameter("secondName"),
                request.getParameter("username"));

        response.setStatus(HttpServletResponse.SC_OK);
        //Mandar a pantalla usuario (NO IMPLEMENTADO)
        response.sendRedirect(response.encodeRedirectURL("/"));
    }

    private AppUser getLoggedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenChecker tokenChecker = new TokenChecker();
        String username = tokenChecker.getUsernameFromToken(accessToken);
        AppUser loggedUser = userService.getUser(username);
        return loggedUser;
    }

}
