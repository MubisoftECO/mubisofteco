package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.service;

import lombok.RequiredArgsConstructor;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.thread.SalesBalanceBuffer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesBalanceServiceFacade implements SalesBalanceService {
    private final SalesBalanceBuffer salesBalanceBuffer;

    @Override
    public List<SalesBalance> getSalesListFromBuffer() {
        List<SalesBalance> list = new ArrayList<>();

        while (salesBalanceBuffer.getBufferSize() > 0) {
            list.add(salesBalanceBuffer.get());
        }

        return list;
    }

    @Override
    public void createSalesBalanceList(Map<String, List<SalesBalanceDetail>> salesBalanceList) {
        Set<Map.Entry<String, List<SalesBalanceDetail>>> select = salesBalanceList.entrySet();

        for (Map.Entry<String, List<SalesBalanceDetail>> entry : select) {
            String key = entry.getKey();
            List<SalesBalanceDetail> values = entry.getValue();

            List<String> currentReason = new ArrayList<>();
            currentReason.add("SOLD");
            currentReason.add("EXPIRED");
            currentReason.add("OTHER");

            SalesBalance sales = new SalesBalance(key);

            for (SalesBalanceDetail s : values) {
                for (int i = 0; i < currentReason.size(); i++) {
                    if(s.getReason().equals(currentReason.get(i))) {
                        currentReason.remove(i);
                    }
                }
                sales.save(s.getReason(), s.getPercentage());
            }
            for (int i = 0; i < currentReason.size(); i++) {
                sales.save(currentReason.get(i), 0.0);
            }
            if(sales != null) salesBalanceBuffer.put(sales);
        }
    }
}
