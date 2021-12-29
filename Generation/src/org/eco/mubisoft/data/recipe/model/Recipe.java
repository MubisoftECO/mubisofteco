package org.eco.mubisoft.data.recipe.model;

import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.user.model.AppUser;

import java.util.ArrayList;
import java.util.Collection;

public class Recipe {

    private Long id;
    private String title;
    private String description;
    private Integer timeInMinutes;
    private String imgSrc = null;
    private AppUser author;
    private Collection<ProductType> ingredients = new ArrayList<>();
    private Collection<Flag> recipeFlags = new ArrayList<>();

}
