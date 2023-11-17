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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtualizarSetorIntegrationTest {
    @TestConfiguration
    public static class Configuration {
        @MockBean
        private UserInputProvider userInputProvider;

        @PostConstruct
        public void initMock() {
            when(userInputProvider.getUserInput()).thenReturn(
                    Constants.OPCOES[1], Constants.SETOR_RH_NAME,
                    Constants.OPCOES[2], Constants.OPCOES[1], Constants.SETOR_FINANCEIRO_NAME,
                    Constants.RESPOSTA_SIM, Constants.OPCOES[0]);
        }
    }

    @Autowired
    private SetorRepository setorRepository;

    @Test
    void dado_usuario_escolhe_atualizar_setor_quando_informa_novo_nome_repository_deve_atualizar_setor_corretamente() {
        // Assert
        assertEquals(1, setorRepository.listarSetores().size());
        assertEquals(Constants.SETOR_FINANCEIRO_NAME, setorRepository.listarSetores().get(0).getNome());

    }
}
