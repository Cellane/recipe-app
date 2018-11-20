package net.milanvit.recipeapp.converter;

import lombok.Synchronized;
import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredient destination = new Ingredient();

        destination.setId(source.getId());
        destination.setAmount(source.getAmount());
        destination.setDescription(source.getDescription());
        destination.setUom(uomConverter.convert(source.getUom()));

        return destination;
    }
}
