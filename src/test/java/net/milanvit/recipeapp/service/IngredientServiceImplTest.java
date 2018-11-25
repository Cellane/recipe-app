package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.converter.IngredientCommandToIngredient;
import net.milanvit.recipeapp.converter.IngredientToIngredientCommand;
import net.milanvit.recipeapp.converter.UnitOfMeasureCommandToUnitOfMeasure;
import net.milanvit.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import net.milanvit.recipeapp.domain.Ingredient;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.repository.RecipeRepository;
import net.milanvit.recipeapp.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private IngredientService ingredientService;

    public IngredientServiceImplTest() {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient,
            recipeRepository, unitOfMeasureRepository
        );
    }

    @Test
    public void findCommandByRecipeIdAndIngredientIdHappy() {
        Recipe recipe = new Recipe();
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();

        recipe.setId("1");
        ingredient1.setId("1");
        ingredient2.setId("2");
        ingredient3.setId("3");

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndIngredientId("1", "3");

        assertEquals("3", ingredientCommand.getId());
        // assertEquals("1", ingredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyString());
    }

    @Test
    public void saveRecipeCommand() {
        IngredientCommand command = new IngredientCommand();
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Recipe savedRecipe = new Recipe();

        command.setId("3");
        command.setRecipeId("2");
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngredients().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals("3", savedCommand.getId());
        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    public void testDeleteById() {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();

        ingredient.setId("3");
        recipe.addIngredient(ingredient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

        ingredientService.deleteByRecipeIdAndIngredientId("1", "3");

        verify(recipeRepository).findById(anyString());
        verify(recipeRepository).save(any(Recipe.class));
    }
}
