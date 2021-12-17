package org.eco.mubisoft.good_and_cheap.application.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping
    public String getIndex(Model model) {
        // Return the name of the html file.
        return "index";
    }

}
