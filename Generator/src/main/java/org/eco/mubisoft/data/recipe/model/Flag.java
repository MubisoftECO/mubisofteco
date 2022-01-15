package org.eco.mubisoft.data.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_en;
    private String name_es;
    private String name_eu;

    public Flag(String name_en, String name_es, String name_eu) {
        this.name_en = name_en;
        this.name_es = name_es;
        this.name_eu = name_eu;
    }
}
