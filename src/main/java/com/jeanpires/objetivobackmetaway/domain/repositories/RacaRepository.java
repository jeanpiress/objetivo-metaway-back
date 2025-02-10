package com.jeanpires.objetivobackmetaway.domain.repositories;

import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RacaRepository extends JpaRepository<Raca, Long> {
}
