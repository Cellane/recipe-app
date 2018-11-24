package net.milanvit.recipeapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import net.milanvit.recipeapp.domain.Category;
import net.milanvit.recipeapp.domain.UnitOfMeasure;
import net.milanvit.recipeapp.repository.CategoryRepository;
import net.milanvit.recipeapp.repository.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile({"dev", "prod"})
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public BootstrapMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (categoryRepository.count() == 0) {
            log.debug("Loading categories");
            loadCategories();
        }

        if (unitOfMeasureRepository.count() == 0) {
            log.debug("Loading UOMs");
            loadUom();
        }
    }

    private void loadCategories() {
        Category cat1 = new Category();
        Category cat2 = new Category();
        Category cat3 = new Category();
        Category cat4 = new Category();

        cat1.setDescription("American");
        cat2.setDescription("Italian");
        cat3.setDescription("Mexican");
        cat4.setDescription("Fast Food");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);
        categoryRepository.save(cat4);
    }

    private void loadUom() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        UnitOfMeasure uom2 = new UnitOfMeasure();
        UnitOfMeasure uom3 = new UnitOfMeasure();
        UnitOfMeasure uom4 = new UnitOfMeasure();
        UnitOfMeasure uom5 = new UnitOfMeasure();
        UnitOfMeasure uom6 = new UnitOfMeasure();
        UnitOfMeasure uom7 = new UnitOfMeasure();
        UnitOfMeasure uom8 = new UnitOfMeasure();

        uom1.setDescription("Teaspoon");
        uom1.setDescription("Tablespoon");
        uom1.setDescription("Cup");
        uom1.setDescription("Pinch");
        uom1.setDescription("Ounce");
        uom1.setDescription("Each");
        uom1.setDescription("Pint");
        uom1.setDescription("Dash");

        unitOfMeasureRepository.save(uom1);
        unitOfMeasureRepository.save(uom2);
        unitOfMeasureRepository.save(uom3);
        unitOfMeasureRepository.save(uom4);
        unitOfMeasureRepository.save(uom5);
        unitOfMeasureRepository.save(uom6);
        unitOfMeasureRepository.save(uom7);
        unitOfMeasureRepository.save(uom8);
    }
}
