package aiss.GitMiner;

import aiss.GitMiner.controller.ProjectController;
import aiss.GitMiner.model.*;
import aiss.GitMiner.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository projectRepository;

    private ObjectMapper objectMapper;

    private Project proyectoEjemplo;

    @BeforeEach
    public void inicializar() {
        objectMapper = new ObjectMapper();

        Commit commit = new Commit();
        commit.setId("commit123");
        commit.setTitle("Commit inicial");
        commit.setMessage("Este es el commit de fusión inicial");
        commit.setAuthorName("Alicia");
        commit.setAuthorEmail("alicia@ejemplo.com");

        Comment comentario = new Comment();
        comentario.setId("comentario1");
        comentario.setBody("Este es un comentario");
        User usuario = new User();
        usuario.setId("usuario123");
        usuario.setName("Alicia");
        comentario.setAuthor(usuario);

        Issue issue = new Issue();
        issue.setId("issue123");
        issue.setTitle("Título del issue");
        issue.setState("abierto");
        issue.setAuthor(usuario);
        issue.setAssignee(usuario);
        issue.setComments(Collections.singletonList(comentario));

        proyectoEjemplo = new Project();
        proyectoEjemplo.setId("proyecto123");
        proyectoEjemplo.setName("Proyecto de prueba");
        proyectoEjemplo.setWebUrl("http://ejemplo.com/proyecto123");
        proyectoEjemplo.setCommits(Arrays.asList(commit));
        proyectoEjemplo.setIssues(Arrays.asList(issue));
    }

    @Test
    public void createProject_returnsCreatedProject() throws Exception {
        when(projectRepository.save(any(Project.class))).thenReturn(proyectoEjemplo);

        String json = objectMapper.writeValueAsString(proyectoEjemplo);

        mockMvc.perform(post("/gitminer/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("proyecto123"))
                .andExpect(jsonPath("$.name").value("Proyecto de prueba"));
    }

    @Test
    public void getAllProjects_returnsList() throws Exception {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(proyectoEjemplo));

        mockMvc.perform(get("/gitminer/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("proyecto123"))
                .andExpect(jsonPath("$[0].name").value("Proyecto de prueba"));
    }

    @Test
    public void getProjectById_returnsProject() throws Exception {
        when(projectRepository.findById("proyecto123")).thenReturn(java.util.Optional.of(proyectoEjemplo));

        mockMvc.perform(get("/gitminer/projects/proyecto123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("proyecto123"))
                .andExpect(jsonPath("$.name").value("Proyecto de prueba"));
    }

}
