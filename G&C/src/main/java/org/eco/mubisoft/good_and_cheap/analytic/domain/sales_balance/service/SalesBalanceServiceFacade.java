package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.service;

import lombok.RequiredArgsConstructor;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.thread.SalesBalanceBuffer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesBalanceServiceFacade implements SalesBalanceService {
    private final SalesBalanceBuffer salesBalanceBuffer;

    @Override
    public List<SalesBalance> getSalesListFromBuffer() throws InterruptedException {
        List<SalesBalance> list = new ArrayList<>();

        while (salesBalanceBuffer.getBufferSize() > 0) {
            list.add(salesBalanceBuffer.get());
        }
        return list;
    }

    @Override
    public void createSalesBalanceList(Map<String, List<SalesBalanceDetail>> salesBalanceList) throws InterruptedException {
        Set<Map.Entry<String, List<SalesBalanceDetail>>> select = salesBalanceList.entrySet();

        for (Map.Entry<String, List<SalesBalanceDetail>> entry : select) {
            String key = entry.getKey();
            List<SalesBalanceDetail> values = entry.getValue();

            List<String> currentReason = new ArrayList<>();
            currentReason.add("SOLD");
            currentReason.add("EXPIRED");
            currentReason.add("OTHER");
            List<String > copy = new ArrayList<>();


            SalesBalance sales = new SalesBalance(key);

            for (SalesBalanceDetail s : values) {
                for (String value: currentReason) {
                    if(value.equals(s.getReason())) {
                        copy.add(s.getReason());
                    }
                }
                for (String d: copy) {
                    currentReason.remove(d);
                }
                sales.save(s.getReason(), s.getPercentage());
            }
            for (String s : currentReason) {
                sales.save(s, 0.0);
            }
            salesBalanceBuffer.put(sales);
        }
    }


}
