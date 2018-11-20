package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.converter.IngredientCommandToIngredient;
import net.milanvit.recipeapp.converter.IngredientToIngredientCommand;
import net.milanvit.recipeapp.domain.Ingredient;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.repository.RecipeRepository;
import net.milanvit.recipeapp.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            return null;
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients()
            .stream()
            .filter(ingredient -> ingredient.getId().equals(ingredientId))
            .map(ingredientToIngredientCommand::convert)
            .findFirst();

        return ingredientCommandOptional.orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
            .stream()
            .filter(ingredient -> ingredient.getId().equals(command.getId()))
            .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient foundIngredient = ingredientOptional.get();

            foundIngredient.setDescription(command.getDescription());
            foundIngredient.setAmount(command.getAmount());
            foundIngredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM not found")));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);

            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
            .filter(recipeIngredient -> recipeIngredient.getId().equals(command.getId()))
            .findFirst();

        if (!savedIngredientOptional.isPresent()) {
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getDescription().equals(command.getDescription()))
                .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();

                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientToDelete);

                recipeRepository.save(recipe);
            }
        }
    }
}
