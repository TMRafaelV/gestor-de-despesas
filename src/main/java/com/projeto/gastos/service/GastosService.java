package com.projeto.gastos.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.gastos.model.entities.Gastos;
import com.projeto.gastos.model.entities.repository.GastosRepository;

@Service
public class GastosService {
    @Autowired
    private GastosRepository gastosRepository;

    public void salvar(Gastos gastos){

        gastosRepository.save(gastos);
    }

    public List<Gastos> consulta(){
        return gastosRepository.findAll();
        
    }

    private Gastos consultaPorDescricao(String descricao){
        return gastosRepository.findByDescricao(descricao);
    }

    public void delete(Gastos gastos){
        gastosRepository.delete(gastos);
    }

    public Gastos updateDescricaoPorId(Long id, String novaDescricao) {
        var gasto = gastosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não encontrado com o ID: " + id));
        gasto.setDescricao(novaDescricao);
        return gastosRepository.save(gasto);
    }
    

    public List<Gastos> consultaCategoria(String categoria){
        List<Gastos> lista = gastosRepository.findByCategoriaContainingIgnoreCase(categoria);
        return lista;
    }
     public BigDecimal somaPorCategoria(String gategoria){
        List<Gastos> lista = consultaCategoria(gategoria);
        return lista.stream()
                .map(Gastos::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
     }

}
