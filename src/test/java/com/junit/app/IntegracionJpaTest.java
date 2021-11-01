package com.junit.app;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.CuentaDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Brando Tomala
 * @version 1.0
 * @since 31/10/2021
 */
@DataJpaTest
class IntegracionJpaTest {

    @Autowired
    CuentaDAO cuentaDAO;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaDAO.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Brando", cuenta.orElseThrow().getNombre());
    }

    @Test
    void testFindByNombre() {
        Optional<Cuenta> cuenta = cuentaDAO.findByNombre("Brando");
        assertTrue(cuenta.isPresent());
        assertEquals("Brando", cuenta.orElseThrow().getNombre());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void testFindByNombreThrowException() {
        Optional<Cuenta> cuenta = cuentaDAO.findByNombre("Robert");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaDAO.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void testSave() {
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        cuentaDAO.save(cuentaPepe);
        Cuenta c1 = cuentaDAO.findByNombre("Pepe").orElseThrow();
        assertEquals("Pepe", c1.getNombre());
        assertEquals("3000", c1.getSaldo().toPlainString());
    }

    @Test
    void testUpdate() {
        Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        cuentaDAO.save(cuentaPepe);
        Cuenta c1 = cuentaDAO.findByNombre("Pepe").orElseThrow();
        assertEquals("Pepe", c1.getNombre());
        assertEquals("3000", c1.getSaldo().toPlainString());

        c1.setSaldo(new BigDecimal("5000"));
        Cuenta c1Update = cuentaDAO.save(c1);

        assertEquals("5000", c1Update.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        Cuenta c1 = cuentaDAO.findById(2L).orElseThrow();
        assertEquals("Jahir", c1.getNombre());

        cuentaDAO.delete(c1);

        assertThrows(NoSuchElementException.class, () -> {
            cuentaDAO.findByNombre("Jahir").orElseThrow();
        });
    }

}
