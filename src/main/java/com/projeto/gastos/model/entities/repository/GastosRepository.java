package com.projeto.gastos.model.entities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.gastos.model.entities.Gastos;
@Repository
public interface GastosRepository extends JpaRepository<Gastos,Long> {
    Gastos findByDescricao(String descricao);
    List<Gastos> findByCategoriaContainingIgnoreCase(String categoria);

}
