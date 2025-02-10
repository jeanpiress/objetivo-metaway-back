package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.enuns.Perfil;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Permissao;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import com.jeanpires.objetivobackmetaway.domain.repositories.PermissaoRepository;
import com.jeanpires.objetivobackmetaway.domain.repositories.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PermissaoRepository permissaoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .cpf("12345678900")
                .build();
    }

    @Test
    void deveBuscarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario usuarioEncontrado = usuarioService.buscarPorId(1L);

        assertNotNull(usuarioEncontrado);
        assertEquals(1L, usuarioEncontrado.getId());

        verify(usuarioRepository).findById(1L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoBuscarUsuarioPorIdInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.buscarPorId(1L));

        verify(usuarioRepository).findById(1L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveRemoverUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> usuarioService.remover(1L));

        verify(usuarioRepository).deleteById(1L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveLancarEntidadeNaoEncontradaExceptionAoRemoverUsuarioInexistente() {
        doThrow(EmptyResultDataAccessException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.remover(1L));

        verify(usuarioRepository).deleteById(1L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveLancarEntidadeEmUsoExceptionAoRemoverUsuarioEmUso() {
        doThrow(DataIntegrityViolationException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(EntidadeEmUsoException.class, () -> usuarioService.remover(1L));

        verify(usuarioRepository).deleteById(1L);
        verifyNoMoreInteractions(usuarioRepository);
    }

    @Test
    void deveAdicionarUsuarioComPermissoesAdmin() {
        Permissao permissaoAdmin = Permissao.builder().id(1L).nome("ROLE_ADMIN").build();
        Permissao permissaoCliente = Permissao.builder().id(2L).nome("ROLE_CLIENTE").build();
        usuario.setPerfil(Perfil.ADMIN);

        when(permissaoRepository.findById(1L)).thenReturn(Optional.of(permissaoAdmin));
        when(permissaoRepository.findById(2L)).thenReturn(Optional.of(permissaoCliente));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = usuarioService.adicionar(usuario);

        assertNotNull(usuarioSalvo);
        assertTrue(usuarioSalvo.getPermissoes().contains(permissaoAdmin));
        assertTrue(usuarioSalvo.getPermissoes().contains(permissaoCliente));

        verify(permissaoRepository).findById(1L);
        verify(permissaoRepository).findById(2L);
        verify(usuarioRepository).save(usuario);
        verifyNoMoreInteractions(permissaoRepository, usuarioRepository);
    }

    @Test
    void deveAdicionarUsuarioComPermissoesCliente() {
        Permissao permissaoAdmin = Permissao.builder().id(1L).nome("ROLE_ADMIN").build();
        Permissao permissaoCliente = Permissao.builder().id(2L).nome("ROLE_CLIENTE").build();
        usuario.setPerfil(Perfil.CLIENTE);

        when(permissaoRepository.findById(1L)).thenReturn(Optional.of(permissaoAdmin));
        when(permissaoRepository.findById(2L)).thenReturn(Optional.of(permissaoCliente));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioSalvo = usuarioService.adicionar(usuario);

        assertNotNull(usuarioSalvo);
        assertFalse(usuarioSalvo.getPermissoes().contains(permissaoAdmin));
        assertTrue(usuarioSalvo.getPermissoes().contains(permissaoCliente));

        verify(permissaoRepository).findById(1L);
        verify(permissaoRepository).findById(2L);
        verify(usuarioRepository).save(usuario);
        verifyNoMoreInteractions(permissaoRepository, usuarioRepository);
    }
}
