package org.eco.mubisoft.good_and_cheap.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_es;
    private String name_en;
    private String name_eu;

    public Flag(Long id){
        this.id = id;
    }

}
