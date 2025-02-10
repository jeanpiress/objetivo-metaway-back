package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import com.jeanpires.objetivobackmetaway.domain.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository repository;

    public Contato buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Contato não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Contato não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Contato não pode ser excluido");
        }
    }

}
