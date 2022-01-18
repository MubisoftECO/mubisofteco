package org.eco.mubisoft.good_and_cheap.analytic.service;

import lombok.AllArgsConstructor;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.service.SalesBalanceService;
import org.eco.mubisoft.good_and_cheap.analytic.selector.Manage;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductTypeConsumer;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductTypeExpiredProducer;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductTypeOtherProducer;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductTypeSoldProducer;
import org.eco.mubisoft.good_and_cheap.thread.ThreadExecutorService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.eco.mubisoft.good_and_cheap.user.thread.UserConsumer;
import org.eco.mubisoft.good_and_cheap.user.thread.UserProducer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AnalyticServiceFacade implements AnalyticService{
    private final UserService userService;
    private final ProductService productService;
    private final SalesBalanceService salesBalanceService;
    private final Manage manage;

    @Override
    public void enableUserIdPicker(String city) throws InterruptedException {
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new UserProducer(city, userService));
    }

    @Override
    public void storeSalesBalanceData(Long id) throws ExecutionException, InterruptedException {

        boolean find;
        do {
            Future<List<Long>> futureIdList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new UserConsumer(userService));

            List<Long> idList = futureIdList.get();

            find = idList.stream()
                    .anyMatch(t -> t.longValue() == id);

        } while(!find);

        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductTypeSoldProducer(id, productService));
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductTypeExpiredProducer(id,productService));
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductTypeOtherProducer(id,productService));
    }

    @Override
    public List<SalesBalance> displaySalesBalance(String lang) throws ExecutionException, InterruptedException {
        Thread.sleep(50);
        Future<List<ProductDto>> futureProductDtoList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductTypeConsumer(productService));
        List<ProductDto> productDtoList = futureProductDtoList.get();
        collectAllToProductDto(productDtoList, lang);
        List<SalesBalance> salesBalanceList = salesBalanceService.getSalesListFromBuffer();

        return salesBalanceList;
    }

    /** NEEDED FUNCTIONALITIES */

    private Map<String, List<SalesBalanceDetail>> collectAllToProductDto(List<ProductDto> productDtoList, String lang) {
        Map<String, List<ProductDto>> products = manage.simpleMapProduct(ProductDto::getNameEn, productDtoList);
        List<SalesBalanceDetail> saleList = new ArrayList<>();

        products.forEach(
                (k,v) ->
                        v.forEach( s -> saleList.add(new SalesBalanceDetail(productLanguage(lang,s),s.getReason(), calculatePercentage(s.getTotal(), v.stream().collect(Collectors.summingDouble(ProductDto::getTotal)))))
                        )
        );

        Map<String, List<SalesBalanceDetail>> sales = manage.simpleMapSalesBalance(SalesBalanceDetail::getProductName, saleList);
        salesBalanceService.createSalesBalanceList(sales);
        return  sales;
    }


    private double calculatePercentage(double value, double total) {
        double percentage = 0;
        try {
            percentage = (value/total) *100;
        } catch (ArithmeticException e) {
            System.out.println("Division by zero forbidden");
        }
        return Math.round(percentage);
    }

    private String productLanguage(String lang, ProductDto productDto) {
        String name  = null;

        switch (lang) {
            case "ES":
                name = productDto.getNameEs();
                break;
            case "EU":
                name = productDto.getNameEu();
                break;
            default:
                name =  productDto.getNameEn();

        }
        return name;
    }



}
