package net.milanvit.recipeapp.repository.reactive;

import net.milanvit.recipeapp.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void recipeSave() throws Exception {
        Recipe recipe = new Recipe();

        recipe.setDescription("Yummy");
        recipeReactiveRepository.save(recipe).block();

        assertEquals(Long.valueOf(1L), recipeReactiveRepository.count().block());
    }
}
