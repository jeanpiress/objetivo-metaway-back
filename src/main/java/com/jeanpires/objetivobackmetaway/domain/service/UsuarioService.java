package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.enuns.Perfil;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Permissao;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import com.jeanpires.objetivobackmetaway.domain.repositories.PermissaoRepository;
import com.jeanpires.objetivobackmetaway.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Usuario buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Usuario não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Usuario não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Usuario não pode ser excluido");
        }
    }

    public Usuario adicionar(Usuario usuario) {
        Permissao permissaoAdmin = permissaoRepository.findById(1L).get();
        Permissao permissaoCliente = permissaoRepository.findById(2L).get();
        Set<Permissao> permissoes = new HashSet<>();

        if(usuario.getPerfil().equals(Perfil.ADMIN)){
           permissoes.add(permissaoAdmin);
           permissoes.add(permissaoCliente);
        }else {permissoes.add(permissaoCliente);}
        usuario.setPermissoes(permissoes);
        return repository.save(usuario);
    }
}
