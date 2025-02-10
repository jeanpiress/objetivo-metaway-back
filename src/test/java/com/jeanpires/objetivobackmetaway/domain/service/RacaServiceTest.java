package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import com.jeanpires.objetivobackmetaway.domain.repositories.RacaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RacaServiceTest {

    @Mock
    private RacaRepository racaRepository;

    @InjectMocks
    private RacaService racaService;

    private Raca raca;

    @BeforeEach
    void setUp() {
        raca = Raca.builder()
                .id(1L)
                .build();
    }

    @Test
    void deveBuscarRacaPorId() {
        when(racaRepository.findById(1L)).thenReturn(Optional.of(raca));

        Raca racaEncontrada = racaService.buscarPorId(1L);

        assertNotNull(racaEncontrada);
        assertEquals(1L, racaEncontrada.getId());

        verify(racaRepository).findById(1L);
        verifyNoMoreInteractions(racaRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarRacaPorIdInexistente() {
        when(racaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> racaService.buscarPorId(1L));

        verify(racaRepository).findById(1L);
        verifyNoMoreInteractions(racaRepository);
    }

    @Test
    void deveRemoverRaca() {
        doNothing().when(racaRepository).deleteById(1L);

        assertDoesNotThrow(() -> racaService.remover(1L));

        verify(racaRepository).deleteById(1L);
        verifyNoMoreInteractions(racaRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverRacaInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(racaRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> racaService.remover(1L));

        verify(racaRepository).deleteById(1L);
        verifyNoMoreInteractions(racaRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverRacaEmUso() {
        doThrow(DataIntegrityViolationException.class).when(racaRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> racaService.remover(1L));

        verify(racaRepository).deleteById(1L);
        verifyNoMoreInteractions(racaRepository);
    }
}
