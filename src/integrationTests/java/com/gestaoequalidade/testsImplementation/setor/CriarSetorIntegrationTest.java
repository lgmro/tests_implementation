package com.gestaoequalidade.testsImplementation.setor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoequalidade.testsImplementation.github.controller.GitHubController;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubRepository;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubUser;
import com.gestaoequalidade.testsImplementation.setor.component.ConsoleMenuHandler;
import com.gestaoequalidade.testsImplementation.setor.component.UserInputProvider;
import com.gestaoequalidade.testsImplementation.setor.controller.SetorController;
import com.gestaoequalidade.testsImplementation.setor.repository.SetorRepository;
import com.gestaoequalidade.testsImplementation.setor.service.SetorService;
import com.gestaoequalidade.testsImplementation.utils.Constants;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriarSetorIntegrationTest {
    @TestConfiguration
    public static class Configuration {
        @MockBean
        private UserInputProvider userInputProvider;

        @PostConstruct
        public void initMock(){
            when(userInputProvider.getUserInput()).thenReturn(Constants.OPCOES[1], Constants.SETOR_RH_NAME, Constants.OPCOES[0]);
        }
    }

    @Autowired
    private SetorRepository setorRepository;

    @Test
    void dado_usuario_escolhe_criar_setor_quando_informa_um_nome_repository_deve_armazenar_setor_corretamente()  {
        // Assert
        assertEquals(1, setorRepository.listarSetores().size());
        assertEquals(Constants.SETOR_RH_NAME, setorRepository.listarSetores().get(0).getNome());

    }
}
