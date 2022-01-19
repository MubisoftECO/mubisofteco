package org.eco.mubisoft.good_and_cheap.application.exception;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ApiExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            // Get the error code.
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "/problem/error_403";
            }
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/problem/error_404";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "/problem/error_500";
            }
        }
        return "/problem/error_403";
    }

}
