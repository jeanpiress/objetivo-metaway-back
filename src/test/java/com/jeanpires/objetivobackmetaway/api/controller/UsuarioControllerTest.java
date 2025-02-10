package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.UsuarioAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.UsuarioInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.UsuarioDto;
import com.jeanpires.objetivobackmetaway.api.inputs.UsuarioInput;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import com.jeanpires.objetivobackmetaway.domain.repositories.UsuarioRepository;
import com.jeanpires.objetivobackmetaway.domain.service.UsuarioService;
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
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAssembler usuarioAssembler;

    @Mock
    private UsuarioInputDisasembler usuarioDisasembler;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private UsuarioDto usuarioDto;
    private UsuarioInput usuarioInput;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        usuarioDto = new UsuarioDto();
        usuarioDto.setId(1L);

        usuarioInput = new UsuarioInput();
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(usuarioAssembler.toModel(usuario)).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuarioDto, response.getBody());
    }

    @Test
    void deveAdicionarUsuario() {
        when(usuarioDisasembler.toDomainObject(any())).thenReturn(usuario);
        when(usuarioService.adicionar(any())).thenReturn(usuario);
        when(usuarioAssembler.toModel(any())).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.adicionar(usuarioInput);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(usuarioDto, response.getBody());
    }

    @Test
    void deveAlterarUsuario() {
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        doNothing().when(usuarioDisasembler).copyToDomainObject(any(), any());
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(usuarioAssembler.toModel(any())).thenReturn(usuarioDto);

        ResponseEntity<UsuarioDto> response = usuarioController.alterar(usuarioInput, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(usuarioDto, response.getBody());
    }

    @Test
    void deveDeletarUsuario() {
        doNothing().when(usuarioService).remover(1L);

        assertDoesNotThrow(() -> usuarioController.deletar(1L));
        verify(usuarioService, times(1)).remover(1L);
    }
}
