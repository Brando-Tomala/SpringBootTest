package com.junit.app.models;

import com.junit.app.exception.DineroInsuficienteException;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {
    private Long id;
    private String nombre;
    private BigDecimal saldo;

    public void debito(BigDecimal monto){
        BigDecimal saldoCurrent= this.saldo.subtract(monto);
        if(saldoCurrent.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero insuficiente en la cuenta");
        }
        this.saldo= saldoCurrent;
    }

    public void credito(BigDecimal monto){
        this.saldo= this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id) && Objects.equals(nombre, cuenta.nombre) && Objects.equals(saldo, cuenta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, saldo);
    }
}
