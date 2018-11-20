package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.converter.IngredientToIngredientCommand;
import net.milanvit.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import net.milanvit.recipeapp.domain.Ingredient;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientService ingredientService;

    public IngredientServiceImplTest() {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand);
    }

    @Test
    public void findCommandByRecipeIdAndIngredientIdHappy() {
        Recipe recipe = new Recipe();
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Ingredient ingredient3 = new Ingredient();

        recipe.setId(1L);
        ingredient1.setId(1L);
        ingredient2.setId(2L);
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand ingredientCommand = ingredientService.findCommandByReceiptIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository).findById(anyLong());
    }
}
