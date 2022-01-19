package org.eco.mubisoft.good_and_cheap.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String language;
    private Integer timeInMinutes;
    private String imgSrc = null;

    @ManyToOne
    private AppUser author;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Flag> recipeFlags = new ArrayList<>();

    public Recipe(Long id) {
        this.id = id;
    }

    public List<Ingredient> getIngredientList() {
        return new ArrayList<>(ingredients);
    }
}
