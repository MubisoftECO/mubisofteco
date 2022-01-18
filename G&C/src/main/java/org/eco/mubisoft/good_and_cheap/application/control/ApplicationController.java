package org.eco.mubisoft.good_and_cheap.application.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p><b>Application Controller</b></p>
 * <p>Controller for the main application. Its duty is to get the user to the index page whenever they search
 * for the applications' domain.</p>
 */
@Controller
public class ApplicationController {

    /**
     * <p><b>GET INDEX</b></p>
     * <p>Get the route of the main html file of the application at the base route.</p>
     * @return The name of the html file.
     */
    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("pageTitle", "index");
        return "index";
    }

}
