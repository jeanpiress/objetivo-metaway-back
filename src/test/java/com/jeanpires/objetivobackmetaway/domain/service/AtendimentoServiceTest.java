package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import com.jeanpires.objetivobackmetaway.domain.repositories.AtendimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendimentoServiceTest {

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @InjectMocks
    private AtendimentoService atendimentoService;

    private Atendimento atendimento;

    @BeforeEach
    void setUp() {
        atendimento = Atendimento.builder().id(1L).build();
    }

    @Test
    void deveBuscarAtendimentoPorId() {
        when(atendimentoRepository.findById(1L)).thenReturn(Optional.of(atendimento));

        Atendimento atendimentoEncontrado = atendimentoService.buscarPorId(1L);

        assertNotNull(atendimentoEncontrado);
        assertEquals(1L, atendimentoEncontrado.getId());

        verify(atendimentoRepository ).findById(1L);
        verifyNoMoreInteractions(atendimentoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarAtendimentoPorIdInexistente() {
        when(atendimentoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> atendimentoService.buscarPorId(1L));

        verify(atendimentoRepository ).findById(1L);
        verifyNoMoreInteractions(atendimentoRepository);
    }

    @Test
    void deveRemoverAtendimento() {
        doNothing().when(atendimentoRepository).deleteById(1L);

        assertDoesNotThrow(() -> atendimentoService.remover(1L));

        verify(atendimentoRepository ).deleteById(1L);
        verifyNoMoreInteractions(atendimentoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverAtendimentoInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(atendimentoRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> atendimentoService.remover(1L));

        verify(atendimentoRepository ).deleteById(1L);
        verifyNoMoreInteractions(atendimentoRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverAtendimentoEmUso() {
        doThrow(DataIntegrityViolationException.class).when(atendimentoRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> atendimentoService.remover(1L));

        verify(atendimentoRepository ).deleteById(1L);
        verifyNoMoreInteractions(atendimentoRepository);
    }

    @Test
    void deveAdicionarAtendimento() {
        when(atendimentoRepository.save(atendimento)).thenReturn(atendimento);

        Atendimento atendimentoCriado = atendimentoService.adicionar(atendimento);

        assertNotNull(atendimentoCriado);
        assertEquals(atendimento.getDescricao(), atendimentoCriado.getDescricao());
        assertNotNull(atendimentoCriado.getData());
        assertTrue(atendimentoCriado.getData().isBefore(LocalDateTime.now().plusSeconds(1)));

        verify(atendimentoRepository).save(atendimento);
        verifyNoMoreInteractions(atendimentoRepository);
    }
}
