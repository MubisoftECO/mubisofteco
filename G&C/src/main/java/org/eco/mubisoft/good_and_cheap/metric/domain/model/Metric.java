package org.eco.mubisoft.good_and_cheap.metric.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metric_id")
    @SequenceGenerator(name = "metric_id", initialValue = 1, allocationSize = 1, sequenceName = "metric_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "button_name", length = 100)
    private String buttonName;

    @Column(name = "counter", nullable = false)
    private Integer counter;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

}