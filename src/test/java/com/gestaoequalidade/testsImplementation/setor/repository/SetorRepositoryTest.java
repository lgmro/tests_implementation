package com.gestaoequalidade.testsImplementation.setor.repository;

import com.gestaoequalidade.testsImplementation.setor.entity.Setor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class SetorRepositoryTest {
    @InjectMocks
    private SetorRepository setorRepository;

    private Setor setor = new Setor();

    @BeforeEach
    public void setUp() {
        setor.setId(1L);
        setor.setNome(SETOR_NAME_RH);
    }

    @Test
    void dado_que_nao_ha_setor_cadastrado_quando_chamar_listar_setores_deve_retornar_lista_vazia() {
        // Act
        List<Setor> setores = setorRepository.listarSetores();

        // Assert
        assertNotNull(setores);
        assertEquals(0, setores.size());
    }

    @Test
    void dado_que_nao_ha_setor_cadastrado_quando_chamar_criar_setor_com_um_setor_deve_salvar_novo_setor() {
        // Act
        Setor result = setorRepository.criarSetor(setor);

        // Assert
        assertNotNull(result);
        assertEquals(setor.getId(), result.getId());
        assertEquals(setor.getNome(), result.getNome());

        List<Setor> setores = setorRepository.listarSetores();
        assertEquals(1, setores.size());
        assertTrue(setores.contains(result));
    }

    @Test
    public void dado_que_setor_desejado_existe_quando_chamar_buscar_setor_por_id_deve_retornar_setor_corretamente() {
        // Arrange
        setorRepository.criarSetor(setor);

        // Act
        Setor result = setorRepository.buscarSetorPorId(setor.getId());

        // Assert
        assertNotNull(result);
        assertEquals(setor.getNome(), result.getNome());
    }

    @Test
    public void dado_que_setor_desejado_nao_for_encontrado_quando_chamar_buscar_setor_por_id_deve_retornar_null() {
        // Arrange
        setorRepository.criarSetor(setor);

        // Act
        Setor result = setorRepository.buscarSetorPorId(ID_INEXISTENTE);

        // Assert
        assertNull(result);
    }

    @Test
    public void dado_que_setor_desejado_existe_quando_chamar_atualizar_setor_deve_atualizar_setor_corretamente() {
        // Arrange
        setorRepository.criarSetor(setor);
        Setor setorAtualizado = new Setor();
        setorAtualizado.setNome(SETOR_NAME_FINANCEIRO);

        // Act
        Setor result = setorRepository.atualizarSetor(setor.getId(), setorAtualizado);

        // Assert
        Setor setorModificado = setorRepository.buscarSetorPorId(setor.getId());

        assertNotNull(setorModificado);
        assertEquals(result.getId(), setorModificado.getId());
        assertEquals(result.getNome(), setorModificado.getNome());
    }

    @Test
    public void dado_que_setor_desejado_nao_existe_quando_chamar_atualizar_setor_deve_retornar_null() {
        // Arrange
        setorRepository.criarSetor(setor);
        Setor setorAtualizado = new Setor();
        setorAtualizado.setNome(SETOR_NAME_FINANCEIRO);

        // Act
        Setor result = setorRepository.atualizarSetor(ID_INEXISTENTE, setorAtualizado);

        // Assert
        assertNull(result);
    }

    @Test
    public void dado_que_setor_desejado_existe_quando_chamar_excluir_setor_deve_excluir_setor_corretamente() {
        // Arrange
        Setor setorCriado = setorRepository.criarSetor(setor);

        // Act
        boolean result = setorRepository.excluirSetor(setorCriado.getId());

        // Assert
        assertTrue(result);
        List<Setor> setores = setorRepository.listarSetores();
        assertEquals(0, setores.size());
    }

    @Test
    public void dado_que_setor_desejado_nao_existe_quando_chamar_excluir_setor_deve_retornar_false() {
        // Arrange
        Setor setorCriado = setorRepository.criarSetor(setor);

        // Act
        boolean result = setorRepository.excluirSetor(ID_INEXISTENTE);

        // Assert
        assertFalse(result);
        List<Setor> setores = setorRepository.listarSetores();
        assertEquals(1, setores.size());
    }

    private static final String SETOR_NAME_RH = "RH";
    private static final String SETOR_NAME_FINANCEIRO = "Financeiro";
    private static final Long ID_INEXISTENTE = 2L;
}
