package org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostLessSoldDetail {
    private String name_en;
    private String name_es;
    private String name_eu;
    private Double total;
}
