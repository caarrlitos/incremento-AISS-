package aiss.GitMiner;

import aiss.GitMiner.controller.CommentController;
import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.User;
import aiss.GitMiner.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment comentarioEjemplo;

    @BeforeEach
    void inicializar() {
        User autor = new User();
        autor.setId("usuario123");
        autor.setUsername("usuario_ejemplo");

        comentarioEjemplo = new Comment();
        comentarioEjemplo.setId("1");
        comentarioEjemplo.setBody("Este es un comentario de prueba");
        comentarioEjemplo.setCreatedAt("2024-01-01T00:00:00Z");
        comentarioEjemplo.setUpdatedAt("2024-01-02T00:00:00Z");
        comentarioEjemplo.setRetrieved_at("2024-01-03T00:00:00Z");
        comentarioEjemplo.setAuthor(autor);
        comentarioEjemplo.setIsBot(false);
    }

    @Test
    void getAllComments_returnsList() throws Exception {
        Mockito.when(commentRepository.findAll()).thenReturn(List.of(comentarioEjemplo));

        mockMvc.perform(get("/gitminer/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].body", is("Este es un comentario de prueba")))
                .andExpect(jsonPath("$[0].author.id", is("usuario123")))
                .andExpect(jsonPath("$[0].is_bot", is(false)));
    }

    @Test
    void getCommentById_found_returnsComment() throws Exception {
        Mockito.when(commentRepository.findById("1")).thenReturn(Optional.of(comentarioEjemplo));

        mockMvc.perform(get("/gitminer/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.body", is("Este es un comentario de prueba")))
                .andExpect(jsonPath("$.author.id", is("usuario123")))
                .andExpect(jsonPath("$.is_bot", is(false)));
    }

    @Test
    void getCommentById_notFound_returnsError() throws Exception {
        Mockito.when(commentRepository.findById("2")).thenReturn(Optional.empty());

        mockMvc.perform(get("/gitminer/comments/2"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createComment_returnsCreated() throws Exception {
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comentarioEjemplo);

        mockMvc.perform(post("/gitminer/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comentarioEjemplo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.is_bot", is(false)));

        Mockito.verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_exists_returnsNoContent() throws Exception {
        Mockito.when(commentRepository.findById("1")).thenReturn(Optional.of(comentarioEjemplo));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comentarioEjemplo);

        mockMvc.perform(put("/gitminer/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comentarioEjemplo)))
                .andExpect(status().isNoContent());

        Mockito.verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void updateComment_notFound_returnsError() throws Exception {
        Mockito.when(commentRepository.findById("99")).thenReturn(Optional.empty());

        mockMvc.perform(put("/gitminer/comments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comentarioEjemplo)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteComment_exists_returnsNoContent() throws Exception {
        Mockito.when(commentRepository.existsById("1")).thenReturn(true);

        mockMvc.perform(delete("/gitminer/comments/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(commentRepository).deleteById("1");
    }

    @Test
    void deleteComment_notFound_returnsNoContent() throws Exception {
        Mockito.when(commentRepository.existsById("99")).thenReturn(false);

        mockMvc.perform(delete("/gitminer/comments/99"))
                .andExpect(status().isNoContent());
    }
}
