package org.eco.mubisoft.generator.data.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.generator.data.product.domain.model.ProductType;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(IngredientId.class)
public class Ingredient {

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "id")
    private ProductType productType;

    @Column(name = "quantity")
    private Integer quantity;

}
