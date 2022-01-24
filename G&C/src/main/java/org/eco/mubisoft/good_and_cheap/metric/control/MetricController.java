package org.eco.mubisoft.good_and_cheap.metric.control;

import lombok.RequiredArgsConstructor;
import org.eco.mubisoft.good_and_cheap.metric.statistic.ActionCounter;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/metric")
@RequiredArgsConstructor
public class MetricController {

    private final UserService userService;
    private final ActionCounter actionCounter;

    @ResponseBody
    @PostMapping("/counter")
    public void  getButtonName(@RequestParam(name= "id") String id, HttpServletRequest request) {
        AppUser user = userService.getLoggedUser(request);
        actionCounter.increment(user,id);
    }
}
