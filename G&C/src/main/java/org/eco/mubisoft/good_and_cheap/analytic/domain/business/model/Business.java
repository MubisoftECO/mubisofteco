package org.eco.mubisoft.good_and_cheap.analytic.domain.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    private String reason;
    private Double total;
}
