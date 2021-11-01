package com.junit.app.services;

import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;
import com.junit.app.repositories.BancoDAO;
import com.junit.app.repositories.CuentaDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService{


    CuentaDAO cuentaDAO;
    BancoDAO bancoDAO;

    public CuentaServiceImpl(CuentaDAO cuentaDAO, BancoDAO bancoDAO) {
        this.cuentaDAO = cuentaDAO;
        this.bancoDAO = bancoDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaDAO.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencia(Long idBank) {
        Banco banco= bancoDAO.findById(idBank).orElseThrow();
        return banco.getTotalTransfer();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long idCuenta) {
        Cuenta cuenta= cuentaDAO.findById(idCuenta).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional()
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long idBank) {
        Banco banco= bancoDAO.findById(idBank).orElseThrow();
        int totalTransfer= banco.getTotalTransfer();
        banco.setTotalTransfer(++totalTransfer);
        bancoDAO.save(banco);

        Cuenta cuentaOrigen= cuentaDAO.findById(numeroCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaDAO.save(cuentaOrigen);

        Cuenta cuentaDestino= cuentaDAO.findById(numeroCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaDAO.save(cuentaDestino);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaDAO.findAll();
    }

    @Override
    @Transactional()
    public Cuenta save(Cuenta cuenta) {
        return cuentaDAO.save(cuenta);
    }
}
