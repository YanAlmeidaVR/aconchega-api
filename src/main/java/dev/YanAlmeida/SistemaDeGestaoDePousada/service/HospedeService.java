package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.mapper.HospedeMapper;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.HospedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospedeService {

    @Autowired
    HospedeRepository hospedeRepository;

    public HospedeResponseDTO cadastrar(HospedeCreateDTO dto){
        if (hospedeRepository.existsByCpf(dto.getCpf())){
            throw new RuntimeException("CPF já cadastrado: " + dto.getCpf());
        }

        HospedeModel hospede = HospedeMapper.toEntity(dto);
        HospedeModel salvo = hospedeRepository.save(hospede);

        return HospedeMapper.toResponseDTO(salvo);
    }

    public List<HospedeResponseDTO> listarTodos(){
        return hospedeRepository.findAll()
                .stream()
                .map(HospedeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public HospedeResponseDTO buscarPorNome(String nome){
        HospedeModel hospede = hospedeRepository.findByNomeHospede(nome).
                orElseThrow(() -> new RuntimeException("Hóspede não encontrado com nome: " + nome));
        return HospedeMapper.toResponseDTO(hospede);
    }

    public HospedeResponseDTO atualizar(String nome, HospedeCreateDTO dto){
        HospedeModel hospede = hospedeRepository.findByNomeHospede(nome).
                orElseThrow(() -> new RuntimeException("Hóspede não encontrado com nome: " + nome));
        hospede.setCpf(dto.getCpf());
        hospede.setTelefone(dto.getTelefone());

        HospedeModel atualizado = hospedeRepository.save(hospede);
        return HospedeMapper.toResponseDTO(atualizado);
    }

}
