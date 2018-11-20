package net.milanvit.recipeapp.converter;

import lombok.Synchronized;
import net.milanvit.recipeapp.command.CategoryCommand;
import net.milanvit.recipeapp.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category destination = new Category();

        destination.setId(source.getId());
        destination.setDescription(source.getDescription());

        return destination;
    }
}
