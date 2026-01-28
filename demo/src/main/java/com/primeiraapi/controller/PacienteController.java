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

import com.primeiraapi.model.Paciente;
import com.primeiraapi.service.PacienteService;

/**
 * Controller responsável por expor os endpoints REST da entidade Paciente.
 * Aqui tratamos as requisições HTTP (GET, POST, PUT, DELETE)
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

    /**
     * BUSCAR TODOS OS PACIENTES
     */
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Paciente> pacientes = service.findAll();
        if (pacientes.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum paciente cadastrado"));
        }
        return ResponseEntity.ok(pacientes);
    }

    /**
     * BUSCAR PACIENTE POR ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        Paciente paciente = service.findOrThrow(id);
        return ResponseEntity.ok(paciente);
    }

    /**
     * CADASTRAR NOVO PACIENTE
     */
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Paciente paciente) {
        try {
            Paciente novoPaciente = service.create(paciente);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(novoPaciente.getId())
                    .toUri();
            return ResponseEntity
                    .created(location)
                    .body(Map.of(
                            "mensagem", "Paciente cadastrado com sucesso",
                            "paciente", novoPaciente
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * ATUALIZAR PACIENTE
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            Paciente pacienteAtualizado = service.update(id, paciente);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Paciente atualizado com sucesso",
                    "paciente", pacienteAtualizado
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * DELETAR PACIENTE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(Map.of("mensagem", "Paciente deletado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
