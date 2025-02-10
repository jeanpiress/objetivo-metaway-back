package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.core.security.MetaWaySecurity;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import com.jeanpires.objetivobackmetaway.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private MetaWaySecurity security;

    public Cliente buscarPorId(Long id){
        if(!security.temAutorizacao("ADMIN") && !security.getUsuarioId().equals(id)){
            throw new AccessDeniedException("Usuario não corresponde ao cliente");
        }
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Cliente não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cliente não pode ser excluido");
        }
    }

    public Cliente adicionar(Cliente cliente) {
        if(!security.temAutorizacao("ADMIN")){
            cliente.setCpf(security.getUsuarioCpf());
        }
        cliente.setDataCadastro(LocalDate.now());
        return repository.save(cliente);
    }
}
