package br.com.codemain.nutrixpertai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codemain.nutrixpertai.dto.User.UserPhysicalDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;
import br.com.codemain.nutrixpertai.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Tag(name = "Usuários")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private IUserService userService;

    @PatchMapping(value = "physical/{id}")
    @Operation(summary = "Atualiza peso e altura do usuário")
    public ResponseEntity<UserResponseDTO> updateAnamnese(
            @PathVariable("id") UUID id,
            @RequestBody UserPhysicalDTO userPhysicalDTO) {
        UserResponseDTO updated = userService.updatePhysical(id, userPhysicalDTO);

        return ResponseEntity.ok().body(updated);
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable("id") UUID id) {
        UserResponseDTO user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Atualiza um usuário")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable("id") UUID id,
            @RequestBody UserUpdateDTO userDTO) {
        UserResponseDTO updated = userService.update(id, userDTO);

        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta usuário")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
