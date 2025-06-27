package aiss.GitMiner;

import aiss.GitMiner.controller.IssueController;
import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.Issue;
import aiss.GitMiner.model.User;
import aiss.GitMiner.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueController.class)
public class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueRepository issueRepository;

    private Issue issueEjemplo;
    private Comment comentarioEjemplo;
    private User usuarioEjemplo;

    @BeforeEach
    public void inicializar() {
        usuarioEjemplo = new User();
        usuarioEjemplo.setId("usuario123");
        usuarioEjemplo.setName("Alicia");

        comentarioEjemplo = new Comment();
        comentarioEjemplo.setId("comentario1");
        comentarioEjemplo.setBody("Este es un comentario");

        issueEjemplo = new Issue();
        issueEjemplo.setId("issue123");
        issueEjemplo.setTitle("Título del issue");
        issueEjemplo.setDescription("Descripción del issue");
        issueEjemplo.setState("abierto");
        issueEjemplo.setAuthor(usuarioEjemplo);
        issueEjemplo.setComments(Collections.singletonList(comentarioEjemplo));
    }

    @Test
    public void getAllIssues_returnsList() throws Exception {
        when(issueRepository.findAll()).thenReturn(Arrays.asList(issueEjemplo));

        mockMvc.perform(get("/gitminer/issues")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("issue123")))
                .andExpect(jsonPath("$[0].title", is("Título del issue")))
                .andExpect(jsonPath("$[0].state", is("abierto")))
                .andExpect(jsonPath("$[0].author.id", is("usuario123")))
                .andExpect(jsonPath("$[0].comments", hasSize(1)))
                .andExpect(jsonPath("$[0].comments[0].id", is("comentario1")));
    }

    @Test
    public void getIssueById_returnsIssue() throws Exception {
        when(issueRepository.findById("issue123")).thenReturn(Optional.of(issueEjemplo));

        mockMvc.perform(get("/gitminer/issues/issue123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("issue123")))
                .andExpect(jsonPath("$.title", is("Título del issue")))
                .andExpect(jsonPath("$.state", is("abierto")))
                .andExpect(jsonPath("$.author.id", is("usuario123")))
                .andExpect(jsonPath("$.comments", hasSize(1)));
    }

    @Test
    public void getCommentsFromIssue_returnsComments() throws Exception {
        when(issueRepository.findById("issue123")).thenReturn(Optional.of(issueEjemplo));

        mockMvc.perform(get("/gitminer/issues/issue123/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("comentario1")))
                .andExpect(jsonPath("$[0].body", is("Este es un comentario")));
    }

    @Test
    public void getCommentsFromNonExistentIssue_returns404() throws Exception {
        when(issueRepository.findById("noexiste")).thenReturn(Optional.empty());

        mockMvc.perform(get("/gitminer/issues/noexiste/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
