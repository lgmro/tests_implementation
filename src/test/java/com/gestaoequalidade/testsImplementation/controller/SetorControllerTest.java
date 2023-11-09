package com.gestaoequalidade.testsImplementation.controller;

import com.gestaoequalidade.testsImplementation.setor.component.UserInputProvider;
import com.gestaoequalidade.testsImplementation.setor.controller.SetorController;
import com.gestaoequalidade.testsImplementation.setor.entity.Setor;
import com.gestaoequalidade.testsImplementation.setor.service.SetorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SetorControllerTest {
    @InjectMocks
    private SetorController setorController;

    @Mock
    private SetorService setorService;

    @Mock
    private UserInputProvider userInputProvider;

    @Test
    void dadoQueExisteSetoresCadastradosQuandoChamarListarSetoresDeveRetornarAListaDeSetores() {
        // Arrange
        Setor setor = new Setor();
        setor.setId(1L);
        setor.setNome("RH");
        List<Setor> setores = new ArrayList<>();
        setores.add(setor);

        // Mock
        when(setorService.listarSetores()).thenReturn(setores);

        // Act
        List<Setor> result = setorController.listarSetores();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        // Verify
        verify(userInputProvider, times(2)).setUserOutput(DIVISOR);
        verify(userInputProvider, atLeastOnce()).setUserOutput(TITULO_SETORES);
        verify(userInputProvider, atLeastOnce()).setUserOutput(setor.getId() + ". " + setor.getNome());
    }

    public static final String TITULO_SETORES = "Lista dos setores:\n";
    public static final String DIVISOR = "------------------------------------------------";
}
