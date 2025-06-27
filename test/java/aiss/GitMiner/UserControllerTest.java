package aiss.GitMiner;

import aiss.GitMiner.controller.UserController;
import aiss.GitMiner.model.User;
import aiss.GitMiner.repository.UserRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repositorioUsuarios;

    private ObjectMapper mapeadorObjetos;
    private User usuarioEjemplo;

    @BeforeEach
    public void inicializar() {
        mapeadorObjetos = new ObjectMapper();

        usuarioEjemplo = new User();
        usuarioEjemplo.setId("user123");
        usuarioEjemplo.setUsername("alice123");
        usuarioEjemplo.setName("Alice");
        usuarioEjemplo.setAvatarUrl("http://example.com/avatar.jpg");
        usuarioEjemplo.setWebUrl("http://example.com/alice");
    }

    @Test
    public void getAllUsers_returnsList() throws Exception {
        when(repositorioUsuarios.findAll()).thenReturn(Arrays.asList(usuarioEjemplo));

        mockMvc.perform(get("/gitminer/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("user123"))
                .andExpect(jsonPath("$[0].username").value("alice123"))
                .andExpect(jsonPath("$[0].name").value("Alice"));
    }

    @Test
    public void getUserById_returnsUser() throws Exception {
        when(repositorioUsuarios.findById("user123")).thenReturn(Optional.of(usuarioEjemplo));

        mockMvc.perform(get("/gitminer/users/user123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("user123"))
                .andExpect(jsonPath("$.username").value("alice123"))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void createUser_returnsCreatedUser() throws Exception {
        when(repositorioUsuarios.save(any(User.class))).thenReturn(usuarioEjemplo);

        String json = mapeadorObjetos.writeValueAsString(usuarioEjemplo);

        mockMvc.perform(post("/gitminer/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("user123"))
                .andExpect(jsonPath("$.username").value("alice123"))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void updateUser_returnsNoContent() throws Exception {
        when(repositorioUsuarios.findById("user123")).thenReturn(Optional.of(usuarioEjemplo));
        when(repositorioUsuarios.save(any(User.class))).thenReturn(usuarioEjemplo);

        User usuarioActualizado = new User();
        usuarioActualizado.setId("user123");
        usuarioActualizado.setUsername("alice_actualizado");
        usuarioActualizado.setName("Alice Actualizada");
        usuarioActualizado.setAvatarUrl("http://example.com/avatar_actualizado.jpg");
        usuarioActualizado.setWebUrl("http://example.com/alice_actualizado");

        String json = mapeadorObjetos.writeValueAsString(usuarioActualizado);

        mockMvc.perform(put("/gitminer/users/user123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());

        verify(repositorioUsuarios, times(1)).save(any(User.class));
    }

    @Test
    public void deleteUser_returnsNoContent() throws Exception {
        when(repositorioUsuarios.existsById("user123")).thenReturn(true);
        doNothing().when(repositorioUsuarios).deleteById("user123");

        mockMvc.perform(delete("/gitminer/users/user123"))
                .andExpect(status().isNoContent());

        verify(repositorioUsuarios, times(1)).deleteById("user123");
    }
}
