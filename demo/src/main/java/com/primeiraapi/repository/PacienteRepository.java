package com.primeiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primeiraapi.model.Paciente;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}
