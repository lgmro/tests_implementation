package com.gestaoequalidade.testsImplementation.setor.component;

import com.gestaoequalidade.testsImplementation.setor.controller.SetorController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ConsoleMenuHandlerTest {

    @InjectMocks
    private ConsoleMenuHandler consoleMenuHandler;
    @Mock
    private SetorController setorController;
    @Mock
    private UserInputProvider userInputProvider;


    @Test
    void dada_opcao_um_quando_executar_handle_menu_deve_chamar_metodo_criar_setor() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("1", "0");

        // Act
        consoleMenuHandler.handleMenu(setorController);

        // Verify
        verify(setorController, times(1)).criarSetor();
        verify(userInputProvider, times(2)).getUserInput();
        verify(userInputProvider, times(1)).setUserOutput("Saindo...");
    }

    @Test
    void dada_opcao_dois_quando_executar_handle_menu_deve_chamar_metodo_editar_setor() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("2", "0");

        // Act
        consoleMenuHandler.handleMenu(setorController);

        // Verify
        verify(setorController, times(1)).editarSetor();
        verify(userInputProvider, times(2)).getUserInput();
        verify(userInputProvider, times(1)).setUserOutput("Saindo...");
    }

    @Test
    void dada_opcao_tres_quando_executar_handle_menu_deve_chamar_metodo_excluir_setor() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("3", "0");

        // Act
        consoleMenuHandler.handleMenu(setorController);

        // Verify
        verify(setorController, times(1)).excluirSetor();
        verify(userInputProvider, times(2)).getUserInput();
        verify(userInputProvider, times(1)).setUserOutput("Saindo...");
    }

    @Test
    void dada_opcao_zero_quando_executar_handle_menu_deve_sair_do_loop() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("0");

        // Act
        consoleMenuHandler.handleMenu(setorController);

        // Verify
        verify(userInputProvider, times(1)).getUserInput();
        verify(userInputProvider, times(1)).setUserOutput("Saindo...");
    }

    @Test
    void dado_ppcao_inexistente_quando_executar_handle_menu_deve_retornar_mensagem_opcao_invalida() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("-1", "0");

        // Act
        consoleMenuHandler.handleMenu(setorController);

        // Verify
        verify(userInputProvider, times(2)).getUserInput();
        verify(userInputProvider, times(1)).setUserOutput("Opção inválida.");
        verify(userInputProvider, times(1)).setUserOutput("Saindo...");
    }

}