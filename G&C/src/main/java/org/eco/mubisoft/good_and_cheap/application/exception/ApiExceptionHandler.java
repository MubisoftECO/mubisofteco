package org.eco.mubisoft.good_and_cheap.application.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.NestedServletException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
public class ApiExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", status);

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
            else {
                // Get attributes
                NestedServletException exception = (NestedServletException) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

                // Set error attributes
                model.addAttribute("status", status.toString());
                model.addAttribute("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("error", exception.getMessage());
                model.addAttribute("trace", Arrays.toString(exception.getStackTrace()));
            }
        }
        return "/problem/error_base";
    }

}
