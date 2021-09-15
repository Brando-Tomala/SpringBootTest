package com.junit.app.services;

import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.BancoDAO;
import com.junit.app.repositories.CuentaDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CuentaServiceImpl implements CuentaService{

    @Autowired
    CuentaDAO cuentaDAO;

    @Autowired
    BancoDAO bancoDAO;

    @Override
    public Cuenta findById(Long id) {
        return cuentaDAO.findById(id);
    }

    @Override
    public int revisarTotalTransferencia(Long idBank) {
        Banco banco= bancoDAO.findById(idBank);
        return banco.getTotalTransfer();
    }

    @Override
    public BigDecimal revisarSaldo(Long idCuenta) {
        Cuenta cuenta= cuentaDAO.findById(idCuenta);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto) {
        Banco banco= bancoDAO.findById(1L);
        int totalTransfer= banco.getTotalTransfer();
        banco.setTotalTransfer(++totalTransfer);
        bancoDAO.update(banco);

        Cuenta cuentaOrigen= cuentaDAO.findById(numeroCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaDAO.update(cuentaOrigen);

        Cuenta cuentaDestino= cuentaDAO.findById(numeroCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaDAO.update(cuentaDestino);
    }
}
