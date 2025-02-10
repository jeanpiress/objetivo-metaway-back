package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import com.jeanpires.objetivobackmetaway.domain.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Endereco não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Endereco não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Endereco não pode ser excluido");
        }
    }
}
