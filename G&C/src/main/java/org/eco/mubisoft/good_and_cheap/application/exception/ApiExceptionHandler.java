package org.eco.mubisoft.good_and_cheap.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Controller
public class ApiExceptionHandler implements ErrorController {

    @RequestMapping("/error")
    public void handleError(Model model, RedirectAttributes redirectAttributes,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        redirectAttributes.addFlashAttribute("status", status);

        if (status != null) {
            // Get attributes
            Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

            // Set error attributes
            redirectAttributes.addFlashAttribute("status", status.toString());
            redirectAttributes.addFlashAttribute("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

            if (exception != null) {
                redirectAttributes.addFlashAttribute("error", exception.getMessage());
                redirectAttributes.addFlashAttribute("trace", Arrays.toString(exception.getStackTrace()));
            }

            // Get the error code.
            int statusCode = Integer.parseInt(status.toString());
            log.info("Error {} found", statusCode);

            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                response.sendRedirect("/problem/403");
            }
            else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                response.sendRedirect("/problem/404");
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                response.sendRedirect("/problem/500");
            }
            else {
                response.sendRedirect("/problem");
            }
        }
        else {
            log.info("Unhandled error found");
            response.sendRedirect("/problem");
        }
    }

    @GetMapping("/problem/{errorID}")
    public String getErrorPage(
            Model model,
            @PathVariable(name = "errorID", required = false) String errorID,
            @ModelAttribute("status") String status,
            @ModelAttribute("path") String path,
            @ModelAttribute("error") String error,
            @ModelAttribute("trace") String trace
    ) {
        String viewURL = "/problem/error_base";

        if (errorID != null) {
            if (status != null) model.addAttribute("status", status);
            if (path != null) model.addAttribute("path", path);
            if (error != null) model.addAttribute("error", error);
            if (trace != null) model.addAttribute("trace", trace);

            switch (errorID) {
                case "403": viewURL = "/problem/error_403"; break;
                case "404": viewURL = "/problem/error_404"; break;
                case "500": viewURL = "/problem/error_500"; break;
                default: break;
            }
        }
        log.info("Sending to {}", viewURL);

        return viewURL;
    }

}
