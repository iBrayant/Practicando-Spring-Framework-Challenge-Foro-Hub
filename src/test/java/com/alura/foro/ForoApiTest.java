package com.alura.foro;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ForoApiTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    @BeforeEach
    void iniciarSesion() throws Exception {
        token = JsonPath.read(
                login("admin@mail.com", "123456").andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString(),
                "$.token");
    }

    private ResultActions login(String email, String password) throws Exception {
        return mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"%s\",\"password\":\"%s\"}".formatted(email, password)));
    }

    private ResultActions crearTopico(String titulo, String mensaje, long autorId, long cursoId) throws Exception {
        return mvc.perform(post("/topicos").header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"%s\",\"mensaje\":\"%s\",\"autorId\":%d,\"cursoId\":%d}"
                        .formatted(titulo, mensaje, autorId, cursoId)));
    }

    // --- Autenticación ---

    @Test
    void loginConPasswordIncorrectoDevuelve401() throws Exception {
        login("admin@mail.com", "incorrecta").andExpect(status().isUnauthorized());
    }

    @Test
    void loginConUsuarioInexistenteDevuelve401() throws Exception {
        login("nadie@mail.com", "123456").andExpect(status().isUnauthorized());
    }

    @Test
    void endpointsDeDepuracionDeContrasenasYaNoExisten() throws Exception {
        mvc.perform(get("/login/test-password").param("password", "x")).andExpect(status().isUnauthorized());
        mvc.perform(get("/login/test-password").param("password", "x").header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void sinTokenDevuelve401() throws Exception {
        mvc.perform(get("/topicos")).andExpect(status().isUnauthorized());
    }

    @Test
    void tokenInvalidoDevuelve401EnLugarDe500() throws Exception {
        mvc.perform(get("/topicos").header("Authorization", "Bearer esto-no-es-un-jwt"))
                .andExpect(status().isUnauthorized());
    }

    // --- Tópicos ---

    @Test
    void creaUnTopicoYLoPuedeConsultar() throws Exception {
        var respuesta = crearTopico("Titulo nuevo", "Mensaje nuevo", 1, 1)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(respuesta, "$.id");

        mvc.perform(get("/topicos/" + id).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Titulo nuevo"));
    }

    @Test
    void topicoDuplicadoDevuelve409() throws Exception {
        crearTopico("Repetido", "Mismo mensaje", 1, 1).andExpect(status().isCreated());
        crearTopico("Repetido", "Mismo mensaje", 1, 1).andExpect(status().isConflict());
    }

    @Test
    void tituloVacioDevuelve400() throws Exception {
        crearTopico("", "Mensaje", 1, 1).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].campo").value("titulo"));
    }

    @Test
    void tituloDemasiadoLargoDevuelve400() throws Exception {
        crearTopico("x".repeat(101), "Mensaje", 1, 1).andExpect(status().isBadRequest());
    }

    @Test
    void autorInexistenteDevuelve404() throws Exception {
        crearTopico("Titulo", "Mensaje", 999, 1).andExpect(status().isNotFound());
    }

    @Test
    void listaConPaginacionYFiltraPorCurso() throws Exception {
        mvc.perform(get("/topicos").param("size", "2").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements").value(3));

        mvc.perform(get("/topicos").param("nombreCurso", "MySQL").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    void actualizarConTituloEnBlancoDevuelve400() throws Exception {
        mvc.perform(put("/topicos").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"titulo\":\"   \"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarEsLogicoYElTopicoDejaDeVerse() throws Exception {
        mvc.perform(delete("/topicos/1").header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        mvc.perform(get("/topicos/1").header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
        mvc.perform(get("/topicos").header("Authorization", "Bearer " + token))
                .andExpect(jsonPath("$.totalElements").value(2));
        // Un tópico eliminado tampoco se puede actualizar
        mvc.perform(put("/topicos").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"titulo\":\"Nuevo\"}"))
                .andExpect(status().isNotFound());
    }
}
