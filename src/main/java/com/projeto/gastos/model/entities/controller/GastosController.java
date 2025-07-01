package com.projeto.gastos.model.entities.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.gastos.dto.AtualizaDescricaoDTO;
import com.projeto.gastos.model.entities.Gastos;
import com.projeto.gastos.service.GastosService;

@RestController
public class GastosController {
    @Autowired
    private GastosService gastosService;
    
    @PostMapping("/gastos")
    public ResponseEntity<?> salvar(@RequestBody Gastos gasto){

        gastosService.salvar(gasto);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/gastos")
    public ResponseEntity<List<Gastos>> lista() {
        List<Gastos> lista = gastosService.consulta();
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/{id}/descricao")
    public ResponseEntity<Gastos> updateDescricao(@PathVariable Long id, @RequestBody AtualizaDescricaoDTO dto) {
        var gastoAtualizado = gastosService.updateDescricaoPorId(id, dto.getNovaDescricao());
        return ResponseEntity.ok(gastoAtualizado);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Gastos>> consultaCategoria( @RequestParam String categoria){
        List<Gastos> lista = gastosService.consultaCategoria(categoria);
        return ResponseEntity.ok(lista);
    } 
   
    @GetMapping("/categoria/soma")
    public ResponseEntity<BigDecimal> totalCategoria( @RequestParam String categoria){
        var soma = gastosService.somaPorCategoria(categoria);
        return ResponseEntity.ok(soma);
    } 

}
