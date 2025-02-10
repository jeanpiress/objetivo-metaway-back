package com.jeanpires.objetivobackmetaway.domain.repositories;

import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
