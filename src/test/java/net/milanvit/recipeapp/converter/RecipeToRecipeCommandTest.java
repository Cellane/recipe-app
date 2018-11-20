package net.milanvit.recipeapp.converter;

import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    private static final Long RECIPE_ID = 1L;
    private static final Integer COOK_TIME = Integer.valueOf("5");
    private static final Integer PREP_TIME = Integer.valueOf("7");
    private static final String DESCRIPTION = "My Recipe";
    private static final String DIRECTIONS = "Directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = Integer.valueOf("3");
    private static final String SOURCE = "Source";
    private static final String URL = "Some URL";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 3L;
    private static final Long INGRED_ID_2 = 4L;
    private static final Long NOTES_ID = 9L;
    private RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
            new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), new NotesToNotesCommand());
    }

    @Test
    public void nullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void emptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {
        Recipe recipe = new Recipe();
        Notes notes = new Notes();
        Category category = new Category();
        Category category2 = new Category();
        Ingredient ingredient = new Ingredient();
        Ingredient ingredient2 = new Ingredient();

        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);
        category.setId(CAT_ID_1);
        category2.setId(CAT_ID2);
        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);
        ingredient.setId(INGRED_ID_1);
        ingredient2.setId(INGRED_ID_2);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        RecipeCommand command = converter.convert(recipe);

        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPrepTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
    }
}