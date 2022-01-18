package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.service;

import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SalesBalanceService {
    List<SalesBalance>  getSalesListFromBuffer();
    void createSalesBalanceList(Map<String, List<SalesBalanceDetail>> salesBalanceList);

}
