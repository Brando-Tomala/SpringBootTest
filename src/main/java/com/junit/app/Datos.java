package com.junit.app;

import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {
//    public static final Cuenta CUENTA_001= new Cuenta(1L, "Brando", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002= new Cuenta(2L, "Jahir", new BigDecimal("2000"));
//    public static final Banco BANCO= new Banco(1L, "Banco Finanza", 0);

    public static Optional<Cuenta> cuenta001(){
        return Optional.of(new Cuenta(1L, "Brando", new BigDecimal("1000")));
    }
    public static Optional<Cuenta>  cuenta002(){
        return Optional.of(new Cuenta(2L, "Jahir", new BigDecimal("2000")));
    }
    public static Optional<Banco> banco(){
        return Optional.of(new Banco(1L, "Banco Finanza", 0));
    }
}
