package org.eco.mubisoft.good_and_cheap.analytic.control;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.business.model.Business;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.service.AnalyticInternalManager;
import org.eco.mubisoft.good_and_cheap.analytic.service.AnalyticService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private AnalyticInternalManager analyticDataManager;
    private static final int MAX_REQUEST_REFRESH = 2;

    @GetMapping("/options")
    public void displayOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        user = getLoggedUser(request);

        if (user != null) {
            if (analyticService.userIsAuthorized(user)) {
                response.sendRedirect("options/menu");
            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendRedirect("/login/sign-in/");
        }
    }

    @GetMapping("/options/menu")
    public String displayMenu()  {
        analyticDataManager = new AnalyticInternalManager();
        analyticService.enableUserIdPicker(user);

        return "analytic/analytic_option";
    }

    @GetMapping("/sales-balance")
    public String displaySalesBalance(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        String lang = "EN";
        analyticDataManager.setCountSales(analyticDataManager.getCountSales() + 1);
        if(analyticDataManager.getCountSales() >= MAX_REQUEST_REFRESH) {
            model.addAttribute("expiredList", analyticDataManager.getExpired());
            model.addAttribute("soldList", analyticDataManager.getSold());
            model.addAttribute("otherList", analyticDataManager.getOther());
            model.addAttribute("productNameList", analyticDataManager.getProductName());
            return "analytic/analytic_salesbalance";
        }

        id = getLoggedUser(request).getId();
        analyticService.storeSalesBalanceData(id);

        List<SalesBalance> salesBalanceList = analyticService.displaySalesBalance(lang);

        for (SalesBalance s: salesBalanceList) {
            analyticDataManager.addProduct(s.getProductName());
            Map<String, Double> percentages = s.getPercentage();
            Set<Map.Entry<String, Double>> percentageList = percentages.entrySet();

            for (Map.Entry<String,Double> entry : percentageList) {
                String key = entry.getKey();
                Double value = entry.getValue();
                analyticDataManager.addPriceByReason(value,key);
            }
        }
        model.addAttribute("expiredList", analyticDataManager.getExpired());
        model.addAttribute("soldList", analyticDataManager.getSold());
        model.addAttribute("otherList", analyticDataManager.getOther());
        model.addAttribute("productNameList", analyticDataManager.getProductName());

        return "analytic/analytic_salesbalance";
    }

    @GetMapping("/business")
    public String displayMyBusiness(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        String lang = "EN";

        analyticDataManager.setCountBusiness(analyticDataManager.getCountBusiness() + 1);
        if(analyticDataManager.getCountBusiness() >= 2) {
            model.addAttribute("reasonList",analyticDataManager.getReason());
            model.addAttribute("totalList", analyticDataManager.getTotal());
            return "analytic/analytic_business";
        }

        id = getLoggedUser(request).getId();
        analyticService.storeSalesBalanceData(id);
        List<Business> businessDetailList = analyticService.displayMyBusiness(lang);

        for (Business b: businessDetailList) {
            analyticDataManager.addReason(b.getReason());
            analyticDataManager.addTotal(b.getTotal());
        }

        model.addAttribute("reasonList",analyticDataManager.getReason());
        model.addAttribute("totalList", analyticDataManager.getTotal());

        return "analytic/analytic_business";
    }

    @GetMapping("/most-least-sold")
    public String displayMostLeast(){
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

}
