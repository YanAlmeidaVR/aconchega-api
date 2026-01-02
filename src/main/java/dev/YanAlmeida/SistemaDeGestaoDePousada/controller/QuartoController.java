package dev.YanAlmeida.SistemaDeGestaoDePousada.controller;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.service.QuartoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pousada/quartos")
public class QuartoController {

    @Autowired
    private QuartoService quartoService;

    @PostMapping
    public ResponseEntity<QuartoResponseDTO> criar(@RequestBody QuartoCreateDTO quarto){
        QuartoResponseDTO response = quartoService.criar(quarto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<QuartoResponseDTO>> listar(){
        return ResponseEntity.ok(quartoService.listarTodos());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<QuartoResponseDTO> buscarQuartoPorNumero(@PathVariable Integer numero){
        QuartoResponseDTO response = quartoService.buscarQuartoPorNumero(numero);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoResponseDTO> atualizar(@PathVariable Integer numero, @RequestBody QuartoCreateDTO quarto){
        QuartoResponseDTO response = quartoService.atualizar(numero,quarto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{numero}/status")
    public ResponseEntity<QuartoResponseDTO> atualizarStatus(@PathVariable Integer numero, @RequestParam QuartoStatus status){
        QuartoResponseDTO response = quartoService.atualizarStatus(numero, status);
        return ResponseEntity.ok(response);
    }

    // 6. Buscar quartos disponíveis
    @GetMapping("/disponiveis")
    public ResponseEntity<List<QuartoResponseDTO>> buscarDisponiveis() {
        return ResponseEntity.ok(quartoService.buscarQuartosDisponiveis());
    }
}
