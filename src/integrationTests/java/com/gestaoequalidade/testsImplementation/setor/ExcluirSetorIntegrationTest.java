package com.gestaoequalidade.testsImplementation.setor;

import com.gestaoequalidade.testsImplementation.setor.component.UserInputProvider;
import com.gestaoequalidade.testsImplementation.setor.repository.SetorRepository;
import com.gestaoequalidade.testsImplementation.utils.Constants;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExcluirSetorIntegrationTest {
    @TestConfiguration
    public static class Configuration {
        @MockBean
        private UserInputProvider userInputProvider;

        @PostConstruct
        public void initMock() {
            when(userInputProvider.getUserInput()).thenReturn(
                    Constants.OPCOES[1], Constants.SETOR_RH_NAME,
                    Constants.OPCOES[3], Constants.OPCOES[1],
                    Constants.RESPOSTA_SIM, Constants.OPCOES[0]);
        }
    }

    @Autowired
    private SetorRepository setorRepository;

    @Test
    void dado_usuario_escolhe_excluir_setor_quando_informa_o_id_do_setor_repository_deve_excluir_setor_corretamente() {
        // Assert
        assertEquals(0, setorRepository.listarSetores().size());
    }
}
