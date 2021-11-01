package com.junit.app.controller;

import com.junit.app.models.Cuenta;
import com.junit.app.models.TransaccionDTO;
import com.junit.app.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brando Tomala
 * @version 1.0
 * @since 31/10/2021
 */
@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    CuentaService service;

    @GetMapping(path = "/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO request){
        service.transferir(request.getCuentaOrigen(), request.getCuentaDestino(), request.getMonto(),
            request.getBancoId());
        Map<String, Object> response= new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transferencia realizada con exito");
        response.put("transaccion", request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Cuenta> listar(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta){
        return service.save(cuenta);
    }

}
