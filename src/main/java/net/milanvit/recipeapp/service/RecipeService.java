package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(String id);

    RecipeCommand findCommandById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(String id);
}
