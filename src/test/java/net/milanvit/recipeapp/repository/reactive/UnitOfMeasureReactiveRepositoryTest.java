package net.milanvit.recipeapp.repository.reactive;

import net.milanvit.recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
    private static final String DESCRIPTION = "Foo";

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void save() throws Exception {
        UnitOfMeasure category = new UnitOfMeasure();

        category.setDescription(DESCRIPTION);
        unitOfMeasureReactiveRepository.save(category).block();

        assertEquals(Long.valueOf(1L), unitOfMeasureReactiveRepository.count().block());
    }

    @Test
    public void findByDescription() throws Exception {
        UnitOfMeasure category = new UnitOfMeasure();

        category.setDescription(DESCRIPTION);
        unitOfMeasureReactiveRepository.save(category).block();

        UnitOfMeasure fetchedUnitOfMeasure = unitOfMeasureReactiveRepository.findByDescription(DESCRIPTION).block();

        assertNotNull(fetchedUnitOfMeasure.getId());
    }
}
