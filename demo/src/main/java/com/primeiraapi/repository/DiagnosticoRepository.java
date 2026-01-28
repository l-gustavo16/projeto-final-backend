package com.primeiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.primeiraapi.model.Diagnostico;
import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    List<Diagnostico> findByPacienteId(Long pacienteId);
    List<Diagnostico> findByMedicoId(Long medicoId);
}
