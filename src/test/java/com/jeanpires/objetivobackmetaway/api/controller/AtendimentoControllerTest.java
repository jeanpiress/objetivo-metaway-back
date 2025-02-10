package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.AtendimentoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.AtendimentoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.AtendimentoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.AtendimentoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import com.jeanpires.objetivobackmetaway.domain.repositories.AtendimentoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.AtendimentoService;
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
class AtendimentoControllerTest {

    @Mock
    private AtendimentoService atendimentoService;

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @Mock
    private AtendimentoAssembler atendimentoAssembler;

    @Mock
    private AtendimentoInputDisasembler atendimentoDisasembler;

    @InjectMocks
    private AtendimentoController atendimentoController;

    private Atendimento atendimento;
    private AtendimentoDto atendimentoDto;
    private AtendimentoInput atendimentoInput;

    @BeforeEach
    void setUp() {
        atendimento = new Atendimento();
        atendimento.setId(1L);

        atendimentoDto = new AtendimentoDto();
        atendimentoDto.setId(1L);

        atendimentoInput = new AtendimentoInput();
    }

    @Test
    void deveBuscarAtendimentoPorId() {
        when(atendimentoService.buscarPorId(1L)).thenReturn(atendimento);
        when(atendimentoAssembler.toModel(atendimento)).thenReturn(atendimentoDto);

        ResponseEntity<AtendimentoDto> response = atendimentoController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(atendimentoDto, response.getBody());
    }

    @Test
    void deveAdicionarAtendimento() {
        when(atendimentoDisasembler.toDomainObject(any())).thenReturn(atendimento);
        when(atendimentoService.adicionar(any())).thenReturn(atendimento);
        when(atendimentoAssembler.toModel(any())).thenReturn(atendimentoDto);

        ResponseEntity<AtendimentoDto> response = atendimentoController.adicionar(atendimentoInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(atendimentoDto, response.getBody());
    }

    @Test
    void deveAlterarAtendimento() {
        when(atendimentoService.buscarPorId(1L)).thenReturn(atendimento);
        doNothing().when(atendimentoDisasembler).copyToDomainObject(any(), any());
        when(atendimentoRepository.save(any())).thenReturn(atendimento);
        when(atendimentoAssembler.toModel(any())).thenReturn(atendimentoDto);

        ResponseEntity<AtendimentoDto> response = atendimentoController.alterar(atendimentoInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(atendimentoDto, response.getBody());
    }

    @Test
    void deveDeletarAtendimento() {
        doNothing().when(atendimentoService).remover(1L);

        assertDoesNotThrow(() -> atendimentoController.deletar(1L));
        verify(atendimentoService, times(1)).remover(1L);
    }
}
