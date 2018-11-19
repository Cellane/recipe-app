package net.milanvit.recipeapp.service;

import lombok.extern.slf4j.Slf4j;
import net.milanvit.recipeapp.domain.Recipe;
import net.milanvit.recipeapp.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        log.debug("I’m in the service!");
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }
}
