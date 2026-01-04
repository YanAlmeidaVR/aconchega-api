package dev.YanAlmeida.SistemaDeGestaoDePousada.service;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.quarto.NumeroQuartoJaCadastradoException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.exception.quarto.QuartoNotFoundException;
import dev.YanAlmeida.SistemaDeGestaoDePousada.repository.QuartoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuartoServiceTest {

    @InjectMocks
    private QuartoService quartoService;

    @Mock
    private QuartoRepository quartoRepository;

    @Test
    void deveCriarQuartoComSucesso() {
        QuartoCreateDTO dto = new QuartoCreateDTO();
        dto.setNumero(101);
        dto.setTipo(TipoQuarto.CASAL);
        dto.setPrecoPorNoite(BigDecimal.valueOf(150));

        when(quartoRepository.existsByNumeroQuarto(101)).thenReturn(false);
        when(quartoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = quartoService.criar(dto);

        assertNotNull(response);
        assertEquals(101, response.getNumero());
        verify(quartoRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarQuartoComNumeroJaExistente() {
        QuartoCreateDTO dto = new QuartoCreateDTO();
        dto.setNumero(101);

        when(quartoRepository.existsByNumeroQuarto(101)).thenReturn(true);

        assertThrows(NumeroQuartoJaCadastradoException.class,
                () -> quartoService.criar(dto));

        verify(quartoRepository, never()).save(any());
    }

    @Test
    void deveListarTodosOsQuartos() {
        when(quartoRepository.findAll())
                .thenReturn(List.of(new QuartoModel(), new QuartoModel()));

        var quartos = quartoService.listarTodos();

        assertEquals(2, quartos.size());
        verify(quartoRepository).findAll();
    }

    @Test
    void deveBuscarQuartoPorNumeroComSucesso() {
        QuartoModel quarto = new QuartoModel();
        quarto.setNumeroQuarto(101);

        when(quartoRepository.findByNumeroQuarto(101))
                .thenReturn(Optional.of(quarto));

        var response = quartoService.buscarQuartoPorNumero(101);

        assertEquals(101, response.getNumero());
        verify(quartoRepository).findByNumeroQuarto(101);
    }

    @Test
    void deveLancarExcecaoAoBuscarQuartoInexistente() {
        when(quartoRepository.findByNumeroQuarto(999))
                .thenReturn(Optional.empty());

        assertThrows(QuartoNotFoundException.class,
                () -> quartoService.buscarQuartoPorNumero(999));
    }

    @Test
    void deveAtualizarStatusDoQuartoComSucesso() {
        QuartoModel quarto = new QuartoModel();
        quarto.setNumeroQuarto(101);
        quarto.setQuartoStatus(QuartoStatus.DISPONIVEL);

        when(quartoRepository.findByNumeroQuarto(101))
                .thenReturn(Optional.of(quarto));
        when(quartoRepository.save(any())).thenReturn(quarto);

        var response = quartoService.atualizarStatus(101, QuartoStatus.OCUPADO);

        assertEquals(QuartoStatus.OCUPADO, response.getStatus());
        verify(quartoRepository).save(quarto);
    }

    @Test
    void deveBuscarQuartosDisponiveis() {
        QuartoModel quarto = new QuartoModel();
        quarto.setQuartoStatus(QuartoStatus.DISPONIVEL);

        when(quartoRepository.findByQuartoStatus(QuartoStatus.DISPONIVEL))
                .thenReturn(List.of(quarto));

        var quartos = quartoService.buscarQuartosDisponiveis();

        assertEquals(1, quartos.size());
        verify(quartoRepository)
                .findByQuartoStatus(QuartoStatus.DISPONIVEL);
    }
}
