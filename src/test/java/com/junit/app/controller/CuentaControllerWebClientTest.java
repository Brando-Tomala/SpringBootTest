package com.junit.app.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.app.models.TransaccionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Brando Tomala
 * @version 1.0
 * @since 01/11/2021
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
class CuentaControllerWebClientTest {

    @Autowired
    private WebTestClient client;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testTransferir() throws JsonProcessingException {
        TransaccionDTO dto= new TransaccionDTO();
        dto.setCuentaOrigen(1L);
        dto.setCuentaDestino(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transferencia realizada con exito");
        response.put("transaccion", dto);

        client.post().uri("http://localhost:8080/api/cuentas/transferir")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.message").isNotEmpty()
            .jsonPath("$.status").value(is("OK"))
            .jsonPath("$.date").value(value-> assertEquals(LocalDate.now().toString(), value))
            .jsonPath("$.transaccion.cuentaOrigen").isEqualTo(dto.getCuentaOrigen())
            .json(mapper.writeValueAsString(response));

    }

    @Test
    void testDetalle() {
    }



    @Test
    void testListar() {
    }

    @Test
    void testGuardar() {
    }
}