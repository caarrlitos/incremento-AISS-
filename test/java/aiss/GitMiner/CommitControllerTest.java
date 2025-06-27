package aiss.GitMiner;

import aiss.GitMiner.controller.CommitController;
import aiss.GitMiner.model.Commit;
import aiss.GitMiner.repository.CommitRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommitController.class)
public class CommitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommitRepository commitRepository;

    private Commit exampleCommit;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void inicializar() {
        exampleCommit = new Commit(
                "commit123",
                "Bug fix",
                "Fixed a critical bug",
                "Juan Pérez",
                "juan@ejemplo.com",
                "2024-06-25T12:00:00Z",
                "http://ejemplo.com/commit123",
                "2024-06-26T12:00:00Z",
                false
        );
    }

    @Test
    public void listCommits_returnsList() throws Exception {
        when(commitRepository.findAll()).thenReturn(Arrays.asList(exampleCommit));

        mockMvc.perform(get("/gitminer/commits")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("commit123")))
                .andExpect(jsonPath("$[0].title", is("Bug fix")))
                .andExpect(jsonPath("$[0].author_name", is("Juan Pérez")))
                .andExpect(jsonPath("$[0].author_email", is("juan@ejemplo.com")))
                .andExpect(jsonPath("$[0].authored_date", is("2024-06-25T12:00:00Z")))
                .andExpect(jsonPath("$[0].is_merge_commit", is(false)));
    }

    @Test
    public void getCommitById_returnsCommit() throws Exception {
        when(commitRepository.findById("commit123")).thenReturn(Optional.of(exampleCommit));

        mockMvc.perform(get("/gitminer/commits/commit123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("commit123")))
                .andExpect(jsonPath("$.author_name", is("Juan Pérez")))
                .andExpect(jsonPath("$.author_email", is("juan@ejemplo.com")))
                .andExpect(jsonPath("$.authored_date", is("2024-06-25T12:00:00Z")))
                .andExpect(jsonPath("$.is_merge_commit", is(false)));
    }

    @Test
    public void createCommit_returnsCreated() throws Exception {
        when(commitRepository.save(any(Commit.class))).thenReturn(exampleCommit);

        String json = objectMapper.writeValueAsString(exampleCommit);

        mockMvc.perform(post("/gitminer/commits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("commit123")))
                .andExpect(jsonPath("$.title", is("Bug fix")))
                .andExpect(jsonPath("$.author_name", is("Juan Pérez")));

        verify(commitRepository, times(1)).save(any(Commit.class));
    }

    @Test
    public void updateCommit_returnsNoContent() throws Exception {
        when(commitRepository.findById("commit123")).thenReturn(Optional.of(exampleCommit));
        when(commitRepository.save(any(Commit.class))).thenReturn(exampleCommit);

        Commit updatedCommit = new Commit(
                "commit123",
                "Updated title",
                "Updated message",
                "Juana Pérez",
                "juana@ejemplo.com",
                "2024-06-27T12:00:00Z",
                "http://ejemplo.com/commit123",
                "2024-06-28T12:00:00Z",
                true
        );

        String json = objectMapper.writeValueAsString(updatedCommit);

        mockMvc.perform(put("/gitminer/commits/commit123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        verify(commitRepository, times(1)).findById("commit123");
        verify(commitRepository, times(1)).save(any(Commit.class));
    }

    @Test
    public void deleteCommit_returnsNoContent() throws Exception {
        when(commitRepository.existsById("commit123")).thenReturn(true);
        doNothing().when(commitRepository).deleteById("commit123");

        mockMvc.perform(delete("/gitminer/commits/commit123"))
                .andExpect(status().isNoContent());

        verify(commitRepository, times(1)).existsById("commit123");
        verify(commitRepository, times(1)).deleteById("commit123");
    }

    @Test
    public void deleteCommit_notFound_doesNotDelete() throws Exception {
        when(commitRepository.existsById("commit123")).thenReturn(false);

        mockMvc.perform(delete("/gitminer/commits/commit123"))
                .andExpect(status().isNoContent());

        verify(commitRepository, times(1)).existsById("commit123");
        verify(commitRepository, times(0)).deleteById(anyString());
    }
}
