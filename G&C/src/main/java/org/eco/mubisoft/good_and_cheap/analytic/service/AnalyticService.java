package org.eco.mubisoft.good_and_cheap.analytic.service;

import org.eco.mubisoft.good_and_cheap.analytic.domain.business.model.Business;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AnalyticService {

    /**
     * <p><b>ENABLE USER PICKER FROM LIST</b></p>
     * <p>Save a new role on the database. Each role must be unique, so it will make
     * sure the role is not already created.</p>
     * @param appUser The role that is going to be added.
     * @return The user role that has been created on the database.
     */

    void enableUserIdPicker(AppUser appUser);
    boolean userIsAuthorized(AppUser appUser);
    /** - SALES - BALANCE - */

    /**
     * <p><b>STORE ID INTO BUFFER</b></p>
     * <p>Activate Thread to save data from db to buffer</p>
     * @param id The role that is going to be added.
     * @return The user role that has been created on the database.
     */

    void storeSalesBalanceData(Long id) throws ExecutionException, InterruptedException;
    /**
     * <p><b>GRAPHIC</b></p>
     * <p>Activate Thread to get all data store in buffer</p>
     */
    List<SalesBalance> displaySalesBalance(String lang) throws ExecutionException, InterruptedException;

    /** - BUSINESS - */
    List<Business> displayMyBusiness(String lang) throws ExecutionException, InterruptedException;

    /** - MOST - LEAST - */
    void storeSoldOnlyData(String city) throws ExecutionException, InterruptedException;
    List<MostLeastSold> productLeastList() throws ExecutionException, InterruptedException;
    List<MostLeastSold> productMostList() throws ExecutionException, InterruptedException;

}
