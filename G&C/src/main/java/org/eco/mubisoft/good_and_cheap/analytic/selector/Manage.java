package org.eco.mubisoft.good_and_cheap.analytic.selector;

import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class Manage {

    public Map<String, List<ProductDto>> simpleMapProduct(Function<ProductDto, String> selector, List<ProductDto> list) {
        Map<String, List<ProductDto>> group = new HashMap<>();

        for(Iterator<ProductDto> iterator = list.iterator(); iterator.hasNext();) {
            ProductDto details = (ProductDto) iterator.next();

            String key = selector.apply(details);

            List<ProductDto> dtoList = group.get(key);

            if(dtoList == null) {
                dtoList = new ArrayList<>();
            }

            dtoList.add(details);
            group.put(key, dtoList);
        }

        return  group;
    }

    public Map<String, List<SalesBalanceDetail>> simpleMapSalesBalance(Function<SalesBalanceDetail, String> selector, List<SalesBalanceDetail> list) {
        Map<String, List<SalesBalanceDetail>> group = new HashMap<>();

        for(Iterator<SalesBalanceDetail> iterator = list.iterator(); iterator.hasNext();) {
            SalesBalanceDetail salesBalance = (SalesBalanceDetail) iterator.next();

            String key = selector.apply(salesBalance);

            List<SalesBalanceDetail> productListSelector = group.get(key);

            if(productListSelector == null) {
                productListSelector = new ArrayList<>();
            }
            productListSelector.add(salesBalance);

            group.put(key,productListSelector);
        }

        return  group;
    }

}
