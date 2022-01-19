package org.eco.mubisoft.good_and_cheap.metric.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface MetricService {
    @Query(value = "insert into metrics (id, button_name, counter, date, user_id) values (:buttonName,:counter,:date,:userId)"
            ,nativeQuery = true)
    void setMetric(@Param("buttonName") String buttonName,
                   @Param("counter") int counter,
                   @Param("date") Date date,
                   @Param("userId")AppUser appUser);
}
