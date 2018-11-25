package net.milanvit.recipeapp.service;

import lombok.extern.slf4j.Slf4j;
import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.converter.RecipeCommandToRecipe;
import net.milanvit.recipeapp.converter.RecipeToRecipeCommand;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.exception.NotFoundException;
import net.milanvit.recipeapp.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        log.debug("I’m in the service!");
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(String id) {
        log.debug("Finding recipe with ID: " + id);

        return recipeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Recipe not found for ID " + id));
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
        log.debug("Finding command with ID: " + id);

        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        log.debug("Saved recipeId: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
