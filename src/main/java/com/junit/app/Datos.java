package com.junit.app;

import com.junit.app.models.Banco;
import com.junit.app.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
//    public static final Cuenta CUENTA_001= new Cuenta(1L, "Brando", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002= new Cuenta(2L, "Jahir", new BigDecimal("2000"));
//    public static final Banco BANCO= new Banco(1L, "Banco Finanza", 0);

    public static Cuenta cuenta001(){
        return new Cuenta(1L, "Brando", new BigDecimal("1000"));
    }
    public static Cuenta cuenta002(){
        return new Cuenta(2L, "Jahir", new BigDecimal("2000"));
    }
    public static Banco banco(){
        return new Banco(1L, "Banco Finanza", 0);
    }
}
