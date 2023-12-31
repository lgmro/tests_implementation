package com.gestaoequalidade.testsImplementation.setor.controller;

import com.gestaoequalidade.testsImplementation.setor.component.UserInputProvider;
import com.gestaoequalidade.testsImplementation.setor.entity.Setor;
import com.gestaoequalidade.testsImplementation.setor.service.SetorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class SetorControllerTest {
    @InjectMocks
    private SetorController setorController;

    @Mock
    private SetorService setorService;

    @Mock
    private UserInputProvider userInputProvider;

    @Captor
    private ArgumentCaptor<Setor> setorCaptor;

    @Captor
    private ArgumentCaptor<Long> idSetorCaptor;

    private Setor setor = new Setor();

    @BeforeEach
    void setup() {
        setor.setId(1L);
        setor.setNome(SETOR_NAME_RH);

    }

    @Test
    void dado_que_existe_setores_cadastrados_quando_chamar_listar_setores_deve_retornar_a_lista_de_setores() {
        // Arrange
        List<Setor> setores = new ArrayList<>();
        setores.add(setor);

        // Mock
        when(setorService.listarSetores()).thenReturn(setores);

        // Act
        List<Setor> result = setorController.listarSetores();

        // Verify Mocks
        verify(userInputProvider, times(2)).setUserOutput(DIVISOR);
        verify(userInputProvider, times(1)).setUserOutput(TITULO_SETORES_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(setor.getId() + ". " + setor.getNome());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(setores.get(0).getId(), result.get(0).getId());
        assertEquals(setores.get(0).getNome(), result.get(0).getNome());
    }

    @Test
    void dado_que_usuario_quer_criar_um_setor_quando_chamar_criar_setor_deve_salvar_o_setor() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn(SETOR_NAME_RH);

        // Act
        setorController.criarSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(TITULO_NOME_SETOR_MESSAGEM);
        verify(setorService, times(1)).criarSetor(setorCaptor.capture());
        verify(userInputProvider, times(1)).setUserOutput(SETOR_CRIADO_COM_SUCESSO_MESSAGEM);

        // Assert
        assertEquals(SETOR_NAME_RH, setorCaptor.getValue().getNome());
    }

    @Test
    void dado_que_usuario_quer_editar_um_setor_quando_chamar_editar_setor_e_esuario_confirmar_alteracao_deve_atualizar_setor_corretamente() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("1", SETOR_NAME_FINANCEIRO, RESPOSTA_SIM);
        when(setorService.buscarSetorPorId(setor.getId())).thenReturn(setor);

        // Act
        setorController.editarSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_NOVO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(NOME_NOVO_SETOR_MESSAGEM);
        verify(userInputProvider, times(2)).setUserOutput(DIVISOR);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_ALTERACAO_NOME_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_NOVO_NOME_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(OPCOES_CONFIRMACAO_ALTERACAO_EXLUSAO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(setor.getId() + ". " + setor.getNome());
        verify(userInputProvider, times(1)).setUserOutput(setor.getId() + ". " + SETOR_NAME_FINANCEIRO);
        verify(setorService, times(1)).atualizarSetor(idSetorCaptor.capture(), setorCaptor.capture());
        verify(userInputProvider, times(1)).setUserOutput(ALTERACAO_NOME_SETOR_BEM_SUCEDIDA_MESSAGEM);


        // Assert
        assertEquals(setor.getId(), idSetorCaptor.getValue());
        assertEquals(SETOR_NAME_FINANCEIRO, setorCaptor.getValue().getNome());
    }

    @Test
    void dado_que_usuario_quer_editar_um_setor_quando_chamar_editar_setor_e_usuario_cancelar_alteracao_deve_interromper_alteracao() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("1", SETOR_NAME_FINANCEIRO, RESPOSTA_NAO);
        when(setorService.buscarSetorPorId(setor.getId())).thenReturn(setor);

        // Act
        setorController.editarSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_NOVO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(NOME_NOVO_SETOR_MESSAGEM);
        verify(userInputProvider, times(2)).setUserOutput(DIVISOR);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_ALTERACAO_NOME_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_NOVO_NOME_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(OPCOES_CONFIRMACAO_ALTERACAO_EXLUSAO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(setor.getId() + ". " + setor.getNome());
        verify(userInputProvider, times(1)).setUserOutput(setor.getId() + ". " + SETOR_NAME_FINANCEIRO);
        verify(userInputProvider, times(1)).setUserOutput(ALTERACAO_NOME_SETOR_CANCELADA_MESSAGEM);
    }

    @Test
    void dado_que_usuario_quer_editar_um_setor_quando_setor_desejado_nao_for_encontrado_deve_exibir_uma_messagem_de_setor_nao_encontrado() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("0");
        when(setorService.buscarSetorPorId(0L)).thenReturn(null);

        // Act
        setorController.editarSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_NOVO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(SETOR_NAO_ENCONTRADO_MESSAGEM);
    }

    @Test
    void dado_que_usuario_quer_excluir_um_setor_quando_usuario_confirmar_a_exclusao_deve_excluir_setor_apropriadamente() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("1", RESPOSTA_SIM);
        when(setorService.buscarSetorPorId(setor.getId())).thenReturn(setor);

        // Act
        setorController.excluirSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_SETOR_A_EXCLUIR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_EXCLUSAO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(OPCOES_CONFIRMACAO_ALTERACAO_EXLUSAO_SETOR_MESSAGEM);
        verify(setorService, times(1)).excluirSetor(idSetorCaptor.capture());
        verify(userInputProvider, times(1)).setUserOutput(EXCLUSAO_SETOR_BEM_SUCEDIDA_MESSAGEM);

        // Assert
        assertEquals(setor.getId(), idSetorCaptor.getValue());
    }

    @Test
    void dado_que_usuario_quer_excluir_um_setor_quando_usuario_cancelar_a_exclusao_deve_mostrar_messagem_de_exlusao_cancelada() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("1", RESPOSTA_NAO);
        when(setorService.buscarSetorPorId(setor.getId())).thenReturn(setor);

        // Act
        setorController.excluirSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_SETOR_A_EXCLUIR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(CONFIRMACAO_EXCLUSAO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(OPCOES_CONFIRMACAO_ALTERACAO_EXLUSAO_SETOR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(EXCLUSAO_SETOR_CANCELADA_MESSAGEM);
    }

    @Test
    void dado_que_usuario_quer_excluir_um_setor_quando_setor_desejado_nao_for_encontrado_deve_exibir_uma_messagem_de_setor_nao_encontrado() {
        // Mock
        when(userInputProvider.getUserInput()).thenReturn("0");
        when(setorService.buscarSetorPorId(0L)).thenReturn(null);

        // Act
        setorController.excluirSetor();

        // Verify Mocks
        verify(userInputProvider, times(1)).setUserOutput(ID_SETOR_A_EXCLUIR_MESSAGEM);
        verify(userInputProvider, times(1)).setUserOutput(SETOR_NAO_ENCONTRADO_MESSAGEM);
    }

    private static final String TITULO_SETORES_MESSAGEM = "Lista dos setores:\n";
    private static final String DIVISOR = "------------------------------------------------";
    private static final String SETOR_NAME_RH = "RH";
    private static final String SETOR_NAME_FINANCEIRO = "Financeiro";
    private static final String TITULO_NOME_SETOR_MESSAGEM = "Nome do setor:";
    private static final String SETOR_CRIADO_COM_SUCESSO_MESSAGEM = "Setor criado com sucesso!";
    private static final String ID_NOVO_SETOR_MESSAGEM = "ID do setor a ser editado:";
    private static final String NOME_NOVO_SETOR_MESSAGEM = "Novo nome do setor:";
    private static final String CONFIRMACAO_ALTERACAO_NOME_SETOR_MESSAGEM = "Você tem certeza que quer alterar o Setor de:";
    private static final String CONFIRMACAO_NOVO_NOME_SETOR_MESSAGEM = "Para:";
    private static final String OPCOES_CONFIRMACAO_ALTERACAO_EXLUSAO_SETOR_MESSAGEM = "S/N?";
    private static final String ALTERACAO_NOME_SETOR_CANCELADA_MESSAGEM = "Atualização do setor foi cancelada.";
    private static final String ALTERACAO_NOME_SETOR_BEM_SUCEDIDA_MESSAGEM = "Setor atualizado com sucesso!";
    private static final String SETOR_NAO_ENCONTRADO_MESSAGEM = "Setor não encontrado.";
    private static final String ID_SETOR_A_EXCLUIR_MESSAGEM = "ID do setor a ser excluído:";
    private static final String CONFIRMACAO_EXCLUSAO_SETOR_MESSAGEM = "Você tem certeza que quer excluir o Setor:";
    private static final String EXCLUSAO_SETOR_BEM_SUCEDIDA_MESSAGEM = "Setor excluído com sucesso!";
    private static final String EXCLUSAO_SETOR_CANCELADA_MESSAGEM = "Exclusão do setor foi cancelada.";
    private static final String RESPOSTA_SIM = "S";
    private static final String RESPOSTA_NAO = "n";
}
