package org.eco.mubisoft.generator.data.recipe.domain.model;

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
    private Collection<Ingredient> ingredients = new ArrayList<>();
    @ManyToMany
    private Collection<Flag> recipeFlags = new ArrayList<>();

    public Recipe(Long id) {
        this.id = id;
    }

    public Recipe(String title, String description, String language, Integer timeInMinutes, AppUser author, Collection<Ingredient> ingredients, Collection<Flag> recipeFlags) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.timeInMinutes = timeInMinutes;
        this.author = author;
        this.ingredients = ingredients;
        this.recipeFlags = recipeFlags;
    }
}
