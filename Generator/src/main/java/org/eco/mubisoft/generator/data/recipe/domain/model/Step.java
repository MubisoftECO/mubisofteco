package org.eco.mubisoft.generator.data.recipe.domain.model;

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
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;
    private Integer stepNum;
    private String description;
    @ManyToOne
    private Recipe recipe;

    public Step(Integer stepNum, String description, Recipe recipe) {
        this.stepNum = stepNum;
        this.description = description;
        this.recipe = recipe;
    }
}
