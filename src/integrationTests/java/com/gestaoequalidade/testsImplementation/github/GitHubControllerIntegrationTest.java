package com.gestaoequalidade.testsImplementation.github;

import com.gestaoequalidade.testsImplementation.github.controller.GitHubController;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubRepository;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubUser;
import com.gestaoequalidade.testsImplementation.setor.component.UserInputProvider;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubControllerIntegrationTest {
    @TestConfiguration
    public static class Configuration {
        @MockBean
        private UserInputProvider userInputProvider;


        @PostConstruct
        public void initMock(){
            when(userInputProvider.getUserInput()).thenReturn("0");
        }
    }

    @Autowired
    private GitHubController gitHubController;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController)
                .alwaysDo(print())
                .build();

       mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void dado_um_username_quando_chamar_end_point_deve_retornar_dados_do_usuario_e_seus_repositorios() throws Exception {
        // Arrange
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setName(USERNAME);
        gitHubUser.setLogin(USERNAME);
        gitHubUser.setBio(BIO);
        gitHubUser.setCreatedAt(DATA_CRIACAO);
        gitHubUser.setLocation(LOCALIZACAO);
        String responseBodyUser = objectMapper.writeValueAsString(gitHubUser);

        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName(REPOSITORIO_NOME);
        gitHubRepository.setHtmlUrl(HTML_URL);
        gitHubRepository.setDescription(DESCRICAO);
        GitHubRepository[] gitHubRepositoryArrayList = {gitHubRepository};
        String responseBodyRepo = objectMapper.writeValueAsString(gitHubRepositoryArrayList);

        // Mock
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(GITHUB_API_URL + USERNAME))
                .andRespond(MockRestResponseCreators.withSuccess(responseBodyUser,MediaType.APPLICATION_JSON));

        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(GITHUB_API_URL + USERNAME + "/repos"))
                .andRespond(MockRestResponseCreators.withSuccess(responseBodyRepo,MediaType.APPLICATION_JSON));

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", USERNAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(EXPECTED_JSON));
    }
    private static final String USERNAME = "username";
    private static final String REPOSITORIO_NOME = "repositorio";
    private static final String EXPECTED_JSON = "{'user':{'login':'username','name':'username','bio':'bio_user','location':localizacao,'created_at':19674},'repositories':[{'name':'repositorio','description':'descricao','htmlUrl':'htmlurl'}]}";
    private static final String BIO = "bio_user";
    private static final String LOCALIZACAO = "localizacao";
    private static final String DESCRICAO = "descricao";
    private static final Date DATA_CRIACAO = new Date(LocalDate.of(2023, 11, 13).toEpochDay());
    private static final String HTML_URL = "htmlurl";
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
}
