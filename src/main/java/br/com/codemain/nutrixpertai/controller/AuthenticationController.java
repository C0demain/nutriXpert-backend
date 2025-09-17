package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.AuthenticationDTO;
import br.com.codemain.nutrixpertai.dto.LoginResponseDTO;
import br.com.codemain.nutrixpertai.dto.RegisterDTO;
import br.com.codemain.nutrixpertai.service.AuthenticationService;
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
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO body) {
        var loginResponse = authenticationService.login(body.email(), body.password());
        return ResponseEntity.ok(loginResponse);
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
