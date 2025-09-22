package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.Auth.AuthenticationDTO;
import br.com.codemain.nutrixpertai.dto.Auth.RegisterDTO;
import br.com.codemain.nutrixpertai.service.IAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO body) {
        try {
            var loginResponse = authenticationService.login(body.email(), body.password());
            return ResponseEntity.ok(loginResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO body) {
        try {
            authenticationService.register(body);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("message", "Usuário cadastrado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
