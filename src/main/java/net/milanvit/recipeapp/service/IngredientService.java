package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteByRecipeIdAndIngredientId(Long recipeId, Long id);
}
