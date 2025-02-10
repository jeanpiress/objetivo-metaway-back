package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.ContatoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.ContatoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.ContatoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.ContatoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import com.jeanpires.objetivobackmetaway.domain.repositories.ContatoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.ContatoService;
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
class ContatoControllerTest {

    @Mock
    private ContatoService contatoService;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ContatoAssembler contatoAssembler;

    @Mock
    private ContatoInputDisasembler contatoDisasembler;

    @InjectMocks
    private ContatoController contatoController;

    private Contato contato;
    private ContatoDto contatoDto;
    private ContatoInput contatoInput;

    @BeforeEach
    void setUp() {
        contato = new Contato();
        contato.setId(1L);

        contatoDto = new ContatoDto();
        contatoDto.setId(1L);

        contatoInput = new ContatoInput();
    }

    @Test
    void deveBuscarContatoPorId() {
        when(contatoService.buscarPorId(1L)).thenReturn(contato);
        when(contatoAssembler.toModel(contato)).thenReturn(contatoDto);

        ResponseEntity<ContatoDto> response = contatoController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(contatoDto, response.getBody());
    }

    @Test
    void deveAdicionarContato() {
        when(contatoDisasembler.toDomainObject(any())).thenReturn(contato);
        when(contatoRepository.save(any())).thenReturn(contato);
        when(contatoAssembler.toModel(any())).thenReturn(contatoDto);

        ResponseEntity<ContatoDto> response = contatoController.adicionar(contatoInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(contatoDto, response.getBody());
    }

    @Test
    void deveAlterarContato() {
        when(contatoService.buscarPorId(1L)).thenReturn(contato);
        doNothing().when(contatoDisasembler).copyToDomainObject(any(), any());
        when(contatoRepository.save(any())).thenReturn(contato);
        when(contatoAssembler.toModel(any())).thenReturn(contatoDto);

        ResponseEntity<ContatoDto> response = contatoController.alterar(contatoInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(contatoDto, response.getBody());
    }

    @Test
    void deveDeletarContato() {
        doNothing().when(contatoService).remover(1L);

        assertDoesNotThrow(() -> contatoController.deletar(1L));
        verify(contatoService, times(1)).remover(1L);
    }
}
