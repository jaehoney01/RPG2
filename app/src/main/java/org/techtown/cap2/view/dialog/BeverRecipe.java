package org.techtown.cap2.view.dialog;

public class BeverRecipe {
    private String[] recipe;
    private int[] recipeCount;

    public BeverRecipe(String[] recipe, int[] recipeCount) {
        this.recipe = recipe;
        this.recipeCount = recipeCount;
    }

    public String[] getRecipe() {
        return recipe;
    }

    public int[] getRecipeCount() {
        return recipeCount;
    }
}
