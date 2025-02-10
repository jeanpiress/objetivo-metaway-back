package com.jeanpires.objetivobackmetaway.domain.service;

import com.jeanpires.objetivobackmetaway.core.security.MetaWaySecurity;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeEmUsoException;
import com.jeanpires.objetivobackmetaway.domain.exceptions.EntidadeNaoEncontradaException;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import com.jeanpires.objetivobackmetaway.domain.repositories.PetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public class PetService {

    @Autowired
    private PetsRepository repository;

    @Autowired
    private MetaWaySecurity security;

    public Pet buscarPorId(Long id){
        Pet pet = repository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("Pet não encontrado"));

        if(!security.temAutorizacao("ADMIN") && !security.getUsuarioId().equals(pet.getCliente().getId())){
            throw new AccessDeniedException("Usuario não corresponde ao cliente tutor");
        }

        return pet;
    }

    public void remover(Long id) {
        try {
            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Pet não encontrado");
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Pet não pode ser excluido");
        }
    }

}
