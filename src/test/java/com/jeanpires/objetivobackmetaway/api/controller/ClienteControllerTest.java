package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.ClienteAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.ClienteInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.ClienteDto;
import com.jeanpires.objetivobackmetaway.api.inputs.ClienteInput;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import com.jeanpires.objetivobackmetaway.domain.repositories.ClienteRepository;
import com.jeanpires.objetivobackmetaway.domain.service.ClienteService;
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
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteAssembler clienteAssembler;

    @Mock
    private ClienteInputDisasembler clienteDisasembler;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private ClienteDto clienteDto;
    private ClienteInput clienteInput;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Teste");

        clienteDto = new ClienteDto();
        clienteDto.setId(1L);
        clienteDto.setNome("Cliente Teste");

        clienteInput = new ClienteInput();
        clienteInput.setNome("Cliente Teste");
    }

    @Test
    void deveBuscarClientePorId() {
        when(clienteService.buscarPorId(1L)).thenReturn(cliente);
        when(clienteAssembler.toModel(cliente)).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clienteDto, response.getBody());
    }

    @Test
    void deveAdicionarCliente() {
        when(clienteDisasembler.toDomainObject(any())).thenReturn(cliente);
        when(clienteService.adicionar(any())).thenReturn(cliente);
        when(clienteAssembler.toModel(any())).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.adicionar(clienteInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(clienteDto, response.getBody());
    }

    @Test
    void deveAlterarCliente() {
        when(clienteService.buscarPorId(1L)).thenReturn(cliente);
        doNothing().when(clienteDisasembler).copyToDomainObject(any(), any());
        when(clienteRepository.save(any())).thenReturn(cliente);
        when(clienteAssembler.toModel(any())).thenReturn(clienteDto);

        ResponseEntity<ClienteDto> response = clienteController.alterar(clienteInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(clienteDto, response.getBody());
    }

    @Test
    void deveDeletarCliente() {
        doNothing().when(clienteService).remover(1L);

        assertDoesNotThrow(() -> clienteController.deletar(1L));
        verify(clienteService, times(1)).remover(1L);
    }
}
