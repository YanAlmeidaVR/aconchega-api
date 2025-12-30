package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import dev.YanAlmeida.SistemaDeGestaoDePousada.mapper.ReservaMapper;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.HospedeRepository;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.QuartoRepository;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    // ===== OPERAÇÕES DE RESERVA =====

    // 1. Criar nova reserva
    @Transactional
    public ReservaResponseDTO criarReserva(ReservaCreateDTO dto){
        // Validar datas
        if (dto.getDataCheckOut().isBefore(dto.getDataCheckIn()) ||
                dto.getDataCheckOut().isEqual(dto.getDataCheckIn())){
            throw new RuntimeException("Data de check-out deve ser posterior ao check-in");
        }

        // Buscar hóspede pelo CPF
        HospedeModel hospede = hospedeRepository.findByCpf(dto.getCpfHospede())
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado com CPF: " + dto.getCpfHospede()));

        // Buscar quarto pelo número
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(dto.getNumeroQuarto())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado: " + dto.getNumeroQuarto()));

        // Verificar se quarto está disponível
        if (quarto.getQuartoStatus() != QuartoStatus.DISPONIVEL){
            throw new RuntimeException("Quarto não está disponível");
        }

        // Verificar conflito de datas (LÓGICA NO SERVICE)
        List<ReservaModel> reservasDoQuarto = reservaRepository.findByNumeroQuarto(dto.getNumeroQuarto());

        boolean temConflito = reservasDoQuarto.stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .anyMatch(r ->
                        r.getDataCheckIn().isBefore(dto.getDataCheckOut()) &&
                                r.getDataCheckOut().isAfter(dto.getDataCheckIn())
                );

        if (temConflito){
            throw new RuntimeException("Quarto já está reservado neste período");
        }

        // Criar reserva com snapshot dos dados
        ReservaModel reserva = new ReservaModel();

        // Dados do hóspede (snapshot)
        reserva.setNomeHospede(hospede.getNomeHospede());
        reserva.setCpfHospede(hospede.getCpf());
        reserva.setTelefoneHospede(hospede.getTelefone());

        // Dados do quarto (snapshot)
        reserva.setNumeroQuarto(quarto.getNumeroQuarto());
        reserva.setTipoQuarto(quarto.getTipoQuarto());

        // Dados da reserva
        reserva.setDataCheckIn(dto.getDataCheckIn());
        reserva.setDataCheckOut(dto.getDataCheckOut());
        reserva.setMetodoPagamento(dto.getMetodoPagamento());

        // Calcular valor total
        long dias = ChronoUnit.DAYS.between(dto.getDataCheckIn(), dto.getDataCheckOut());
        BigDecimal valorTotal = quarto.getPreçoPorNoite().multiply(BigDecimal.valueOf(dias));
        reserva.setValorTotal(valorTotal);

        // Definir status inicial
        reserva.setStatusReserva(StatusReserva.ATIVA);
        reserva.setStatusPagamento(StatusPagamento.PENDENTE);
        reserva.setStatusChave(StatusChave.NAO_DEVOLVIDA);

        // Atualizar status do quarto para OCUPADO
        quarto.setQuartoStatus(QuartoStatus.OCUPADO);
        quartoRepository.save(quarto);

        // Salvar reserva
        ReservaModel reservaSalva = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(reservaSalva);
    }

    // 2. Fazer check-in
    @Transactional
    public ReservaResponseDTO fazerCheckIn(Long reservaId){
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));

        // Validar status
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new RuntimeException("Reserva não está ativa");
        }

        // Validar se é o dia do check-in
        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(reserva.getDataCheckIn())){
            throw new RuntimeException("Ainda não é o dia do check-in");
        }

        // Atualizar status do quarto
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(reserva.getNumeroQuarto())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
        quarto.setQuartoStatus(QuartoStatus.OCUPADO);
        quartoRepository.save(quarto);

        // Atualiza no banco de dados
        ReservaModel atualizada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(atualizada);
    }

    // 3. Fazer check-out
    @Transactional
    public ReservaResponseDTO fazerCheckOut(Long reservaId){
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));

        // Validar status
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new RuntimeException("Reserva não está ativa");
        }

        // Verificar pagamento
        if (reserva.getStatusPagamento() != StatusPagamento.PAGO){
            throw new RuntimeException("Pagamento pendente. Realize o pagamento antes do check-out");
        }

        // Verificar devolução da chave
        if (reserva.getStatusChave() != StatusChave.DEVOLVIDA){
            throw new RuntimeException("Chave não foi devolvida");
        }

        // Atualizar status
        reserva.setStatusReserva(StatusReserva.FINALIZADA);

        // Liberar quarto
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(reserva.getNumeroQuarto())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
        quarto.setQuartoStatus(QuartoStatus.DISPONIVEL);
        quartoRepository.save(quarto);

        ReservaModel atualizada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(atualizada);
    }

    // 4. Cancelar reserva
    @Transactional
    public ReservaResponseDTO cancelarReserva(Long reservaId){
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));

        // Só pode cancelar se estiver ativa
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new RuntimeException("Apenas reservas ativas podem ser canceladas");
        }

        // Atualizar status
        reserva.setStatusReserva(StatusReserva.CANCELADA);

        // Liberar quarto
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(reserva.getNumeroQuarto())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
        quarto.setQuartoStatus(QuartoStatus.DISPONIVEL);
        quartoRepository.save(quarto);

        ReservaModel atualizada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(atualizada);
    }

    // 5. Processar pagamento
    @Transactional
    public ReservaResponseDTO processarPagamento(Long reservaId){
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));

        // Atualizar status de pagamento
        reserva.setStatusPagamento(StatusPagamento.PAGO);

        ReservaModel atualizada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(atualizada);
    }

    // ===== CONSULTAS =====

    // 6. Listar todas as reservas
    public List<ReservaResponseDTO> listarReservas(){
        return reservaRepository.findAll()
                .stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 7. Buscar reservas do dia (check-ins e check-outs)
    public List<ReservaResponseDTO> buscarReservasDoDia(){
        LocalDate hoje = LocalDate.now();

        // Buscar check-ins de hoje (status ATIVA e data check-in = hoje)
        List<ReservaModel> checkIns = reservaRepository.findByDataCheckIn(hoje).stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .collect(Collectors.toList());

        // Buscar check-outs de hoje (status ATIVA e data check-out = hoje)
        List<ReservaModel> checkOuts = reservaRepository.findByDataCheckOut(hoje).stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .collect(Collectors.toList());

        checkIns.addAll(checkOuts);

        return checkIns.stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 8. Buscar reservas por quarto
    public List<ReservaResponseDTO> buscarPorQuarto(Integer numeroQuarto){
        return reservaRepository.findByNumeroQuarto(numeroQuarto)
                .stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    // ===== MÉTRICAS =====

    // 9. Calcular receita em período
    public BigDecimal calcularReceita(LocalDate inicio, LocalDate fim){
        List<ReservaModel> reservas = reservaRepository.findAll().stream()
                .filter(r -> r.getStatusPagamento() == StatusPagamento.PAGO)
                .filter(r -> !r.getDataCheckIn().isBefore(inicio) && !r.getDataCheckOut().isAfter(fim))
                .collect(Collectors.toList());

        return reservas.stream()
                .map(ReservaModel::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 10. Calcular taxa de ocupação
    public Double calcularTaxaOcupacao(){
        long totalQuartos = quartoRepository.count();
        long quartosOcupados = quartoRepository.findByQuartoStatus(QuartoStatus.OCUPADO).size();

        if (totalQuartos == 0) return 0.0;

        return (quartosOcupados * 100.0) / totalQuartos;
    }
}