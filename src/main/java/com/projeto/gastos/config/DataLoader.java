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
        gastoRepository.save(new Gastos(null, "Mercado", new BigDecimal("150.00"), LocalDate.of(2025,02,05), "Alimentação"));
        
        // Alimentação
        gastoRepository.save(new Gastos(null, "Supermercado Extra", new BigDecimal("220.45"), LocalDate.of(2025, 1, 10), "Alimentação"));
        gastoRepository.save(new Gastos(null, "Café da Manhã Padoca", new BigDecimal("18.00"), LocalDate.of(2025, 2, 12), "Alimentação"));
        gastoRepository.save(new Gastos(null, "Almoço Marmitex", new BigDecimal("22.50"), LocalDate.of(2025, 4, 3), "Alimentação"));
        gastoRepository.save(new Gastos(null, "Delivery iFood", new BigDecimal("48.90"), LocalDate.of(2025, 5, 19), "Alimentação"));

        // Transporte
        gastoRepository.save(new Gastos(null, "Estacionamento", new BigDecimal("15.00"), LocalDate.of(2025, 1, 25), "Transporte"));
        gastoRepository.save(new Gastos(null, "Manutenção Carro", new BigDecimal("350.00"), LocalDate.of(2025, 2, 28), "Transporte"));
        gastoRepository.save(new Gastos(null, "Bicicletário", new BigDecimal("5.00"), LocalDate.of(2025, 4, 10), "Transporte"));
        gastoRepository.save(new Gastos(null, "Combustível", new BigDecimal("189.70"), LocalDate.of(2025, 5, 2), "Transporte"));
        gastoRepository.save(new Gastos(null, "Posto", new BigDecimal("200.00"), LocalDate.of(2025,01,02), "Transporte"));
        gastoRepository.save(new Gastos(null, "Onibus", new BigDecimal("8.00"), LocalDate.now(), "Transporte"));
        gastoRepository.save(new Gastos(null, "Posto Shell", new BigDecimal("200.00"), LocalDate.of(2025,3,25), "Transporte"));
        gastoRepository.save(new Gastos(null, "Uber", new BigDecimal("20.41"), LocalDate.now(), "Transporte"));
        // Moradia
        gastoRepository.save(new Gastos(null, "Condomínio", new BigDecimal("420.00"), LocalDate.of(2025, 3, 1), "Moradia"));
        gastoRepository.save(new Gastos(null, "IPTU", new BigDecimal("650.00"), LocalDate.of(2025, 1, 20), "Moradia"));
        gastoRepository.save(new Gastos(null, "Conta de Água", new BigDecimal("96.50"), LocalDate.of(2025, 2, 14), "Moradia"));
        gastoRepository.save(new Gastos(null, "Gás de Cozinha", new BigDecimal("105.00"), LocalDate.of(2025, 5, 11), "Moradia"));

        // Saúde
        gastoRepository.save(new Gastos(null, "Exames Clínicos", new BigDecimal("290.00"), LocalDate.of(2025, 3, 7), "Saúde"));
        gastoRepository.save(new Gastos(null, "Óculos de Grau", new BigDecimal("480.00"), LocalDate.of(2025, 4, 21), "Saúde"));
        gastoRepository.save(new Gastos(null, "Plano de Saúde", new BigDecimal("399.99"), LocalDate.of(2025, 5, 1), "Saúde"));

        // Educação
        gastoRepository.save(new Gastos(null, "Mensalidade Faculdade", new BigDecimal("890.00"), LocalDate.of(2025, 2, 5), "Educação"));
        gastoRepository.save(new Gastos(null, "Material Escolar", new BigDecimal("215.30"), LocalDate.of(2025, 1, 18), "Educação"));
        gastoRepository.save(new Gastos(null, "Apostila Técnica", new BigDecimal("67.80"), LocalDate.of(2025, 4, 27), "Educação"));

        // Lazer
        gastoRepository.save(new Gastos(null, "Netflix", new BigDecimal("39.90"), LocalDate.of(2025, 3, 5), "Lazer"));
        gastoRepository.save(new Gastos(null, "Parque Aquático", new BigDecimal("124.00"), LocalDate.of(2025, 4, 14), "Lazer"));
        gastoRepository.save(new Gastos(null, "Assinatura Spotify", new BigDecimal("19.90"), LocalDate.of(2025, 5, 9), "Lazer"));

    }
}
