package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.mapper.QuartoMapper;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuartoService {
    @Autowired
    private QuartoRepository quartoRepository;

    // Criar novo quarto
    public QuartoResponseDTO criar(QuartoCreateDTO dto){
        if (quartoRepository.existsByNumeroQuarto(dto.getNumero())){
            throw new RuntimeException("Número de quarto já cadastrado: " + dto.getNumero());
        }

        QuartoModel quarto = QuartoMapper.toEntity(dto);
        QuartoModel salvo = quartoRepository.save(quarto);

        return QuartoMapper.toResponseDTO(salvo);
    }

    // Listar todos os quartos
    public List<QuartoResponseDTO> listarTodos(){
        return quartoRepository.findAll()
                .stream()
                .map(QuartoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar o quarto pelo número
    public QuartoResponseDTO buscarQuartoPorNumero(Integer numero){
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(numero)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado: " + numero));

        return QuartoMapper.toResponseDTO(quarto);
    }

    // Atualizar os dados do quarto
    public QuartoResponseDTO atualizar(Integer numero, QuartoCreateDTO dto){
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(numero)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado: " + numero));

        quarto.setTipoQuarto(dto.getTipo());
        quarto.setPrecoPorNoite(dto.getPrecoPorNoite());

        QuartoModel atualizado = quartoRepository.save(quarto);
        return QuartoMapper.toResponseDTO(atualizado);
    }

    // Atualizar o status do quarto
    public QuartoResponseDTO atualizarStatus(Integer numero, QuartoStatus novoStatus){
        QuartoModel quarto = quartoRepository.findByNumeroQuarto(numero)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado: " + numero));

        quarto.setQuartoStatus(novoStatus);

        QuartoModel atualizado = quartoRepository.save(quarto);
        return QuartoMapper.toResponseDTO(atualizado);
    }

    // Buscar os quartos disponíveis
    public List<QuartoResponseDTO> buscarQuartosDisponiveis(){
        return quartoRepository.findByQuartoStatus(QuartoStatus.DISPONIVEL)
                .stream()
                .map(QuartoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

}
