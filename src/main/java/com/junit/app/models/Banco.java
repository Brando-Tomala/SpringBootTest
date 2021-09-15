package com.junit.app.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Banco {
    private Long id;
    private String nombre;
    private Integer totalTransfer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banco banco = (Banco) o;
        return Objects.equals(id, banco.id) && Objects.equals(nombre, banco.nombre) && Objects.equals(totalTransfer, banco.totalTransfer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, totalTransfer);
    }
}
