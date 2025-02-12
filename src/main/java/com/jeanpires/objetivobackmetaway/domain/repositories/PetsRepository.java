package com.jeanpires.objetivobackmetaway.domain.repositories;

import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p FROM Pet p WHERE LOWER(p.nome) LIKE LOWER(CONCAT(:nome, '%'))")
    List<Pet> findByNome(String nome);

    @Query("SELECT p FROM Pet p WHERE p.cliente.id = :id")
    List<Pet> buscarPorCliente(Long id);
}
