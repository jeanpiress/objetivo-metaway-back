package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.EnderecoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.EnderecoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.EnderecoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.EnderecoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import com.jeanpires.objetivobackmetaway.domain.repositories.EnderecoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.EnderecoService;
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
class EnderecoControllerTest {

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoAssembler enderecoAssembler;

    @Mock
    private EnderecoInputDisasembler enderecoDisasembler;

    @InjectMocks
    private EnderecoController enderecoController;

    private Endereco endereco;
    private EnderecoDto enderecoDto;
    private EnderecoInput enderecoInput;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
        endereco.setId(1L);

        enderecoDto = new EnderecoDto();
        enderecoDto.setId(1L);

        enderecoInput = new EnderecoInput();
    }

    @Test
    void deveBuscarEnderecoPorId() {
        when(enderecoService.buscarPorId(1L)).thenReturn(endereco);
        when(enderecoAssembler.toModel(endereco)).thenReturn(enderecoDto);

        ResponseEntity<EnderecoDto> response = enderecoController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(enderecoDto, response.getBody());
    }

    @Test
    void deveAdicionarEndereco() {
        when(enderecoDisasembler.toDomainObject(any())).thenReturn(endereco);
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(enderecoAssembler.toModel(any())).thenReturn(enderecoDto);

        ResponseEntity<EnderecoDto> response = enderecoController.adicionar(enderecoInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(enderecoDto, response.getBody());
    }

    @Test
    void deveAlterarEndereco() {
        when(enderecoService.buscarPorId(1L)).thenReturn(endereco);
        doNothing().when(enderecoDisasembler).copyToDomainObject(any(), any());
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(enderecoAssembler.toModel(any())).thenReturn(enderecoDto);

        ResponseEntity<EnderecoDto> response = enderecoController.alterar(enderecoInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(enderecoDto, response.getBody());
    }

    @Test
    void deveDeletarEndereco() {
        doNothing().when(enderecoService).remover(1L);

        assertDoesNotThrow(() -> enderecoController.deletar(1L));
        verify(enderecoService, times(1)).remover(1L);
    }
}
