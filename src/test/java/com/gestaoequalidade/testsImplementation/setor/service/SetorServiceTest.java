package com.gestaoequalidade.testsImplementation.setor.service;

import com.gestaoequalidade.testsImplementation.setor.entity.Setor;
import com.gestaoequalidade.testsImplementation.setor.repository.SetorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SetorServiceTest {
    @Mock
    private SetorRepository setorRepository;

    @InjectMocks
    private SetorService setorService;

    @Test
    void dado_repository_tem_setores_cadastrados_quando_executar_listar_setores_deve_retornar_setores_cadastrados() {
        // Arrange: Criando alguns dados fictícios para o teste
        List<Setor> setoresFicticios = new ArrayList<>();

        Setor setorA = new Setor();
        setorA.setNome("Setor A");
        setoresFicticios.add(setorA);

        Setor setorB = new Setor();
        setorB.setNome("Setor B");
        setoresFicticios.add(setorB);

        // Mock: Configurando o comportamento simulado do repository
        when(setorRepository.listarSetores()).thenReturn(setoresFicticios);

        // Act: Chamando o método do serviço
        List<Setor> setores = setorService.listarSetores();

        // Verify: Verificando se o método do repository foi chamado
        verify(setorRepository, times(1)).listarSetores();

        // Assert: Faça as asserções necessárias
        assertEquals(2, setores.size());
        assertEquals("Setor A", setores.get(0).getNome());
        assertEquals("Setor B", setores.get(1).getNome());
    }

    @Test
    void dado_repository_nao_tem_setores_cadastrados_quando_executar_criar_setor_deve_setor_ser_cadastrado_corretamente() {
        // Arrange: Crie um objeto Setor fictício para o teste
        Setor newSetor = new Setor();
        newSetor.setId(1L);
        newSetor.setNome("Ti");

        // Mock: Configurando o comportamento simulado do repository
        when(setorRepository.criarSetor(newSetor)).thenReturn(newSetor);

        // Act: Chamando o método do serviço
        Setor setorCriado = setorService.criarSetor(newSetor);

        // Verify: Verifique se o método do repository foi chamado com os argumentos corretos
        verify(setorRepository, times(1)).criarSetor(newSetor);

        // Assert: Faça as asserções necessárias
        assertEquals(1L, setorCriado.getId());
        assertEquals("Ti", setorCriado.getNome());
    }

    @Test
    void dado_repository_tem_setores_cadastrados_quando_executar_buscar_setor_deve_retornar_setor_buscado() {
        // Arrange: Crie um objeto setor fictício para o teste
        Setor setorEncontrado = new Setor();
        setorEncontrado.setId(1L);
        setorEncontrado.setNome("Ti");

        // Mock: Configure o comportamento simulado do repository
        when(setorRepository.buscarSetorPorId(setorEncontrado.getId())).thenReturn(setorEncontrado);

        // Act: Chamando o método do serviço
        Setor setorRetornado = setorService.buscarSetorPorId(setorEncontrado.getId());

        // Verify: Verifique se o método do repository foi chamado com os argumentos corretos
        verify(setorRepository, times(1)).buscarSetorPorId(setorEncontrado.getId());

        // Assert: Faça as asserções necessárias
        assertEquals(1L, setorRetornado.getId());
        assertEquals("Ti", setorRetornado.getNome());
    }


    @Test
    void dado_repository_tem_setor_cadastrado_quando_executar_atualizar_setor_deve_retornar_setor_atualizado() {
        // Arrange: Crie um objeto Setor fictício para o teste
        Setor  setorAtualizado = new Setor();
        setorAtualizado.setId(1L);
        setorAtualizado.setNome("Setor Atualizado");

        // Mock: Configure o comportamento simulado do repository
        when(setorRepository.atualizarSetor(setorAtualizado.getId(), setorAtualizado)).thenReturn(setorAtualizado);

        // Act: Chamando o método do serviço
        Setor setorRetornado = setorService.atualizarSetor(setorAtualizado.getId(), setorAtualizado);

        // Verify: Verifique se o método do repository foi chamado com os argumentos corretos
        verify(setorRepository, times(1)).atualizarSetor(setorAtualizado.getId(), setorAtualizado);

        // Assert: Faça as asserções necessárias
        assertEquals(1L, setorRetornado.getId());
        assertEquals("Setor Atualizado", setorRetornado.getNome());
    }



    @Test
    void dado_repository_tem_o_setor_desejado_quando_executar_excluir_setor_deve_excluir_setor() {
        // Arrange: Crie um ID fictício para o teste
        Long idParaExcluir = 1L;

        // Mock: Configure o comportamento simulado do repository
        when(setorRepository.excluirSetor(idParaExcluir)).thenReturn(true);

        // Act: Chamando o método do serviço
        boolean resultadoExclusao = setorService.excluirSetor(idParaExcluir);

        // Verifique se o método do repository foi chamado com os argumentos corretos
        verify(setorRepository, times(1)).excluirSetor(idParaExcluir);

        // Assert
        assertTrue(resultadoExclusao);
    }


    @Test
    void dado_repository_nao_tem_o_setor_desejado_quando_executar_excluir_setor_deve_retornar_falso() {
        // Arrange: Crie um ID fictício para o teste
        Long idParaExcluir = 1L;

        // Mock: Configure o comportamento simulado do repository para indicar falha
        when(setorRepository.excluirSetor(idParaExcluir)).thenReturn(false);

        // Act: Chame o método do serviço que você deseja testar
        boolean resultadoExclusao = setorService.excluirSetor(idParaExcluir);

        // Verify: Verifique se o método do repository foi chamado com os argumentos corretos
        verify(setorRepository, times(1)).excluirSetor(idParaExcluir);

        // Assert
        assertFalse(resultadoExclusao);
    }
}
