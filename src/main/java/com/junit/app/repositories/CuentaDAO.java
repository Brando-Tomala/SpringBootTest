package com.junit.app.repositories;

import com.junit.app.models.Cuenta;

import java.util.List;

public interface CuentaDAO {
    List<Cuenta> findAll();
    Cuenta findById(Long id);
    void update(Cuenta cuenta);
}
