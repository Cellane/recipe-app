package net.milanvit.recipeapp.repository.reactive;

import net.milanvit.recipeapp.domain.Category;
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
public class CategoryReactiveRepositoryTest {
    private static final String DESCRIPTION = "Foo";

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void save() throws Exception {
        Category category = new Category();

        category.setDescription(DESCRIPTION);
        categoryReactiveRepository.save(category).block();

        assertEquals(Long.valueOf(1L), categoryReactiveRepository.count().block());
    }

    @Test
    public void findByDescription() throws Exception {
        Category category = new Category();

        category.setDescription(DESCRIPTION);
        categoryReactiveRepository.save(category).block();

        Category fetchedCategory = categoryReactiveRepository.findByDescription(DESCRIPTION).block();

        assertNotNull(fetchedCategory.getId());
    }
}
