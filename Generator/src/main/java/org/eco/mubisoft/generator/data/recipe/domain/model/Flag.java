package org.eco.mubisoft.generator.data.recipe.domain.model;

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
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;

    public Flag(Long id){
        this.id = id;
    }

}
