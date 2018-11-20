package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAll();
}
