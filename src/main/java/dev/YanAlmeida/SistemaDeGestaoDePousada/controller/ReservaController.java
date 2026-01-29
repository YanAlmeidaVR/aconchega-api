package dev.YanAlmeida.SistemaDeGestaoDePousada.controller;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.MetodoPagamento;
import dev.YanAlmeida.SistemaDeGestaoDePousada.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/aconchega/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // 1. Criar reserva
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@RequestBody ReservaCreateDTO reserva){
        ReservaResponseDTO response = reservaService.criarReserva(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. Fazer check-in
    @PutMapping("/{id}/check-in")
    public ResponseEntity<ReservaResponseDTO> fazerCheckIn(@PathVariable Long id){
        return ResponseEntity.ok(reservaService.fazerCheckIn(id));
    }

    // 3. Registrar devolução da chave
    @PutMapping("/{id}/devolucao-chave")
    public ResponseEntity<ReservaResponseDTO> devolverChave(@PathVariable Long id){
        return ResponseEntity.ok(reservaService.registrarDevolucaoChave(id));
    }

    // 4. Fazer check-out
    @PutMapping("/{id}/check-out")
    public ResponseEntity<ReservaResponseDTO> fazerCheckOut(@PathVariable Long id){
        return ResponseEntity.ok(reservaService.fazerCheckOut(id));
    }

    // 5. Cancelar reserva
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable Long id){
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }

    // 6. Processar pagamento
    @PutMapping("/{id}/pagamento")
    public ResponseEntity<ReservaResponseDTO> processarPagamento(
            @PathVariable Long id,
            @RequestParam MetodoPagamento metodoPagamento
    ) {
        return ResponseEntity.ok(
                reservaService.processarPagamento(id, metodoPagamento)
        );
    }

    // 7. Listar todas as reservas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas(){
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    // 8. Reservas do dia
    @GetMapping("/hoje")
    public ResponseEntity<List<ReservaResponseDTO>> reservasDoDia(){
        return ResponseEntity.ok(reservaService.buscarReservasDoDia());
    }

    // 9. Reservas por quarto
    @GetMapping("/quarto/{numeroQuarto}")
    public ResponseEntity<List<ReservaResponseDTO>> buscarPorQuarto(
            @PathVariable Integer numeroQuarto
    ){
        return ResponseEntity.ok(reservaService.buscarPorQuarto(numeroQuarto));
    }

    // 10. Receita por período
    @GetMapping("/receita")
    public ResponseEntity<BigDecimal> calcularReceita(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ){
        return ResponseEntity.ok(reservaService.calcularReceita(inicio, fim));
    }

    // 11. Taxa de ocupação
    @GetMapping("/taxa-ocupacao")
    public ResponseEntity<Double> taxaOcupacao(){
        return ResponseEntity.ok(reservaService.calcularTaxaOcupacao());
    }
}
