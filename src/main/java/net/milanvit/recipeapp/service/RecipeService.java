package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
