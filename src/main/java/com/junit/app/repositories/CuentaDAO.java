package com.junit.app.repositories;

import com.junit.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaDAO extends JpaRepository<Cuenta, Long> {
//    List<Cuenta> findAll();
//    Cuenta findById(Long id);
//    void update(Cuenta cuenta);
    Optional<Cuenta> findByNombre(String nombre);
}
