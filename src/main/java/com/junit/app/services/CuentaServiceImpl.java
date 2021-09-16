package com.junit.app.services;

import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.BancoDAO;
import com.junit.app.repositories.CuentaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CuentaServiceImpl implements CuentaService{


    CuentaDAO cuentaDAO;
    BancoDAO bancoDAO;

    public CuentaServiceImpl(CuentaDAO cuentaDAO, BancoDAO bancoDAO) {
        this.cuentaDAO = cuentaDAO;
        this.bancoDAO = bancoDAO;
    }

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
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long idBank) {
        Banco banco= bancoDAO.findById(idBank);
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
