package org.eco.mubisoft.good_and_cheap.metric.domain.repo;

import org.eco.mubisoft.good_and_cheap.metric.domain.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
}
