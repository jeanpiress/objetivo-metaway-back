package com.jeanpires.objetivobackmetaway.domain.repositories;

import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
}
