package br.com.codemain.nutrixpertai.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codemain.nutrixpertai.dto.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@Tag(name = "Usuários")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    @Operation(summary = "Cria usuário")
    public ResponseEntity<User> create(@RequestBody UserCreateDTO userDTO) {
        User created = userService.create(userDTO);

        return ResponseEntity
                .created(URI.create("/user/" + created.getId()))
                .body(created);
    }

    @GetMapping
    @Operation(summary = "Busca todos os usuários")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<User> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Atualiza um usuário")
    public ResponseEntity<User> update(
            @PathVariable("id") UUID id,
            @RequestBody UserUpdateDTO userDTO) {
        User updated = userService.update(id, userDTO);

        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deleta usuário")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
