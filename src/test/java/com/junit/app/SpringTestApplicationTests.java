package com.junit.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import com.junit.app.exception.DineroInsuficienteException;
import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.BancoDAO;
import com.junit.app.repositories.CuentaDAO;
import com.junit.app.services.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SpringTestApplicationTests {

    //	La forma de spring
    @MockBean
    CuentaDAO cuentaDAO;
    @MockBean
    BancoDAO bancoDAO;

    @Autowired
    CuentaService service;

//	La forma de mockito
//	@Mock
//	CuentaDAO cuentaDAO;
//	@Mock
//	BancoDAO bancoDAO;
//
//	@InjectMocks
//	CuentaServiceImpl service;

    //	Si se usa beforeEach se debe declara CuentaService
//	CuentaService service;
    @BeforeEach
    void setUp() {
//		cuentaDAO= Mockito.mock(CuentaDAO.class);
//		bancoDAO= Mockito.mock(BancoDAO.class);
//		service= new CuentaServiceImpl(cuentaDAO, bancoDAO);
    }


    @Test
    void contextLoads() {
        when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
        when(cuentaDAO.findById(2L)).thenReturn(Datos.cuenta002());
        when(bancoDAO.findById(1L)).thenReturn(Datos.banco());
        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);
        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());

        int totalTransfer = service.revisarTotalTransferencia(1L);
        assertEquals(1, totalTransfer);

        verify(cuentaDAO, times(3)).findById(1L);
        verify(cuentaDAO, times(3)).findById(2L);

        verify(cuentaDAO, times(2)).save(any(Cuenta.class));

        verify(bancoDAO, times(2)).findById(1L);
        verify(bancoDAO).save(any(Banco.class));

        verify(cuentaDAO, times(6)).findById(Mockito.anyLong());
        verify(cuentaDAO, never()).findAll();
    }

    @Test
    void contextLoads2() {
        when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
        when(cuentaDAO.findById(2L)).thenReturn(Datos.cuenta002());
        when(bancoDAO.findById(1L)).thenReturn(Datos.banco());
        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);
        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class,
            () -> service.transferir(1L, 2L, new BigDecimal("1200"), 1L));

        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        int totalTransfer = service.revisarTotalTransferencia(1L);
        assertEquals(1, totalTransfer);

        verify(cuentaDAO, times(3)).findById(1L);
        verify(cuentaDAO, times(2)).findById(2L);
        verify(cuentaDAO, never()).save(any(Cuenta.class));

        verify(bancoDAO, times(2)).findById(1L);
        verify(bancoDAO).save(any(Banco.class));

        verify(cuentaDAO, times(5)).findById(Mockito.anyLong());
        verify(cuentaDAO, never()).findAll();
    }

    @Test
    void contextLoad3() {
        when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
        Cuenta cuenta1 = service.findById(1L);
        Cuenta cuenta2 = service.findById(1L);

        assertSame(cuenta1, cuenta2);
        verify(cuentaDAO, times(2)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = List.of(Datos.cuenta001().orElseThrow(),
            Datos.cuenta002().orElseThrow());
        when(cuentaDAO.findAll()).thenReturn(cuentas);

        List<Cuenta> allCuentas = service.findAll();
        assertFalse(allCuentas.isEmpty());
        assertEquals(2, allCuentas.size());
        assertTrue(allCuentas.contains(Datos.cuenta002().orElseThrow()));

        verify(cuentaDAO).findAll();
    }

    @Test
    void testGuardar() {
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("4500"));
        when(cuentaDAO.save(any())).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        Cuenta c1 = service.save(cuenta);
        assertEquals("Pepe", c1.getNombre());
        assertEquals("4500", c1.getSaldo().toPlainString());
        verify(cuentaDAO).save(any());
    }

}
