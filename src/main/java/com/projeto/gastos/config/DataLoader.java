package com.projeto.gastos.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.projeto.gastos.model.entities.Gastos;
import com.projeto.gastos.model.entities.repository.GastosRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private GastosRepository gastoRepository;

    @Override
    public void run(String... args) throws Exception {
        gastoRepository.save(new Gastos(null, "Mercado", new BigDecimal("150.00"), LocalDate.now(), "Alimentação"));
        gastoRepository.save(new Gastos(null, "Posto", new BigDecimal("200.00"), LocalDate.now(), "Transporte"));
        gastoRepository.save(new Gastos(null, "Onibus", new BigDecimal("200.00"), LocalDate.now(), "Transporte"));
        gastoRepository.save(new Gastos(null, "Posto Shell", new BigDecimal("200.00"), LocalDate.now(), "Transporte"));
        gastoRepository.save(new Gastos(null, "Uber", new BigDecimal("200.00"), LocalDate.now(), "Transporte"));
    }
}
