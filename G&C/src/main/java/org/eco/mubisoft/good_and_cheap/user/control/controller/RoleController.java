package org.eco.mubisoft.good_and_cheap.user.control.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.eco.mubisoft.good_and_cheap.user.domain.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/create")
    public String createRole() {
        return "role/role_form";
    }

    @PostMapping("/save")
    public void saveRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        roleService.saveRole(new Role(null, request.getParameter("name")));
        response.sendRedirect("/role/view");
    }

    @GetMapping("/set")
    public String setRoleForm() {
        return "/role/set_form";
    }

    @PostMapping("/set")
    public void setUserRole(HttpServletRequest request, HttpServletResponse response) throws IOException {
        roleService.setUserRole(
                request.getParameter("username"),
                request.getParameter("name")
        );
        response.sendRedirect("/");
    }

    @GetMapping("/view")
    public List<Role> getUser() {
        return roleService.getAllRoles();
    }

}
