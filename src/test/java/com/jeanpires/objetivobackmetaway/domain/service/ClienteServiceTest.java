package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.core.security.MetaWaySecurity;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import com.jeanpires.objetivobackmetaway.domain.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private MetaWaySecurity security;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1L)
                .build();
    }

    @Test
    void deveBuscarClientePorIdComAutorizacaoAdmin() {
        when(security.temAutorizacao("ADMIN")).thenReturn(true);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarPorId(1L);

        assertNotNull(clienteEncontrado);
        assertEquals(1L, clienteEncontrado.getId());

        verify(clienteRepository).findById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveBuscarClientePorIdComAutorizacaoClienteProprio() {
        when(security.temAutorizacao("ADMIN")).thenReturn(false);
        when(security.getUsuarioId()).thenReturn(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente clienteEncontrado = clienteService.buscarPorId(1L);

        assertNotNull(clienteEncontrado);
        assertEquals(1L, clienteEncontrado.getId());

        verify(clienteRepository).findById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveLancarAccessDeniedExceptionAoBuscarClientePorIdComAutorizacaoNegada() {
        when(security.temAutorizacao("ADMIN")).thenReturn(false);
        when(security.getUsuarioId()).thenReturn(2L);

        assertThrows(AccessDeniedException.class, () -> clienteService.buscarPorId(1L));

        verify(security).temAutorizacao("ADMIN");
        verify(security).getUsuarioId();
        verifyNoMoreInteractions(security);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarClientePorIdInexistente() {
        when(security.temAutorizacao("ADMIN")).thenReturn(true);
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> clienteService.buscarPorId(1L));

        verify(clienteRepository).findById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveRemoverCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.remover(1L));

        verify(clienteRepository).deleteById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverClienteInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(clienteRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> clienteService.remover(1L));

        verify(clienteRepository).deleteById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverClienteEmUso() {
        doThrow(DataIntegrityViolationException.class).when(clienteRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> clienteService.remover(1L));

        verify(clienteRepository).deleteById(1L);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveAdicionarClienteComAutorizacaoAdmin() {
        when(security.temAutorizacao("ADMIN")).thenReturn(true);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteAdicionado = clienteService.adicionar(cliente);

        assertNotNull(clienteAdicionado);
        assertEquals(cliente.getCpf(), clienteAdicionado.getCpf());
        assertEquals(LocalDate.now(), clienteAdicionado.getDataCadastro());

        verify(clienteRepository).save(cliente);
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void deveAdicionarClienteSemAutorizacaoAdmin() {
        when(security.temAutorizacao("ADMIN")).thenReturn(false);
        when(security.getUsuarioCpf()).thenReturn("98765432100");
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        clienteService.adicionar(cliente);

        assertEquals("98765432100", cliente.getCpf());

        verify(clienteRepository).save(cliente);
        verifyNoMoreInteractions(clienteRepository);
    }
}
