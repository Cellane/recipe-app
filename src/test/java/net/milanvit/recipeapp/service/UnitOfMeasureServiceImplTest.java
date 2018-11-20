package net.milanvit.recipeapp.service;

import net.milanvit.recipeapp.command.UnitOfMeasureCommand;
import net.milanvit.recipeapp.converter.UnitOfMeasureToUnitOfMeasureCommand;
import net.milanvit.recipeapp.domain.UnitOfMeasure;
import net.milanvit.recipeapp.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    UnitOfMeasureService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void findAll() {
        Set<UnitOfMeasure> unitsOfMeasure = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        UnitOfMeasure uom2 = new UnitOfMeasure();

        uom1.setId(1L);
        uom2.setId(2L);

        unitsOfMeasure.add(uom1);
        unitsOfMeasure.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitsOfMeasure);

        Set<UnitOfMeasureCommand> commands = service.findAll();

        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository).findAll();
    }
}
