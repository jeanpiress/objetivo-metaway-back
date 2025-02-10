package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.PetAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.PetInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.PetDto;
import com.jeanpires.objetivobackmetaway.api.inputs.PetInput;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import com.jeanpires.objetivobackmetaway.domain.repositories.PetsRepository;
import com.jeanpires.objetivobackmetaway.domain.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private PetsRepository petsRepository;

    @Mock
    private PetAssembler petAssembler;

    @Mock
    private PetInputDisasembler petDisasembler;

    @InjectMocks
    private PetController petController;

    private Pet pet;
    private PetDto petDto;
    private PetInput petInput;

    @BeforeEach
    void setUp() {
        pet = new Pet();
        pet.setId(1L);

        petDto = new PetDto();
        petDto.setId(1L);

        petInput = new PetInput();
    }

    @Test
    void deveBuscarPetPorId() {
        when(petService.buscarPorId(1L)).thenReturn(pet);
        when(petAssembler.toModel(pet)).thenReturn(petDto);

        ResponseEntity<PetDto> response = petController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void deveAdicionarPet() {
        when(petDisasembler.toDomainObject(any())).thenReturn(pet);
        when(petsRepository.save(any())).thenReturn(pet);
        when(petAssembler.toModel(any())).thenReturn(petDto);

        ResponseEntity<PetDto> response = petController.adicionar(petInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void deveAlterarPet() {
        when(petService.buscarPorId(1L)).thenReturn(pet);
        doNothing().when(petDisasembler).copyToDomainObject(any(), any());
        when(petsRepository.save(any())).thenReturn(pet);
        when(petAssembler.toModel(any())).thenReturn(petDto);

        ResponseEntity<PetDto> response = petController.alterar(petInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(petDto, response.getBody());
    }

    @Test
    void deveDeletarPet() {
        doNothing().when(petService).remover(1L);

        assertDoesNotThrow(() -> petController.deletar(1L));
        verify(petService, times(1)).remover(1L);
    }
}
