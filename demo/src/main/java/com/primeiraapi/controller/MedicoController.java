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

import com.primeiraapi.model.Medico;
import com.primeiraapi.service.MedicoService;

/**
 * Controller responsável por expor os endpoints REST da entidade Médico.
 * Aqui tratamos as requisições HTTP (GET, POST, PUT, DELETE)
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    /**
     * BUSCAR TODOS OS MÉDICOS
     */
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<Medico> medicos = service.findAll();
        if (medicos.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum médico cadastrado"));
        }
        return ResponseEntity.ok(medicos);
    }

    /**
     * BUSCAR MÉDICO POR ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscarPorId(@PathVariable Long id) {
        Medico medico = service.findOrThrow(id);
        return ResponseEntity.ok(medico);
    }

    /**
     * CADASTRAR NOVO MÉDICO
     */
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Medico medico) {
        try {
            Medico novoMedico = service.create(medico);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(novoMedico.getId())
                    .toUri();
            return ResponseEntity
                    .created(location)
                    .body(Map.of(
                            "mensagem", "Médico cadastrado com sucesso",
                            "medico", novoMedico
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * ATUALIZAR MÉDICO
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Medico medico) {
        try {
            Medico medicoAtualizado = service.update(id, medico);
            return ResponseEntity.ok(Map.of(
                    "mensagem", "Médico atualizado com sucesso",
                    "medico", medicoAtualizado
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    /**
     * DELETAR MÉDICO
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(Map.of("mensagem", "Médico deletado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}
