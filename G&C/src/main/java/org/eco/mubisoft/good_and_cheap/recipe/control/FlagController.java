package org.eco.mubisoft.good_and_cheap.recipe.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.FlagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/flag")
public class FlagController {

    private final FlagService flagService;

    @GetMapping("/create")
    public String setFlagForm() {
        return "/recipe/flags_form";
    }

    @PostMapping("/save")
    public void setRecipeFlag(HttpServletRequest request) {
        flagService.saveFlag(
                new Flag (
                        request.getParameter("name"),
                        request.getParameter("name"),
                        request.getParameter("name")
                )
        );
    }

}
