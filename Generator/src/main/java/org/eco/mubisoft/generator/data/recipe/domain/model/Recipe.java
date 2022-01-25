package org.eco.mubisoft.generator.data.recipe.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.generator.data.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;
    private String title;
    private String description;
    private String language;
    private Integer timeInMinutes;
    private String imgSrc = null;

    @ManyToOne
    private AppUser author;

    @OneToMany(mappedBy = "recipe")
    @JsonManagedReference
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany
    private Collection<Flag> recipeFlags = new ArrayList<>();

}
