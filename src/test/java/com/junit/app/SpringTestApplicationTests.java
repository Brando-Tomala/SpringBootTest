package com.junit.app;

import com.junit.app.exception.DineroInsuficienteException;
import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.BancoDAO;
import com.junit.app.repositories.CuentaDAO;
import com.junit.app.services.CuentaService;
import com.junit.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

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
	void setUp(){
//		cuentaDAO= Mockito.mock(CuentaDAO.class);
//		bancoDAO= Mockito.mock(BancoDAO.class);
//		service= new CuentaServiceImpl(cuentaDAO, bancoDAO);
	}


	@Test
	void contextLoads() {
		Mockito.when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
		Mockito.when(cuentaDAO.findById(2L)).thenReturn(Datos.cuenta002());
		Mockito.when(bancoDAO.findById(1L)).thenReturn(Datos.banco());
		BigDecimal saldoOrigen= service.revisarSaldo(1L);
		BigDecimal saldoDestino= service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		service.transferir(1L, 2L, new BigDecimal("100"), 1L);
		saldoOrigen= service.revisarSaldo(1L);
		saldoDestino= service.revisarSaldo(2L);

		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());

		int totalTransfer= service.revisarTotalTransferencia(1l);
		assertEquals(1, totalTransfer);

		Mockito.verify(cuentaDAO, Mockito.times(3)).findById(1L);
		Mockito.verify(cuentaDAO, Mockito.times(3)).findById(2L);

		Mockito.verify(cuentaDAO, Mockito.times(2)).update(Mockito.any(Cuenta.class));

		Mockito.verify(bancoDAO, Mockito.times(2)).findById(1L);
		Mockito.verify(bancoDAO).update(Mockito.any(Banco.class));

		Mockito.verify(cuentaDAO, Mockito.times(6)).findById(Mockito.anyLong());
		Mockito.verify(cuentaDAO, Mockito.never()).findAll();
	}

	@Test
	void contextLoads2() {
		Mockito.when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
		Mockito.when(cuentaDAO.findById(2L)).thenReturn(Datos.cuenta002());
		Mockito.when(bancoDAO.findById(1L)).thenReturn(Datos.banco());
		BigDecimal saldoOrigen= service.revisarSaldo(1L);
		BigDecimal saldoDestino= service.revisarSaldo(2L);
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class, ()->{
			service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});


		saldoOrigen= service.revisarSaldo(1L);
		saldoDestino= service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		int totalTransfer= service.revisarTotalTransferencia(1L);
		assertEquals(1, totalTransfer);

		Mockito.verify(cuentaDAO, Mockito.times(3)).findById(1L);
		Mockito.verify(cuentaDAO, Mockito.times(2)).findById(2L);
		Mockito.verify(cuentaDAO, Mockito.never()).update(Mockito.any(Cuenta.class));

		Mockito.verify(bancoDAO, Mockito.times(2)).findById(1L);
		Mockito.verify(bancoDAO).update(Mockito.any(Banco.class));

		Mockito.verify(cuentaDAO, Mockito.times(5)).findById(Mockito.anyLong());
		Mockito.verify(cuentaDAO, Mockito.never()).findAll();
	}

	@Test
	void contextLoad3(){
		Mockito.when(cuentaDAO.findById(1L)).thenReturn(Datos.cuenta001());
		Cuenta cuenta1= service.findById(1L);
		Cuenta cuenta2= service.findById(1L);

		assertSame(cuenta1, cuenta2);
		Mockito.verify(cuentaDAO, Mockito.times(2)).findById(1L);
	}

}
