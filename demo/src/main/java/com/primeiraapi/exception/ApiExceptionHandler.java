package com.primeiraapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Classe responsável por tratar exceções lançadas pela aplicação
 * e padronizar as respostas enviadas ao cliente.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Trata erros do tipo ResponseStatusException
     * Exemplo: Pessoa não encontrada (404)
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {

        // Retorna apenas a mensagem configurada na exceção
        // sem JSON grande e sem stack trace
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }

    /**
     * Trata erros de argumentos inválidos
     * Exemplo: CPF duplicado
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
