package com.primeiraapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.primeiraapi.model.Diagnostico;
import com.primeiraapi.service.DiagnosticoService;

/**
 * Controller responsável por expor os endpoints REST da entidade Diagnóstico.
 * Aqui tratamos as requisições HTTP (GET, POST, PUT, DELETE)
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService service;

    public DiagnosticoController(DiagnosticoService service) {
        this.service = service;
    }

    /**
     * BUSCAR TODOS OS DIAGNÓSTICOS
     */
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Diagnostico> diagnosticos = service.findAll();
        if (diagnosticos.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum diagnóstico cadastrado"));
        }
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * BUSCAR DIAGNÓSTICO POR ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Diagnostico> buscarPorId(@PathVariable Long id) {
        Diagnostico diagnostico = service.findOrThrow(id);
        return ResponseEntity.ok(diagnostico);
    }

    /**
     * BUSCAR DIAGNÓSTICOS DE UM PACIENTE
     */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<?> buscarPorPaciente(@PathVariable Long pacienteId) {
        List<Diagnostico> diagnosticos = service.findByPacienteId(pacienteId);
        if (diagnosticos.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum diagnóstico para este paciente"));
        }
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * BUSCAR DIAGNÓSTICOS DE UM MÉDICO
     */
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<?> buscarPorMedico(@PathVariable Long medicoId) {
        List<Diagnostico> diagnosticos = service.findByMedicoId(medicoId);
        if (diagnosticos.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum diagnóstico para este médico"));
        }
        return ResponseEntity.ok(diagnosticos);
    }

    /**
     * CADASTRAR NOVO DIAGNÓSTICO
     */
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Diagnostico diagnostico) {
        try {
            Diagnostico novoDiagnostico = service.create(diagnostico);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(novoDiagnostico.getId())
                    .toUri();
            return ResponseEntity
                    .created(location)
                    .body(Map.of(
                            "mensagem", "Diagnóstico cadastrado com sucesso",
                            "diagnostico", novoDiagnostico
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * ATUALIZAR DIAGNÓSTICO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Diagnostico diagnostico) {
        try {
            Diagnostico diagnosticoAtualizado = service.update(id, diagnostico);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Diagnóstico atualizado com sucesso",
                    "diagnostico", diagnosticoAtualizado
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * DELETAR DIAGNÓSTICO
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(Map.of("mensagem", "Diagnóstico deletado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
