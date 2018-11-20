package net.milanvit.recipeapp.converter;

import lombok.Synchronized;
import net.milanvit.recipeapp.command.RecipeCommand;
import net.milanvit.recipeapp.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @SuppressWarnings("Duplicates")
    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand destination = new RecipeCommand();

        destination.setId(source.getId());
        destination.setCookTime(source.getCookTime());
        destination.setPrepTime(source.getPrepTime());
        destination.setDescription(source.getDescription());
        destination.setDifficulty(source.getDifficulty());
        destination.setDirections(source.getDirections());
        destination.setServings(source.getServings());
        destination.setSource(source.getSource());
        destination.setUrl(source.getUrl());
        destination.setNotes(notesConverter.convert(source.getNotes()));
        destination.setImage(source.getImage());

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                .forEach(category -> destination.getCategories().add(categoryConverter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                .forEach(ingredient -> destination.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return destination;
    }
}
