package com.junit.app.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.app.Datos;
import com.junit.app.models.Cuenta;
import com.junit.app.models.TransaccionDTO;
import com.junit.app.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Brando Tomala
 * @version 1.0
 * @since 31/10/2021
 */
@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CuentaService cuentaService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testDetalle() throws Exception {
        when(cuentaService.findById(1L)).thenReturn(Datos.cuenta001().orElseThrow());

        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.nombre").value("Brando"))
            .andExpect(jsonPath("$.saldo").value("1000"));
        verify(cuentaService).findById(1L);
    }

    @Test
    void testTransferir() throws Exception {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaOrigen(1L);
        dto.setCuentaDestino(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transferencia realizada con exito");
        response.put("transaccion", dto);

        mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("Transferencia realizada con exito"))
            .andExpect(jsonPath("$.transaccion.cuentaOrigen").value(1L))
            .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    void testListar() throws Exception {
        List<Cuenta> cuentas = List.of(Datos.cuenta001().orElseThrow(),
            Datos.cuenta002().orElseThrow());
        when(cuentaService.findAll()).thenReturn(cuentas);

        mvc.perform(get("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].nombre").value("Brando"))
            .andExpect(jsonPath("$[1].nombre").value("Jahir"))
            .andExpect(jsonPath("$[0].saldo").value("1000"))
            .andExpect(jsonPath("$[1].saldo").value("2000"))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(content().json(mapper.writeValueAsString(cuentas)));
        verify(cuentaService).findAll();
    }

    @Test
    void testGuardar() throws Exception {
        Cuenta cuenta = new Cuenta(3L, "Pepe", new BigDecimal("4500"));
        when(cuentaService.save(any())).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        mvc.perform(post("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cuenta))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(3)))
            .andExpect(jsonPath("$.nombre").value("Pepe"))
            .andExpect(jsonPath("$.saldo").value("4500"))
            .andExpect(content().json(mapper.writeValueAsString(cuenta)));
        verify(cuentaService).save(any());
    }
}