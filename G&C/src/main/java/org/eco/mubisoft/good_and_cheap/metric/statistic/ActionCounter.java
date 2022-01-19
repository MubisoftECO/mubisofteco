package org.eco.mubisoft.good_and_cheap.metric.statistic;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eco.mubisoft.good_and_cheap.metric.domain.service.MetricService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ActionCounter {

    private Counter testCounter;
    private MetricService metricService;

    public ActionCounter(MeterRegistry meterRegistry, MetricService metricService) {
        testCounter = meterRegistry.counter("custom_counter");
        this.metricService = metricService;
    }

    public void increment(AppUser appUser, String buttonName) {
        testCounter.increment();
        //check if exists not implemented yet
        record(testCounter.count(), appUser,buttonName);
    }

    private void record(double count, AppUser appUser, String buttonName) {
        Date date = new Date();
        metricService.setMetric(buttonName, (int) count,date,appUser);
    }

}
