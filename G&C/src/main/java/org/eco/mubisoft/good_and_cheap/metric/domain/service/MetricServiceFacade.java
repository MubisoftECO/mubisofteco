package org.eco.mubisoft.good_and_cheap.metric.domain.service;

import lombok.RequiredArgsConstructor;
import org.eco.mubisoft.good_and_cheap.metric.domain.model.Metric;
import org.eco.mubisoft.good_and_cheap.metric.domain.repo.MetricRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class MetricServiceFacade implements MetricService{
    private final MetricRepository metricRepository;

    @Override
    public void setMetric(String buttonName, int counter, Date date, AppUser appUser) {
        Metric metric = new Metric();
        metric.setButtonName(buttonName);
        metric.setCounter(counter);
        metric.setDate(date);
        metric.setUser(appUser);

        metricRepository.save(metric);
    }
}
