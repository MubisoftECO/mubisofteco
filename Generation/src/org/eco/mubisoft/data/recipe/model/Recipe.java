package org.eco.mubisoft.data.recipe.model;

import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.user.model.AppUser;

import java.util.ArrayList;
import java.util.Collection;

public class Recipe {

    private Long id;
    private String title;
    private String description;
    private String language;
    private Integer timeInMinutes;
    private String imgSrc = null;
    private AppUser author;
    private Collection<ProductType> ingredients = new ArrayList<>();
    private Collection<Flag> recipeFlags = new ArrayList<>();

    public Recipe(Long id, String title, String description, String language, Integer timeInMinutes,
                  AppUser author, Collection<ProductType> ingredients, Collection<Flag> recipeFlags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.language = language;
        this.timeInMinutes = timeInMinutes;
        this.author = author;
        this.ingredients = ingredients;
        this.recipeFlags = recipeFlags;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public AppUser getAuthor() {
        return author;
    }

    public Collection<ProductType> getIngredients() {
        return ingredients;
    }

    public Collection<Flag> getRecipeFlags() {
        return recipeFlags;
    }
}
