package net.milanvit.recipeapp.controller;

import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.service.IngredientService;
import net.milanvit.recipeapp.service.RecipeService;
import net.milanvit.recipeapp.service.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredients() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredient"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/list"))
            .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findCommandById(anyLong());
    }

    @Test
    public void showIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findCommandByReceiptIdAndIngredientId(anyLong(), anyLong()))
            .thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/show"))
            .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService).findCommandByReceiptIdAndIngredientId(anyLong(), anyLong());
    }

    @Test
    public void updateIngredientForm() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findCommandByReceiptIdAndIngredientId(anyLong(), anyLong()))
            .thenReturn(ingredientCommand);
        when(unitOfMeasureService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/ingredient-form"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uoms"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/2/ingredient")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/recipe/2/ingredient/3/show"));
    }
}
