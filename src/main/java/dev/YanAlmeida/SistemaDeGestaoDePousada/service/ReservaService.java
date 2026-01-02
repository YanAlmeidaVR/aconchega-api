package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva.*;
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

    // OPERAÇÕES DE RESERVA

    // 1. Criar nova reserva
    @Transactional
    public ReservaResponseDTO criarReserva(ReservaCreateDTO dto){
        // Validar datas
        if (!dto.getDataCheckOut().isAfter(dto.getDataCheckIn())){
            throw new CheckInInvalidoException("Data de check-out deve ser posterior ao check-in");
        }

        // Buscar hóspede pelo ID
        HospedeModel hospede = hospedeRepository.findById(dto.getHospedeId())
                .orElseThrow(() -> new ReservaNotFoundExceptionById(dto.getHospedeId()));

        // Buscar quarto pelo número
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(dto.getNumeroQuarto())
                .orElseThrow(() -> new ReservaNotFoundExceptionByNumber(dto.getNumeroQuarto()));

        // Verificar conflito de datas
        List<ReservaModel> reservasDoQuarto = reservaRepository.findByNumeroQuarto(dto.getNumeroQuarto());

        boolean temConflito = reservaRepository.findByNumeroQuarto(dto.getNumeroQuarto())
                .stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .anyMatch(r ->
                        r.getDataCheckIn().isBefore(dto.getDataCheckOut()) &&
                                r.getDataCheckOut().isAfter(dto.getDataCheckIn())
                );

        if (temConflito) {throw new QuartoOcupadoException(
                    dto.getNumeroQuarto(),
                    dto.getDataCheckIn(),
                    dto.getDataCheckOut()
            );
        }

        // Começando a criar a reserva
        ReservaModel reserva = new ReservaModel();

        // Dados do hóspede
        reserva.setNomeHospede(hospede.getNomeHospede());
        reserva.setCpfHospede(hospede.getCpf());
        reserva.setTelefoneHospede(hospede.getTelefone());

        // Dados do quarto
        reserva.setNumeroQuarto(quarto.getNumeroQuarto());
        reserva.setTipoQuarto(quarto.getTipoQuarto());

        // Dados da reserva
        reserva.setDataCheckIn(dto.getDataCheckIn());
        reserva.setDataCheckOut(dto.getDataCheckOut());
        reserva.setMetodoPagamento(dto.getMetodoPagamento());

        // Calcular valor total
        long dias = ChronoUnit.DAYS.between(dto.getDataCheckIn(), dto.getDataCheckOut());
        BigDecimal valorTotal = quarto.getPrecoPorNoite().multiply(BigDecimal.valueOf(dias));
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

        // Busca a reserva pelo id
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundExceptionById(reservaId));

        // Verifica o status da reserva
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new ReservaJaCanceladaException();
        }

        // Verifica se é o dia de check-in
        if (LocalDate.now().isBefore(reserva.getDataCheckIn())){
            throw new CheckInInvalidoException("Ainda não é o dia do check-in");
        }

        return ReservaMapper.toResponseDTO(reserva);
    }


    // 3. Registrar Devolucao da Chave
    @Transactional
    public ReservaResponseDTO registrarDevolucaoChave(Long reservaId){
        // Busca a reserva pelo id
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada: " + reservaId));

        // Validar se a reserva está ativa
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new RuntimeException("Apenas reservas ativas podem devolver chaves");
        }

        // Atualizar status da chave
        reserva.setStatusChave(StatusChave.DEVOLVIDA);

        ReservaModel atualizada = reservaRepository.save(reserva);
        return ReservaMapper.toResponseDTO(atualizada);
    }

    // 4. Fazer Check-Out
    @Transactional
    public ReservaResponseDTO fazerCheckOut(Long reservaId){

        // Busca a reserva pelo id
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundExceptionById(reservaId));

        // Verifica o status da reserva
        if (reserva.getStatusReserva() != StatusReserva.ATIVA) {
            throw new ReservaJaCanceladaException();
        }

        // Verifica o status do pagamento
        if (reserva.getStatusPagamento() != StatusPagamento.PAGO){
            throw new PagamentoInvalidoException("Pagamento pendente");
        }

        // Verifica se a chave foi devolvida ou não
        if (reserva.getStatusChave() != StatusChave.DEVOLVIDA){
            throw new CheckInInvalidoException("Chave não foi devolvida");
        }

        // Muda o status da reserva para FINALIZADA
        reserva.setStatusReserva(StatusReserva.FINALIZADA);
        return ReservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }


    // 5. Cancelar reserva
    @Transactional
    public ReservaResponseDTO cancelarReserva(Long reservaId){

        // Busca a reserva pelo id
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundExceptionById(reservaId));

        // Verifica o status da reserva
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new ReservaJaCanceladaException();
        }

        // Muda o status da reserva para CANCELADA
        reserva.setStatusReserva(StatusReserva.CANCELADA);
        return ReservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }


    // 6. Processar pagamento
    @Transactional
    public ReservaResponseDTO processarPagamento(Long reservaId, MetodoPagamento metodoPagamento){

        // Busca a reserva pelo id
        ReservaModel reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNotFoundExceptionById(reservaId));

        // Verifica se a reserva está ativa
        if (reserva.getStatusReserva() != StatusReserva.ATIVA){
            throw new ReservaJaCanceladaException();
        }

        // Coloca o metódo de pagamento e muda o status para PAGO
        reserva.setMetodoPagamento(metodoPagamento);
        reserva.setStatusPagamento(StatusPagamento.PAGO);

        return ReservaMapper.toResponseDTO(reservaRepository.save(reserva));
    }


    // CONSULTAS

    // 7. Listar todas as reservas
    public List<ReservaResponseDTO> listarReservas(){
        return reservaRepository.findAll()
                .stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 8. Buscar reservas do dia (check-ins e check-outs)
    public List<ReservaResponseDTO> buscarReservasDoDia(){
        LocalDate hoje = LocalDate.now();

        // Buscar check-ins de hoje
        List<ReservaModel> checkIns = reservaRepository.findByDataCheckIn(hoje).stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .collect(Collectors.toList());

        // Buscar check-outs de hoje
        List<ReservaModel> checkOuts = reservaRepository.findByDataCheckOut(hoje).stream()
                .filter(r -> r.getStatusReserva() == StatusReserva.ATIVA)
                .collect(Collectors.toList());

        checkIns.addAll(checkOuts);

        return checkIns.stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 9. Buscar reservas por quarto
    public List<ReservaResponseDTO> buscarPorQuarto(Integer numeroQuarto){
        return reservaRepository.findByNumeroQuarto(numeroQuarto)
                .stream()
                .map(ReservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    // MÉTRICAS

    // 10. Calcular receita em período
    public BigDecimal calcularReceita(LocalDate inicio, LocalDate fim){
        List<ReservaModel> reservas = reservaRepository.findAll().stream()
                .filter(r -> r.getStatusPagamento() == StatusPagamento.PAGO)
                .filter(r -> !r.getDataCheckIn().isBefore(inicio) && !r.getDataCheckOut().isAfter(fim))
                .collect(Collectors.toList());

        return reservas.stream()
                .map(ReservaModel::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 11. Calcular taxa de ocupação
    public Double calcularTaxaOcupacao(){
        long totalQuartos = quartoRepository.count();
        long quartosOcupados = quartoRepository.findByQuartoStatus(QuartoStatus.OCUPADO).size();

        if (totalQuartos == 0) return 0.0;

        return (quartosOcupados * 100.0) / totalQuartos;
    }
}