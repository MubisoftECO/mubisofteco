package org.eco.mubisoft.good_and_cheap.metric.statistic;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.metric.domain.service.MetricService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class ActionCounter {

    private final MetricService metricService;

    public ActionCounter(MetricService metricService) {
        this.metricService = metricService;
    }

    public void increment(AppUser appUser, String buttonName) {
        if(appUser == null) {
            log.info("Storing anonymous user pressing button {}", buttonName);
            recordButtonPress(1, null, buttonName);
        } else {
            log.info("Storing {} user pressing button {}", appUser.getId(), buttonName);
            recordButtonPress(1, appUser, buttonName);
        }
    }

    private void recordButtonPress(double count, AppUser appUser, String buttonName) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = format.format(cal.getTime());

        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        metricService.setMetric(buttonName, (int) count,date,appUser);
    }

}
