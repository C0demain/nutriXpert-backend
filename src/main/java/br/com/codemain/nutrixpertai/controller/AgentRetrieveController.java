package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.User.UserPhysicalDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.service.IUserService;
import br.com.codemain.nutrixpertai.service.impl.AgentServiceImpl;
import br.com.codemain.nutrixpertai.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Agente IA", description = "Endpoints para o agente de IA interagir com o backend")
@RestController
@CrossOrigin
@RequestMapping("/api/interact/agent")
@Hidden
@SecurityRequirement(name = "Bearer Authentication")
public class AgentRetrieveController {

    private final AgentServiceImpl agentService;
    private final IUserService userService;

    public AgentRetrieveController(AgentServiceImpl agentServiceImpl, UserServiceImpl userService) {
        this.agentService = agentServiceImpl;
        this.userService = userService;
    }

    @GetMapping(value = "/getUserInfo/{id}")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable("id") UUID id) {
        UserResponseDTO user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "physical/{id}")
    @Operation(summary = "Atualiza peso e altura do usuário")
    public ResponseEntity<UserResponseDTO> updateUserWeight(
            @PathVariable("id") UUID id,
            @RequestBody UserPhysicalDTO userPhysicalDTO) {
        UserResponseDTO updated = userService.updatePhysical(id, userPhysicalDTO);

        return ResponseEntity.ok().body(updated);
    }
}
