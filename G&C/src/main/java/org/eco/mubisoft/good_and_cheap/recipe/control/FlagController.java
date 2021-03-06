package org.eco.mubisoft.good_and_cheap.recipe.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.FlagService;
import org.springframework.http.ResponseEntity;
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
        log.info("Sending to flag form");
        return "/recipe/flags_form";
    }

    @PostMapping("/save")
    public ResponseEntity<Long> setRecipeFlag(HttpServletRequest request) {
        String flagName = request.getParameter("name");
        return ResponseEntity.ok().body(
                flagService.saveFlag(
                        new Flag (
                                flagName,
                                flagName,
                                flagName
                        )
                ).getId()
        );
    }

}
