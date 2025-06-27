package ProyectoAiss.BitBucket;

import ProyectoAiss.BitBucket.controller.BitBucketController;
import ProyectoAiss.BitBucket.model.BitBucket.BProject;
import ProyectoAiss.BitBucket.service.BitBucketMinerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BitBucketController.class)
public class BitBucketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BitBucketMinerService minerService;

    @MockBean
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper;
    private BProject proyectoEjemplo;

    @BeforeEach
    public void inicializar() {
        objectMapper = new ObjectMapper();

        proyectoEjemplo = new BProject();
        proyectoEjemplo.setId("proj123");
        proyectoEjemplo.setName("Proyecto Ejemplo");
        // Añade más campos según tu modelo
    }

    @Test
    public void getProject_withDefaultParams_returnsProject() throws Exception {
        when(minerService.fetchProject(eq("workspace1"), eq("repo1"), eq(5), eq(5), eq(2)))
                .thenReturn(proyectoEjemplo);

        mockMvc.perform(get("/bitbucket/workspace1/repo1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("proj123"))
                .andExpect(jsonPath("$.name").value("Proyecto Ejemplo"));
    }

    @Test
    public void postProject_returnsCreatedProject() throws Exception {
        when(minerService.fetchProject(eq("workspace1"), eq("repo1"), eq(5), eq(5), eq(2)))
                .thenReturn(proyectoEjemplo);

        when(restTemplate.postForObject(anyString(), any(BProject.class), eq(BProject.class)))
                .thenReturn(proyectoEjemplo);

        mockMvc.perform(post("/bitbucket/workspace1/repo1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("proj123"))
                .andExpect(jsonPath("$.name").value("Proyecto Ejemplo"));
    }

    @Test
    public void getProject_withCustomParams_returnsProject() throws Exception {
        when(minerService.fetchProject(eq("workspace1"), eq("repo1"), eq(10), eq(15), eq(3)))
                .thenReturn(proyectoEjemplo);

        mockMvc.perform(get("/bitbucket/workspace1/repo1")
                        .param("nCommits", "10")
                        .param("nIssues", "15")
                        .param("maxPages", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("proj123"))
                .andExpect(jsonPath("$.name").value("Proyecto Ejemplo"));
    }
}
