package br.com.zup.orange.autor;

import javax.persistence.EntityManager;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
class AutorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    EntityManager entityManager;

    @Test
    void deveCadastrarUmNovoAutor() throws Exception {
        CriacaoDeNovoAutorRequest request = new CriacaoDeNovoAutorRequest("Tiago", "tiago.lima@zup.com.br", "Eu sou um autor");
        String json = json(request);

        mockMvc.perform(post("/autor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());

        List<Autor> autores = entityManager.createQuery("from Autor where email = :email", Autor.class)
                .setParameter("email", request.email)
                .getResultList();

        assertTrue(autores.size() == 1);

        Autor autor = autores.get(0);

        //        assertThat(autor.nome, equalTo(request.nome));
        assertAll(() -> assertEquals(request.nome, autor.nome),
                  () -> assertEquals(request.email, autor.email),
                  () -> assertEquals(request.descricao, autor.descricao));
    }

    @Test
    void deveRecusarUmAutorComNomeVazio() throws Exception {
        CriacaoDeNovoAutorRequest request = new CriacaoDeNovoAutorRequest(null, "tiago.lima@zup.com.br", "Eu sou um autor");
        String json = json(request);

        mockMvc.perform(post("/autor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    private String json(CriacaoDeNovoAutorRequest request) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(request);
    }
}
