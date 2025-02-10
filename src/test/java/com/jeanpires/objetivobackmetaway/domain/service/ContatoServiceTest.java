package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import com.jeanpires.objetivobackmetaway.domain.repositories.ContatoRepository;
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
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoService contatoService;

    private Contato contato;

    @BeforeEach
    void setUp() {
        contato = Contato.builder()
                .id(1L)
                .build();
    }

    @Test
    void deveBuscarContatoPorId() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));

        Contato contatoEncontrado = contatoService.buscarPorId(1L);

        assertNotNull(contatoEncontrado);
        assertEquals(1L, contatoEncontrado.getId());

        verify(contatoRepository).findById(1L);
        verifyNoMoreInteractions(contatoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarContatoPorIdInexistente() {
        when(contatoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> contatoService.buscarPorId(1L));

        verify(contatoRepository).findById(1L);
        verifyNoMoreInteractions(contatoRepository);
    }

    @Test
    void deveRemoverContato() {
        doNothing().when(contatoRepository).deleteById(1L);

        assertDoesNotThrow(() -> contatoService.remover(1L));

        verify(contatoRepository).deleteById(1L);
        verifyNoMoreInteractions(contatoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverContatoInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(contatoRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> contatoService.remover(1L));

        verify(contatoRepository).deleteById(1L);
        verifyNoMoreInteractions(contatoRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverContatoEmUso() {
        doThrow(DataIntegrityViolationException.class).when(contatoRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> contatoService.remover(1L));

        verify(contatoRepository).deleteById(1L);
        verifyNoMoreInteractions(contatoRepository);
    }
}
