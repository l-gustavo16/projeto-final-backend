package com.primeiraapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primeiraapi.model.Diagnostico;
import com.primeiraapi.repository.DiagnosticoRepository;

/**
 * Camada de serviço para Diagnóstico:
 * - Contém as regras de negócio
 * - Centraliza validações
 * - Evita lógica pesada no Controller
 */
@Service
@SuppressWarnings("null")
public class DiagnosticoService {

    private final DiagnosticoRepository repository;

    public DiagnosticoService(DiagnosticoRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo diagnóstico
     */
    public Diagnostico create(Diagnostico diagnostico) {
        if (diagnostico.getPaciente() == null || diagnostico.getMedico() == null) {
            throw new IllegalArgumentException("Paciente e Médico são obrigatórios");
        }
        return repository.save(diagnostico);
    }

    /**
     * Retorna todos os diagnósticos cadastrados
     */
    public List<Diagnostico> findAll() {
        return repository.findAll();
    }

    /**
     * Busca por ID retornando Optional
     */
    public Optional<Diagnostico> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Busca por ID ou lança erro 404
     */
    public Diagnostico findOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Diagnóstico não encontrado"
                )
            );
    }

    /**
     * Retorna diagnósticos de um paciente específico
     */
    public List<Diagnostico> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    /**
     * Retorna diagnósticos de um médico específico
     */
    public List<Diagnostico> findByMedicoId(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    /**
     * Atualiza diagnóstico existente
     */
    @SuppressWarnings("null")
    public Diagnostico update(Long id, Diagnostico dados) {
        Diagnostico existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Diagnóstico não encontrado"));

        if (dados.getDescricao() != null)
            existente.setDescricao(dados.getDescricao());

        if (dados.getRecomendacoes() != null)
            existente.setRecomendacoes(dados.getRecomendacoes());

        return repository.save(existente);
    }

    /**
     * Remove diagnóstico pelo ID
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Diagnóstico não encontrado");
        }
        repository.deleteById(id);
    }
}
