package net.milanvit.recipeapp.controller;

import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.exception.NotFoundException;
import net.milanvit.recipeapp.service.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    private RecipeController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(ControllerExceptionHandler.class)
            .build();
    }

    @Test
    public void getRecipe() throws Exception {
        Recipe recipe = new Recipe();

        recipe.setId("1");
        when(recipeService.findById(anyString())).thenReturn(recipe);
        mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/show"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getRecipeNotFound() throws Exception {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("404"));
    }

    @Test
    @Ignore
    public void getRecipeNumberFormat() throws Exception {
        mockMvc.perform(get("/recipe/test/show"))
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400"));
    }

    @Test
    public void getCreateRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipe-form"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void postNewRecipe() throws Exception {
        RecipeCommand command = new RecipeCommand();

        command.setId("2");
        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("id", "")
            .param("description", "some string"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/recipe/2/show"));
    }

    @Test
    public void getUpdateRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        command.setId("1");
        when(recipeService.findCommandById(anyString())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/recipe-form"))
            .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }
}
