package com.primeiraapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.primeiraapi.model.Medico;
import com.primeiraapi.repository.MedicoRepository;

/**
 * Camada de serviço para Médico:
 * - Contém as regras de negócio
 * - Centraliza validações
 * - Evita lógica pesada no Controller
 */
@Service
@SuppressWarnings("null")
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria um novo médico
     * - Valida CRM duplicado
     */
    public Medico create(Medico medico) {
        if (medico.getCrm() != null && repository.existsByCrm(medico.getCrm())) {
            throw new IllegalArgumentException("CRM já cadastrado");
        }
        return repository.save(medico);
    }

    /**
     * Retorna todos os médicos cadastrados
     */
    public List<Medico> findAll() {
        return repository.findAll();
    }

    /**
     * Busca por ID retornando Optional
     */
    public Optional<Medico> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Busca por ID ou lança erro 404
     */
    public Medico findOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Médico não encontrado"
                )
            );
    }

    /**
     * Atualiza médico existente
     * - Atualiza SOMENTE campos que vieram no JSON
     */
    @SuppressWarnings("null")
    public Medico update(Long id, Medico dados) {
        Medico existente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));

        if (dados.getNome() != null)
            existente.setNome(dados.getNome());

        if (dados.getEspecialidade() != null)
            existente.setEspecialidade(dados.getEspecialidade());

        if (dados.getTelefone() != null)
            existente.setTelefone(dados.getTelefone());

        if (dados.getEmail() != null)
            existente.setEmail(dados.getEmail());

        return repository.save(existente);
    }

    /**
     * Remove médico pelo ID
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Médico não encontrado");
        }
        repository.deleteById(id);
    }
}
