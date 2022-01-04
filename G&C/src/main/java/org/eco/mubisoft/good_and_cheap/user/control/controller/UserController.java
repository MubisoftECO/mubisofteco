package org.eco.mubisoft.good_and_cheap.user.control.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.data.FileUploadUtil;
import org.eco.mubisoft.good_and_cheap.application.data.HibernateProxyTypeAdapter;
import org.eco.mubisoft.good_and_cheap.application.security.TokenChecker;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.City;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AutonomousCommunityService autonomousCommunityService;
    private final ProvinceService provinceService;
    private final CityService cityService;
    private final LocationService locationService;

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("pageTitle", "user_form");
        model.addAttribute("acList", autonomousCommunityService.getAllAutonomousCommunities());
        return "user/user_form";
    }

    @GetMapping("/create/getProvince/{acId}")
    public @ResponseBody String getProvincesByAutonomousCommunity (@PathVariable("acId") Long acId){
        Gson gson = new Gson();
        return gson.toJson(
                provinceService.getProvincesByAutonomousCommunity(
                        autonomousCommunityService.getAutonomousCommunity(acId)
                )
        );
    }

    @GetMapping("/create/getCity/{provinceId}")
    public @ResponseBody String getCitiesByProvince (@PathVariable("provinceId") Long provinceId){
        List<City> cities = cityService.getCitiesByProvince(provinceService.getProvince(provinceId));
        log.warn("La provincia que buscas tiene {} ciudades", cities.size());
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        Gson gson = b.create();
        return gson.toJson(cities);
    }

    @PostMapping("/save")
    public void saveUser(HttpServletRequest request, HttpServletResponse response, @RequestParam("image") MultipartFile imageFile) throws IOException {
        AppUser user = new AppUser();

        user.setName(request.getParameter("name"));
        user.setSecondName(request.getParameter("secondName"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(passwordEncoder.encode(
                request.getParameter("password")
        ));

        Location locationToSave = new Location();
        locationToSave.setCity(cityService.getCity(Long.parseLong(request.getParameter("city"))));
        locationToSave.setProvince(provinceService.getProvince(Long.parseLong(request.getParameter("province"))));
        locationToSave.setAutonomousCommunity(
                autonomousCommunityService.getAutonomousCommunity(
                        Long.parseLong(request.getParameter("autonomousCommunity"))));
        Location savedLocation = locationService.saveLocation(locationToSave);

        user.setLocation(savedLocation);
        roleService.setUserRole(user.getUsername(), "ROLE_USER");

        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        user.setImgSrc(fileName);

        AppUser savedUser = userService.saveUser(user);

        String uploadDir = "user-photos/" + savedUser.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, imageFile);

        // Send response
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.sendRedirect(response.encodeRedirectURL("/login/sign-in/"));
    }

    @GetMapping("/view")
    public String getView(Model model) {
        List<AppUser> userList = userService.getAllUsers();
        //model.addAttribute("userList", userList);
        model.addAttribute("roleList", roleService.getAllRoles());
        return "user/user_list";
    }

    @GetMapping("/view/getUsers/{roleId}")
    public @ResponseBody String getUsersByRole (@PathVariable("roleId") Long roleId){
        List<AppUser> users;
        if(roleId == -1){
            users = userService.getAllUsers();
        } else {
            users = userService.getUsersByRole(roleService.getRole(roleId));
        }
        Gson gson = new Gson();
        return gson.toJson(users);
    }

    @GetMapping("/info")
    public String getUser(Model model, HttpServletRequest request) {
        model.addAttribute("user", getLoggedUser(request));
        return "user/user_view";
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
        model.addAttribute("acList", autonomousCommunityService.getAllAutonomousCommunities());
        model.addAttribute("provinceList", provinceService.getProvincesByAutonomousCommunity(
                autonomousCommunityService.getAutonomousCommunity(
                        loggedUser.getLocation().getAutonomousCommunity().getId())
        ));
        model.addAttribute("cityList", cityService.getCitiesByProvince(
                provinceService.getProvince(loggedUser.getLocation().getProvince().getId())
        ));
        model.addAttribute("user", loggedUser);
        return "user/user_edit";
    }

    @GetMapping("/changepassword")
    public String editPassword(){
        //igual hay que poner un @PreAutorized con los roles
        return "user/user_password_edit";
    }

    @PostMapping("/changepassword")
    public String updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        return "user/user_view";
    }

    @PostMapping("/update")
    public String updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenChecker tokenChecker = new TokenChecker();
        String username = tokenChecker.getUsernameFromToken(accessToken);
        AppUser user = userService.getUser(username);

        Location locationToSave = new Location();
        locationToSave.setCity(cityService.getCity(Long.parseLong(request.getParameter("city"))));
        locationToSave.setProvince(provinceService.getProvince(Long.parseLong(request.getParameter("province"))));
        locationToSave.setAutonomousCommunity(
                autonomousCommunityService.getAutonomousCommunity(
                        Long.parseLong(request.getParameter("autonomousCommunity"))));
        userService.updateUser(user.getId(), request.getParameter("name"), request.getParameter("secondName"),
                request.getParameter("username"), locationToSave);

        response.setStatus(HttpServletResponse.SC_OK);
        return "user/user_view";
    }

    private AppUser getLoggedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenChecker tokenChecker = new TokenChecker();
        String username = tokenChecker.getUsernameFromToken(accessToken);
        return userService.getUser(username);
    }

}
