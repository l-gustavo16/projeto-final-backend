package com.primeiraapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller de Health Check e informações da API
 */
@CrossOrigin("*")
@RestController
public class HealthController {

    /**
     * Endpoint raiz - retorna informações sobre a API
     */
    @GetMapping("/")
    public ResponseEntity<?> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Sistema Hospitalar - API");
        response.put("status", "ativo");
        response.put("versao", "1.0.0");
        response.put("endpoints", Map.of(
                "medicos", "/api/medicos",
                "pacientes", "/api/pacientes",
                "diagnosticos", "/api/diagnosticos"
        ));
        return ResponseEntity.ok(response);
    }

    /**
     * Health check simples
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "OK"));
    }
}
