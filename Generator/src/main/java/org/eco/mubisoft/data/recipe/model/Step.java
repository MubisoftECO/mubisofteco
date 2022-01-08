package org.eco.mubisoft.data.recipe.model;

public class Step {

    private Long id;
    private Integer stepNum;
    private String description;
    private Recipe recipe;

    public Step(Long id, Integer stepNum, String description, Recipe recipe) {
        this.id = id;
        this.stepNum = stepNum;
        this.description = description;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public Integer getStepNum() {
        return stepNum;
    }

    public String getDescription() {
        return description;
    }

    public Long getRecipe() {
        return recipe.getId();
    }

}
