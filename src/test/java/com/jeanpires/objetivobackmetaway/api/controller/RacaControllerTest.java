package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.RacaAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.RacaInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.RacaDto;
import com.jeanpires.objetivobackmetaway.api.inputs.RacaInput;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import com.jeanpires.objetivobackmetaway.domain.repositories.RacaRepository;
import com.jeanpires.objetivobackmetaway.domain.service.RacaService;
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
class RacaControllerTest {

    @Mock
    private RacaService racaService;

    @Mock
    private RacaRepository racaRepository;

    @Mock
    private RacaAssembler racaAssembler;

    @Mock
    private RacaInputDisasembler racaDisasembler;

    @InjectMocks
    private RacaController racaController;

    private Raca raca;
    private RacaDto racaDto;
    private RacaInput racaInput;

    @BeforeEach
    void setUp() {
        raca = new Raca();
        raca.setId(1L);

        racaDto = new RacaDto();
        racaDto.setId(1L);

        racaInput = new RacaInput();
    }

    @Test
    void deveBuscarRacaPorId() {
        when(racaService.buscarPorId(1L)).thenReturn(raca);
        when(racaAssembler.toModel(raca)).thenReturn(racaDto);

        ResponseEntity<RacaDto> response = racaController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(racaDto, response.getBody());
    }

    @Test
    void deveAdicionarRaca() {
        when(racaDisasembler.toDomainObject(any())).thenReturn(raca);
        when(racaRepository.save(any())).thenReturn(raca);
        when(racaAssembler.toModel(any())).thenReturn(racaDto);

        ResponseEntity<RacaDto> response = racaController.adicionar(racaInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(racaDto, response.getBody());
    }

    @Test
    void deveAlterarRaca() {
        when(racaService.buscarPorId(1L)).thenReturn(raca);
        doNothing().when(racaDisasembler).copyToDomainObject(any(), any());
        when(racaRepository.save(any())).thenReturn(raca);
        when(racaAssembler.toModel(any())).thenReturn(racaDto);

        ResponseEntity<RacaDto> response = racaController.alterar(racaInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(racaDto, response.getBody());
    }

    @Test
    void deveDeletarRaca() {
        doNothing().when(racaService).remover(1L);

        assertDoesNotThrow(() -> racaController.deletar(1L));
        verify(racaService, times(1)).remover(1L);
    }
}
