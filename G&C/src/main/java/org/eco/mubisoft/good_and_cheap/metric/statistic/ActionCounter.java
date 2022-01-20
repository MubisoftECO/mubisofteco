package org.eco.mubisoft.good_and_cheap.metric.statistic;



import org.eco.mubisoft.good_and_cheap.metric.domain.model.Metric;
import org.eco.mubisoft.good_and_cheap.metric.domain.service.MetricService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ActionCounter {


    private MetricService metricService;

    public ActionCounter(MetricService metricService) {
        this.metricService = metricService;
    }

    public void increment(AppUser appUser, String buttonName) {
        List<Metric> metricList = metricService.getAllMetrics();
        boolean userExists = checkUserExists(metricList,appUser);

        if (userExists) {
            List<Metric> savedMetrics = metricService.getMetricSpecificId(appUser.getId());
            Metric metric = checkButtonExists(savedMetrics, buttonName);
            if (metric != null) {
                metricService.updateCounter(metric.getCounter(), metric.getButtonName(), metric.getId());
            } else {
                record(1, appUser, buttonName);
            }
        } else {
            record(1, appUser, buttonName);
        }
    }

    private void record(double count, AppUser appUser, String buttonName) {
        Date date = new Date();
        metricService.setMetric(buttonName, (int) count,date,appUser);
    }

    private Metric checkButtonExists(List<Metric> metricList, String buttonName) {
        List<Metric>  list = metricList;
        if (list != null) {
            for (Metric metric : metricList) {
                if(metric.getButtonName().equals(buttonName)) return metric;
            }
        }
        return  null;
    }

    private boolean checkUserExists(List<Metric> metricList, AppUser appUser) {
        List<Metric>  list = metricList;
        if (list != null) {
            for (Metric m : metricList) {
                if(m.getUser().getId().compareTo(appUser.getId()) == 0) {
                    return true;
                }
            }
        }
        return  false;
    }

}
