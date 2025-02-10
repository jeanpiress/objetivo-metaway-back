package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.core.security.MetaWaySecurity;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import com.jeanpires.objetivobackmetaway.domain.repositories.PetsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetsRepository petsRepository;

    @Mock
    private MetaWaySecurity security;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = Pet.builder()
                .id(1L)
                .nome("Rex")
                .cliente(Cliente.builder().id(1L).nome("Cliente Teste").build())
                .build();
    }

    @Test
    void deveBuscarPetPorIdAdmin() {
        when(petsRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(security.temAutorizacao("ADMIN")).thenReturn(true);

        Pet petEncontrado = petService.buscarPorId(1L);

        assertNotNull(petEncontrado);
        assertEquals(1L, petEncontrado.getId());

        verify(petsRepository).findById(1L);
        verify(security).temAutorizacao("ADMIN");
        verifyNoMoreInteractions(petsRepository, security);
    }

    @Test
    void deveBuscarPetPorIdComAutorizacaoCliente() {
        when(petsRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(security.temAutorizacao("ADMIN")).thenReturn(false);
        when(security.getUsuarioId()).thenReturn(1L);

        Pet petEncontrado = petService.buscarPorId(1L);

        assertNotNull(petEncontrado);
        assertEquals(1L, petEncontrado.getId());

        verify(petsRepository).findById(1L);
        verify(security).temAutorizacao("ADMIN");
        verify(security).getUsuarioId();
        verifyNoMoreInteractions(petsRepository, security);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarPetPorIdInexistente() {
        when(petsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> petService.buscarPorId(1L));

        verify(petsRepository).findById(1L);
        verifyNoMoreInteractions(petsRepository);
    }

    @Test
    void deveLancarAccessDeniedExceptionQuandoUsuarioNaoForTutor() {
        when(petsRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(security.temAutorizacao("ADMIN")).thenReturn(false);
        when(security.getUsuarioId()).thenReturn(2L);

        assertThrows(AccessDeniedException.class, () -> petService.buscarPorId(1L));

        verify(petsRepository).findById(1L);
        verify(security).temAutorizacao("ADMIN");
        verify(security).getUsuarioId();
        verifyNoMoreInteractions(petsRepository, security);
    }

    @Test
    void deveRemoverPet() {
        doNothing().when(petsRepository).deleteById(1L);

        assertDoesNotThrow(() -> petService.remover(1L));

        verify(petsRepository).deleteById(1L);
        verifyNoMoreInteractions(petsRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverPetInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(petsRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> petService.remover(1L));

        verify(petsRepository).deleteById(1L);
        verifyNoMoreInteractions(petsRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverPetEmUso() {
        doThrow(DataIntegrityViolationException.class).when(petsRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> petService.remover(1L));

        verify(petsRepository).deleteById(1L);
        verifyNoMoreInteractions(petsRepository);
    }
}
