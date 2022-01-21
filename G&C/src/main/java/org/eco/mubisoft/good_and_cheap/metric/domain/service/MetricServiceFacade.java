package org.eco.mubisoft.good_and_cheap.metric.domain.service;

import lombok.RequiredArgsConstructor;
import org.eco.mubisoft.good_and_cheap.metric.domain.model.Metric;
import org.eco.mubisoft.good_and_cheap.metric.domain.repo.MetricRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    @Override
    public List<Metric> getMetricSpecificId(Long user_id) {
        return  metricRepository.findByuser_id(user_id);
    }

    @Override
    public void updateCounter(int counter, String buttonName, Long id) {
        Metric optional = metricRepository.findById(id).orElse(null);

        if (optional != null) {
            Metric metric = optional;
            metric.setDate(new Date());
            metric.setCounter(counter+1);
            metricRepository.save(metric);
        }
    }
}
