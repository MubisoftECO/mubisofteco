package org.eco.mubisoft.good_and_cheap.analytic.control;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.business.model.Business;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.service.AnalyticService;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.eco.mubisoft.good_and_cheap.metric.statistic.ActionCounter;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Slf4j
@Controller
@RequestMapping("/analytic")
@RequiredArgsConstructor
public class AnalyticController {
    private final AnalyticService analyticService;
    private final UserService userService;
    private AppUser user;
    private Long id;
    private int countSales;
    private int countBusiness;
    private List<Double> expired;
    private List<Double> sold;
    private List<Double> other;
    private List<String> productName;
    private List<String> reason;
    private List<Double> total;
    private final int MAX_REQUEST_REFRESH = 2;
    private final ActionCounter actionCounter;

    @GetMapping("/options")
    public void displayOptions(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {
        user = getLoggedUser(request);

        if (user != null) {
            if (!analyticService.userIsAuthorized(user)) response.sendError(HttpServletResponse.SC_FORBIDDEN);
            else{
                analyticService.enableUserIdPicker(user);
                restartList();
                response.sendRedirect("options/menu");
            }
        } else {
            response.sendRedirect("/login/sign-in/");
        }
    }

    @GetMapping("/options/menu")
    public String displayMenu()  {
        analyticService.enableUserIdPicker(user);
        restartList();

        return "/analytic/analytic_option";
    }

    @GetMapping("/sales-balance")
    public String displaySalesBalance(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        String lang = "EN";
        actionCounter.increment(user,"salesBalanceBtn");
        countSales++;
        if(countSales >= MAX_REQUEST_REFRESH) {
            model.addAttribute("expiredList", expired);
            model.addAttribute("soldList", sold);
            model.addAttribute("otherList", other);
            model.addAttribute("productNameList", productName);
            return "analytic/analytic_salesbalance";
        }

        id = getLoggedUser(request).getId();
        analyticService.storeSalesBalanceData(id);

        List<SalesBalance> salesBalanceList = analyticService.displaySalesBalance(lang);

        for (SalesBalance s: salesBalanceList) {
            productName.add(s.getProductName());
            Map<String, Double> percentages = s.getPercentage();
            Set<Map.Entry<String, Double>> percentageList = percentages.entrySet();

            for (Map.Entry<String,Double> entry : percentageList) {
                String key = entry.getKey();
                Double values = entry.getValue();
                switch (key) {
                    case "SOLD":
                        sold.add(values.doubleValue());
                        break;
                    case "EXPIRED":
                        expired.add(values.doubleValue());
                        break;
                    case "OTHER":
                        other.add(values.doubleValue());
                        break;
                }
            }
        }
        model.addAttribute("expiredList", expired);
        model.addAttribute("soldList", sold);
        model.addAttribute("otherList", other);
        model.addAttribute("productNameList", productName);

        return "analytic/analytic_salesbalance";
    }

    @GetMapping("/business")
    public String displayMyBusiness(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        String lang = "EN";
        actionCounter.increment(user,"businessBtn");
        countBusiness++;
        if(countBusiness>= 2) {
            model.addAttribute("reasonList",reason);
            model.addAttribute("totalList", total);
            return "analytic/analytic_business";
        }

        id = getLoggedUser(request).getId();
        analyticService.storeSalesBalanceData(id);
        List<Business> businessDetailList = analyticService.displayMyBusiness(lang);

        for (Business b: businessDetailList) {
            reason.add(b.getReason());
            total.add(b.getTotal());
        }

        model.addAttribute("reasonList",reason);
        model.addAttribute("totalList", total);

        return "analytic/analytic_business";
    }

    @GetMapping("/most-least-sold")
    public String displayMostLeast(){
        actionCounter.increment(user,"most-least-btn");
        return "analytic/analytic_most_least";
    }

    @GetMapping("/most-sold")
    @ResponseBody
    public String getMostSoldProducts(HttpServletRequest request) throws ExecutionException, InterruptedException {

        String city =  getLoggedUser(request).getLocation().getCity().getName();

        analyticService.storeSoldOnlyData(city);
        List<MostLeastSold> list = analyticService.productMostList();

        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @GetMapping("/least-sold")
    @ResponseBody
    public String getLessSoldProducts(HttpServletRequest request) throws ExecutionException, InterruptedException {

        String city =  getLoggedUser(request).getLocation().getCity().getName();

        analyticService.storeSoldOnlyData(city);
        List<MostLeastSold> list = analyticService.productLeastList();
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    /** NEEDED LOCAL FUNCTIONALITIES*/

    private AppUser getLoggedUser(HttpServletRequest request){
        return userService.getLoggedUser(request);
    }

    private void restartList() {
        expired = new ArrayList<>();
        sold = new ArrayList<>();
        other  = new ArrayList<>();
        productName  = new ArrayList<>();
        reason = new ArrayList<>();
        total = new ArrayList<>();
        countSales = 0;
        countBusiness = 0;
    }
}
