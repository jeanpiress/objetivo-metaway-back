package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import com.jeanpires.objetivobackmetaway.domain.repositories.EnderecoRepository;
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
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = Endereco.builder()
                .id(1L)
                .build();
    }

    @Test
    void deveBuscarEnderecoPorId() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));

        Endereco enderecoEncontrado = enderecoService.buscarPorId(1L);

        assertNotNull(enderecoEncontrado);
        assertEquals(1L, enderecoEncontrado.getId());

        verify(enderecoRepository).findById(1L);
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarEnderecoPorIdInexistente() {
        when(enderecoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> enderecoService.buscarPorId(1L));

        verify(enderecoRepository).findById(1L);
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveRemoverEndereco() {
        doNothing().when(enderecoRepository).deleteById(1L);

        assertDoesNotThrow(() -> enderecoService.remover(1L));

        verify(enderecoRepository).deleteById(1L);
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverEnderecoInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(enderecoRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> enderecoService.remover(1L));

        verify(enderecoRepository).deleteById(1L);
        verifyNoMoreInteractions(enderecoRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverEnderecoEmUso() {
        doThrow(DataIntegrityViolationException.class).when(enderecoRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> enderecoService.remover(1L));

        verify(enderecoRepository).deleteById(1L);
        verifyNoMoreInteractions(enderecoRepository);
    }
}
