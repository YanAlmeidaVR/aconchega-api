package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede.CpfJaCadastradoException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede.HospedeNotFoundException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.mapper.HospedeMapper;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    @Autowired
    private HospedeRepository hospedeRepository;

    // 1. Cadastrar hóspede
    public HospedeResponseDTO cadastrar(HospedeCreateDTO dto){

        if (hospedeRepository.existsByCpf(dto.getCpf())){
            throw new CpfJaCadastradoException(dto.getCpf());
        }

        HospedeModel hospede = HospedeMapper.toEntity(dto);
        HospedeModel salvo = hospedeRepository.save(hospede);

        return HospedeMapper.toResponseDTO(salvo);
    }

    // 2. Listar todos os hóspedes
    public List<HospedeResponseDTO> listarTodos(){
        return hospedeRepository.findAll()
                .stream()
                .map(HospedeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 3. Buscar hóspede por ID
    public HospedeResponseDTO buscarPorId(Long id) {
        HospedeModel hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new HospedeNotFoundException(id));

        return HospedeMapper.toResponseDTO(hospede);
    }

    // 4. Atualizar hóspede
    public HospedeResponseDTO atualizar(Long id, HospedeCreateDTO dto){

        HospedeModel hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new HospedeNotFoundException(id));

        hospede.setCpf(dto.getCpf());
        hospede.setTelefone(dto.getTelefone());

        HospedeModel atualizado = hospedeRepository.save(hospede);
        return HospedeMapper.toResponseDTO(atualizado);
    }
}
