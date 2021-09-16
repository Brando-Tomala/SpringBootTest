package com.junit.app.services;

import com.junit.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {
    Cuenta findById(Long id);
    int revisarTotalTransferencia(Long idBank);
    BigDecimal revisarSaldo(Long idCuenta);
    void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long idBank);

}
