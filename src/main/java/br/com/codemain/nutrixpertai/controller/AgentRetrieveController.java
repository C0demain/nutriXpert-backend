package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.User.UserPhysicalDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.service.IAnamneseService;
import br.com.codemain.nutrixpertai.service.IUserService;
import br.com.codemain.nutrixpertai.service.impl.AgentServiceImpl;
import br.com.codemain.nutrixpertai.service.impl.AnamneseServiceImpl;
import br.com.codemain.nutrixpertai.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Tag(name = "Agente IA", description = "Endpoints para o agente de IA interagir com o backend")
@RestController
@CrossOrigin
@RequestMapping("/api/interact/agent")
//@Hidden
@SecurityRequirement(name = "Bearer Authentication")
public class AgentRetrieveController {

    private final AgentServiceImpl agentService;
    private final IUserService userService;
    private final IAnamneseService anamneseService;


    public AgentRetrieveController(AgentServiceImpl agentServiceImpl, UserServiceImpl userService, AnamneseServiceImpl anamneseService) {
        this.agentService = agentServiceImpl;
        this.userService = userService;
        this.anamneseService = anamneseService;
    }

    @GetMapping(value = "/getUserInfo/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable("id") UUID id) {
        UserResponseDTO user = userService.getById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping(value = "physical/{id}")
    public ResponseEntity<UserResponseDTO> updateUserWeight(
            @PathVariable("id") UUID id,
            @RequestBody UserPhysicalDTO userPhysicalDTO) {
        UserResponseDTO updated = userService.updatePhysical(id, userPhysicalDTO);

        return ResponseEntity.ok().body(updated);
    }

    @PostMapping("/{userId}/anamnese")
    public ResponseEntity<UserResponseDTO> create(
            @PathVariable UUID userId,
            @RequestBody AnamneseRequestDTO requestDTO) {
        UserResponseDTO responseDTO = anamneseService.createAgent(userId, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping("/{userId}/anamnese")
    public ResponseEntity<UserResponseDTO> patch(
            @PathVariable UUID userId,
            @RequestBody AnamneseRequestDTO patchRequest) {
        UserResponseDTO responseDTO = anamneseService.patchAgent(userId, patchRequest);
        return ResponseEntity.ok(responseDTO);
    }
}
