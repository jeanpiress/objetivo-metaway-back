package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import com.jeanpires.objetivobackmetaway.domain.repositories.AtendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository repository;

    public Atendimento buscarPorId(Long id){
        return repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Atendimento não encontrado"));
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Atendimento não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Atendimento não pode ser excluido");
        }
    }

    public Atendimento adicionar(Atendimento atendimento) {
        atendimento.setData(LocalDateTime.now());
        return repository.save(atendimento);
    }
}
