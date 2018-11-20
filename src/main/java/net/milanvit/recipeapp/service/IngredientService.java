package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.IngredientCommand;

public interface IngredientService {
    IngredientCommand findCommandByReceiptIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
