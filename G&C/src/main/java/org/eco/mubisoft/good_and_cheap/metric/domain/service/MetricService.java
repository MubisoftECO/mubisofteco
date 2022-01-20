package org.eco.mubisoft.good_and_cheap.metric.domain.service;

import org.eco.mubisoft.good_and_cheap.metric.domain.model.Metric;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface MetricService {
    @Query(value = "insert into metrics (id, button_name, counter, date, user_id) values (:buttonName,:counter,:date,:userId)"
            ,nativeQuery = true)
    void setMetric(@Param("buttonName") String buttonName,
                   @Param("counter") int counter,
                   @Param("date") Date date,
                   @Param("userId")AppUser appUser);

    List<Metric> getAllMetrics();

    List<Metric> getMetricSpecificId(@Param("user_id") Long user_id);

    @Query(value = "update metrics set  = :counter  where button_name = :buttonName",nativeQuery = true)
    void updateCounter(@Param("counter") int counter,
                       @Param("buttonName") String buttonName, Long id);

}
