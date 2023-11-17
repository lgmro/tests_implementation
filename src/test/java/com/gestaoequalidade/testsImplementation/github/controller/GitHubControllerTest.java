package com.gestaoequalidade.testsImplementation.github.controller;

import com.gestaoequalidade.testsImplementation.github.entity.GitHubRepository;
import com.gestaoequalidade.testsImplementation.github.entity.GitHubUser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class GitHubControllerTest {
    @InjectMocks
    private GitHubController gitHubController;

    @Mock
    private RestTemplate restTemplate;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(gitHubController)
                .alwaysDo(print())
                .build();
    }

    @Test
    void dado_um_username_quando_chamar_getUserAndRepos_deve_retornar_dados_do_usuario_e_seus_repositorios() throws Exception {
        // Arrange
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setLogin(USERNAME);
        gitHubUser.setName(USERNAME);
        gitHubUser.setBio(BIO);
        gitHubUser.setLocation(LOCALIZACAO);
        gitHubUser.setCreatedAt(DATA_CRIACAO);

        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName(USERNAME);
        gitHubRepository.setDescription(DESCRICAO);
        gitHubRepository.setHtmlUrl(HTML_URL);

        GitHubRepository[] gitHubRepositoryArrayList = {gitHubRepository};

        String expectedJson = "{'user':{'login':'username','name':'username','bio':'bio_user','location':localizacao,'created_at':19674},'repositories':[{'name':'username','description':'descricao','htmlUrl':'htmlurl'}]}";


        // Mock
        when(restTemplate.getForObject(GITHUB_API_URL + USERNAME, GitHubUser.class)).thenReturn(gitHubUser);
        when(restTemplate.getForObject(GITHUB_API_URL + USERNAME + REPOSITORIO, GitHubRepository[].class)).thenReturn(gitHubRepositoryArrayList);

        // Act and Asserts
        mockMvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", USERNAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(expectedJson));

        // Verify Mocks
        verify(restTemplate, times(1)).getForObject(GITHUB_API_URL + USERNAME, GitHubUser.class);
        verify(restTemplate, times(1)).getForObject(GITHUB_API_URL + USERNAME + REPOSITORIO, GitHubRepository[].class);
    }

    @Test
    void dado_um_username_quando_chamar_getUserAndRepos_e_ocorrer_uma_exception_deve_retornar_internal_server_error() throws Exception {
        // Mock
        when(restTemplate.getForObject(GITHUB_API_URL + USERNAME, GitHubUser.class)).thenThrow(new RestClientException(INTERNAL_SERVER_ERROR_MESSAGE));

        // Act and Asserts
        mockMvc.perform(MockMvcRequestBuilders.get("/github/user/{username}", USERNAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(content().string(Matchers.blankOrNullString()));

        // Verify Mocks
        verify(restTemplate, times(1)).getForObject(GITHUB_API_URL + USERNAME, GitHubUser.class);
    }
    private static final String USERNAME = "username";
    private static final String BIO = "bio_user";
    private static final String LOCALIZACAO = "localizacao";
    private static final String DESCRICAO = "descricao";
    private static final Date DATA_CRIACAO = new Date(LocalDate.of(2023, 11, 13).toEpochDay());
    private static final String HTML_URL = "htmlurl";
    private static final String GITHUB_API_URL = "https://api.github.com/users/";
    private static final String REPOSITORIO = "/repos";
    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "internal_server_error";
}
