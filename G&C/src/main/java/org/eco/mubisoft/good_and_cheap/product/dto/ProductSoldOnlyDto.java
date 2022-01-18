package org.eco.mubisoft.good_and_cheap.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductSoldOnlyDto {
    private String nameEn;
    private String nameEs;
    private String nameEu;
    private Double price;
    private Double quantity;
}
