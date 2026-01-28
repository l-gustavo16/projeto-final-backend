package com.primeiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primeiraapi.model.Medico;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
    boolean existsByCrm(String crm);
}
