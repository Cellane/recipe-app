package net.milanvit.recipeapp.controller;

import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.command.UnitOfMeasureCommand;
import net.milanvit.recipeapp.service.IngredientService;
import net.milanvit.recipeapp.service.RecipeService;
import net.milanvit.recipeapp.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredient")
    public String getIngredients(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable Long recipeId, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setRecipeId(recipeId);
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureService.findAll());

        return "recipe/ingredient/ingredient-form";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long recipeId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeIdAndIngredientId(recipeId, id));
        model.addAttribute("uoms", unitOfMeasureService.findAll());

        return "recipe/ingredient/ingredient-form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable Long recipeId, @PathVariable Long id) {
        ingredientService.deleteByRecipeIdAndIngredientId(recipeId, id);

        return "redirect:/recipe/" + recipeId + "/ingredient";
    }
}
