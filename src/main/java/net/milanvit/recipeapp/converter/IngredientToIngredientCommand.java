package net.milanvit.recipeapp.converter;

import lombok.Synchronized;
import net.milanvit.recipeapp.command.IngredientCommand;
import net.milanvit.recipeapp.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        final IngredientCommand destination = new IngredientCommand();

        destination.setId(source.getId());
        destination.setAmount(source.getAmount());
        destination.setDescription(source.getDescription());
        destination.setUom(uomConverter.convert(source.getUom()));

        return destination;
    }
}
