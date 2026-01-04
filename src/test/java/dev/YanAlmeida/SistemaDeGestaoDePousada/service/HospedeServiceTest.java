package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede.CpfJaCadastradoException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede.HospedeNotFoundException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.HospedeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospedeServiceTest {

    @InjectMocks
    private HospedeService hospedeService;

    @Mock
    private HospedeRepository hospedeRepository;


    @Test
    void deveCadastrarHospedeComSucesso(){
        // Given
        HospedeCreateDTO dto = new HospedeCreateDTO();
        dto.setNome("João");
        dto.setTelefone("999999999");
        dto.setCpf("12345678901");

        when(hospedeRepository.existsByCpf(anyString())).thenReturn(false);
        when(hospedeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        var response = hospedeService.cadastrar(dto);

        // Then
        assertNotNull(response);
        verify(hospedeRepository).existsByCpf(anyString());
        verify(hospedeRepository).save(any(HospedeModel.class));
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaExistir(){
        // Given
        HospedeCreateDTO dto = new HospedeCreateDTO();
        dto.setNome("João");
        dto.setTelefone("999999999");
        dto.setCpf("12345678901");

        when(hospedeRepository.existsByCpf(anyString())).thenReturn(true);

        // When / Then
        assertThrows(CpfJaCadastradoException.class,
                () -> hospedeService.cadastrar(dto));

        verify(hospedeRepository, never()).save(any());
    }
    @Test
    void deveListarTodosOsHospedes(){
        // Given
        HospedeModel h1 = new HospedeModel();
        HospedeModel h2 = new HospedeModel();

        when(hospedeRepository.findAll()).thenReturn(List.of(h1, h2));

        // When
        var lista = hospedeService.listarTodos();

        // Then
        assertEquals(2, lista.size());
        verify(hospedeRepository).findAll();
    }

    @Test
    void deveBuscarHospedePorIdComSucesso() {
        // Given
        HospedeModel hospede = new HospedeModel();
        hospede.setId(1L);

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));

        // When
        var response = hospedeService.buscarPorId(1L);

        // Then
        assertNotNull(response);
        verify(hospedeRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoHospedeNaoExistir() {
        // Given
        when(hospedeRepository.findById(99L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(HospedeNotFoundException.class,
                () -> hospedeService.buscarPorId(99L));
    }

    @Test
    void deveAtualizarHospedeComSucesso() {
        // Given
        HospedeModel hospede = new HospedeModel();
        hospede.setId(1L);

        HospedeCreateDTO dto = new HospedeCreateDTO();
        dto.setNome("João");
        dto.setTelefone("999999999");
        dto.setCpf("12345678901");

        when(hospedeRepository.findById(1L)).thenReturn(Optional.of(hospede));
        when(hospedeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        var response = hospedeService.atualizar(1L, dto);

        // Then
        assertNotNull(response);
        verify(hospedeRepository).findById(1L);
        verify(hospedeRepository).save(hospede);
    }

    @Test
    void deveLancarExcecaoAoAtualizarHospedeInexistente() {
        // Given
        HospedeCreateDTO dto = new HospedeCreateDTO();

        when(hospedeRepository.findById(10L)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(HospedeNotFoundException.class,
                () -> hospedeService.atualizar(10L, dto));
    }
}
