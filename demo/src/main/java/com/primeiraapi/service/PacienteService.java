package com.primeiraapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primeiraapi.model.Paciente;
import com.primeiraapi.repository.PacienteRepository;

/**
 * Camada de serviço para Paciente:
 * - Contém as regras de negócio
 * - Centraliza validações
 * - Evita lógica pesada no Controller
 */
@Service
@SuppressWarnings("null")
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo paciente
     * - Valida CPF duplicado
     */
    public Paciente create(Paciente paciente) {
        if (paciente.getCpf() != null && repository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        return repository.save(paciente);
    }

    /**
     * Retorna todos os pacientes cadastrados
     */
    public List<Paciente> findAll() {
        return repository.findAll();
    }

    /**
     * Busca por ID retornando Optional
     */
    public Optional<Paciente> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Busca por ID ou lança erro 404
     */
    public Paciente findOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Paciente não encontrado"
                )
            );
    }

    /**
     * Atualiza paciente existente
     * - Atualiza SOMENTE campos que vieram no JSON
     */
    @SuppressWarnings("null")
    public Paciente update(Long id, Paciente dados) {
        Paciente existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        if (dados.getNome() != null)
            existente.setNome(dados.getNome());

        if (dados.getDataNascimento() != null)
            existente.setDataNascimento(dados.getDataNascimento());

        if (dados.getTelefone() != null)
            existente.setTelefone(dados.getTelefone());

        if (dados.getEmail() != null)
            existente.setEmail(dados.getEmail());

        if (dados.getHistoricoMedico() != null)
            existente.setHistoricoMedico(dados.getHistoricoMedico());

        return repository.save(existente);
    }

    /**
     * Remove paciente pelo ID
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        repository.deleteById(id);
    }
}
