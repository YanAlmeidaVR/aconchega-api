package dev.YanAlmeida.SistemaDeGestaoDePousada.controller;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aconchega/hospedes")
public class HospedeController {

    @Autowired
    private HospedeService hospedeService;

    @PostMapping
    public ResponseEntity<HospedeResponseDTO> cadastrarHospede (@RequestBody HospedeCreateDTO hospede){
        HospedeResponseDTO response = hospedeService.cadastrar(hospede);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<HospedeResponseDTO>> listarHospede(){
        return ResponseEntity.ok(hospedeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospedeResponseDTO> buscarPorId(@PathVariable Long id){
        HospedeResponseDTO response = hospedeService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospedeResponseDTO> atualizar(@PathVariable Long id, @RequestBody HospedeCreateDTO hospede){
        HospedeResponseDTO response = hospedeService.atualizar(id,hospede);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospedeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
