package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import com.jeanpires.objetivobackmetaway.domain.repositories.RacaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RacaService {

    @Autowired
    private RacaRepository repository;

    public Raca buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Raca não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Raca não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Raca não pode ser excluido");
        }
    }
}
