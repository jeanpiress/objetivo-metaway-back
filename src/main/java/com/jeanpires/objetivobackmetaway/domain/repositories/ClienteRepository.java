package com.jeanpires.objetivobackmetaway.domain.repositories;

import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT(:nome, '%'))")
    List<Cliente> findByNome(String nome);

    Optional<Cliente> findByCpf(String cpf);
}
