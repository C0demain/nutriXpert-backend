package br.com.codemain.nutrixpertai.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Trata ResponseStatusException (erros manuais do service/controller)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(Map.of(
                        "status", ex.getStatusCode().value(),
                        "error", ex.getStatusCode(),
                        "message", ex.getReason()
                ));
    }

    // ✅ Trata erros de validação com @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Validation Failed",
                "messages", errors
        ));
    }

    // ✅ Trata payload mal formatado ou atributos errados
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidJson(HttpMessageNotReadableException ex) {
        String message = "Payload inválido ou mal formatado";
        if (ex.getCause() instanceof InvalidFormatException invalidFormat) {
            message = "Campo '" + invalidFormat.getPath().get(0).getFieldName()
                    + "' recebeu valor inválido: " + invalidFormat.getValue();
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "error", "Bad Request",
                        "message", message
                ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", HttpStatus.NOT_FOUND.value(),
                        "error", "Not Found",
                        "message", ex.getMessage()
                ));
    }
}
