package org.eco.mubisoft.good_and_cheap.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;

}
