package com.junit.app.repositories;

import com.junit.app.models.Banco;

import java.util.List;

public interface BancoDAO {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco banco);
}
