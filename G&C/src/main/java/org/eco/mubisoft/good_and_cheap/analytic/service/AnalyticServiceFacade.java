package org.eco.mubisoft.good_and_cheap.analytic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.business.model.Business;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSoldType;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalanceDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.service.SalesBalanceService;
import org.eco.mubisoft.good_and_cheap.analytic.selector.Manage;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.product.thread.*;
import org.eco.mubisoft.good_and_cheap.thread.ThreadExecutorService;
import org.eco.mubisoft.good_and_cheap.thread.ThreadInitializer;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AnalyticServiceFacade implements AnalyticService{
    private final UserService userService;
    private final ProductService productService;
    private final SalesBalanceService salesBalanceService;
    private final Manage manage;
    private static final String REASON_SOLD = "SOLD";
    private static final String REASON_EXPIRED = "EXPIRED";
    private static final String REASON_OTHER = "OTHER";
    private static final String LANG_ES = "ES";
    private static final String LANG_EU = "EU";


    @Override
    public void enableUserIdPicker(AppUser appUser){
        ThreadInitializer.setTypeRunnableList(appUser.getId(),productService);
    }

    @Override
    public boolean userIsAuthorized(AppUser appUser) {
        return userService.userHasRole(appUser, "ROLE_VENDOR");
    }

    @Override
    public void storeSalesBalanceData(Long id)  {
        List<Runnable> list = ThreadInitializer.getListProductType();

        ThreadExecutorService.INSTANCE.getExecutorService().execute(() -> {
            log.info("(TASK STARTS) PRODUCT (EXPIRED|SOLD|OTHER) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
            for (Runnable runnable : list) {
                log.info("RUNNABLE EXECUTING..");
                runnable.run();
            }
            log.info("(TASK ENDS) PRODUCT (EXPIRED|SOLD|OTHER) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        });

    }

    @Override
    public List<SalesBalance> displaySalesBalance(String lang) throws ExecutionException, InterruptedException {
        Thread.sleep(1000);
        Future<List<ProductDto>> futureProductDtoList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductTypeConsumer(productService));
        List<ProductDto> productDtoList = futureProductDtoList.get();
        collectAllToProductDto(productDtoList, lang);
        return salesBalanceService.getSalesListFromBuffer();
    }

    public List<Business> displayMyBusiness(String lang) throws ExecutionException, InterruptedException {
        Thread.sleep(50);
        Future<List<ProductDto>> futureProductDtoList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductTypeConsumer(productService));
        List<ProductDto> productDtoList = futureProductDtoList.get();

        Map<String, List<ProductDto>> products = manage.simpleMapProduct(ProductDto::getReason, productDtoList);
        List<Business> businessDetailList = new ArrayList<>();

        Set<Map.Entry<String, List<ProductDto>>> percentageList = products.entrySet();

        for (Map.Entry<String,List<ProductDto>> entry : percentageList) {
            String key = entry.getKey();
            List<ProductDto> dtoList = entry.getValue();
            Double total = (double) 0;
            for (ProductDto p: dtoList) {
                total+= p.getTotal();
            }
            switch (key) {
                case REASON_OTHER:
                    key = reasonLanguage(lang,REASON_OTHER);
                    break;
                case REASON_EXPIRED:
                    key = reasonLanguage(lang,REASON_EXPIRED);
                    break;
                case REASON_SOLD:
                    key = reasonLanguage(lang,REASON_SOLD);
                    break;
                default: break;
            }
            Business businessDetail = new Business(key, BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue());
            if (!businessDetailList.contains(businessDetail)) {
                businessDetailList.add(businessDetail);
            }
        }
        return businessDetailList;
    }

    @Override
    public void storeSoldOnlyData(String city) throws ExecutionException, InterruptedException {
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductSoldOnlyProducer(city, productService));
        Thread.sleep(500);
        Future<List<ProductSoldOnlyDto>> futureProductSoldList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductSoldOnlyConsumer(productService));
        List<ProductSoldOnlyDto> productSoldOnlyDtoList = futureProductSoldList.get();

        // total value
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductSoldOnlyTotalProducer(productService,productSoldOnlyDtoList));
        Thread.sleep(500);
        Future<List<MostLessSoldDetail>> futureMostLessSoldDetail = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductSoldOnlyTotalConsumer(productService));
        List<MostLessSoldDetail> productMostLessSoldDetailList = futureMostLessSoldDetail.get();

        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductMostSoldProducer(productService,productMostLessSoldDetailList));
        ThreadExecutorService.INSTANCE.getExecutorService().execute(new ProductLeastSoldProducer(productService,productMostLessSoldDetailList));

        Thread.sleep(500);
    }

    @Override
    public List<MostLeastSold> productLeastList() throws ExecutionException, InterruptedException {
        Future<List<MostLeastSold>> futureMostLeastList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductMostLeastConsumer(productService));
        List<MostLeastSold> productMostLeastDtoList = futureMostLeastList.get();

        return productMostLeastDtoList.stream()
                .filter(m -> m.getType() == MostLeastSoldType.BOTTOM)
                .sorted(Comparator.comparing(MostLeastSold::getOrder))
                .collect(Collectors.toList());
    }

    @Override
    public List<MostLeastSold> productMostList() throws ExecutionException, InterruptedException {
        Future<List<MostLeastSold>> futureMostLeastList = ThreadExecutorService.INSTANCE.getExecutorService().submit(new ProductMostLeastConsumer(productService));
        List<MostLeastSold> productMostLeastDtoList = futureMostLeastList.get();

        return productMostLeastDtoList.stream()
                .filter(m -> m.getType() == MostLeastSoldType.TOP)
                .sorted(Comparator.comparing(MostLeastSold::getOrder))
                .collect(Collectors.toList());
    }

    /** NEEDED FUNCTIONALITIES */

    private void collectAllToProductDto(List<ProductDto> productDtoList, String lang) throws InterruptedException {
        Map<String, List<ProductDto>> products = manage.simpleMapProduct(ProductDto::getNameEn, productDtoList);
        List<SalesBalanceDetail> saleList = new ArrayList<>();

        products.forEach(
                (k,v) -> v.forEach( s -> saleList.add(
                        new SalesBalanceDetail(
                                productLanguage(lang,s),s.getReason(),
                                calculatePercentage(s.getTotal(),
                                        v.stream().mapToDouble(ProductDto::getTotal).sum())))
                        )
        );

        Map<String, List<SalesBalanceDetail>> sales = manage.simpleMapSalesBalance(SalesBalanceDetail::getProductName, saleList);
        salesBalanceService.createSalesBalanceList(sales);
    }

    private double calculatePercentage(double value, double total) {
        double percentage = 0;
        try {
            percentage = (value/total) *100;
        } catch (ArithmeticException e) {
            log.warn("Division by zero forbidden");
        }
        return Math.round(percentage);
    }

    private String productLanguage(String lang, ProductDto productDto) {
        String name;

        switch (lang) {
            case LANG_ES:
                name = productDto.getNameEs();
                break;
            case LANG_EU:
                name = productDto.getNameEu();
                break;
            default:
                name =  productDto.getNameEn();
        }
        return name;
    }

    private String reasonLanguage(String lang, String key) {
        String reasonMessage;

        switch (key) {
            case REASON_OTHER:
                switch (lang) {
                    case LANG_ES:
                        reasonMessage = "Productos rechazados";
                        break;
                    case LANG_EU:
                        reasonMessage = "Baztertutako produktuak";
                        break;
                    default:
                        reasonMessage =  "Discarted products";
                }
                break;
            case REASON_EXPIRED:
                switch (lang) {
                    case LANG_ES:
                        reasonMessage = "Productos no vendidos";
                        break;
                    case LANG_EU:
                        reasonMessage = "Saldu gabeko produktuak";
                        break;
                    default:
                        reasonMessage =  "Not sold products";

                }
                break;
            case REASON_SOLD:
                switch (lang) {
                    case LANG_ES:
                        reasonMessage = "Productos vendidos";
                        break;
                    case LANG_EU:
                        reasonMessage = "Saldutako produktuak";
                        break;
                    default:
                        reasonMessage =  "Sold products";
                }
                break;
            default:
                reasonMessage = null;
                break;
        }
        return reasonMessage;
    }
}
