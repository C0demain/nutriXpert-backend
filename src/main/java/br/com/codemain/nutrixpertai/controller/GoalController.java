package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.GoalResponseDTO;
import br.com.codemain.nutrixpertai.dto.Goal.UpdateGoalDTO;
import br.com.codemain.nutrixpertai.service.impl.GoalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
@Tag(name = "Objetivos", description = "Operações relacionadas aos objetivos nutricionais dos usuários")
@SecurityRequirement(name = "Bearer Authentication")
public class GoalController {

    @Autowired
    private GoalServiceImpl goalService;

    @PostMapping
    @Operation(
            summary = "Criar novo objetivo nutricional",
            description = "Cria um novo objetivo nutricional para o usuário com base nas informações fornecidas"
    )
    public ResponseEntity<GoalResponseDTO> createGoal(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação do objetivo nutricional",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateGoalDTO.class))
            )
            @RequestBody CreateGoalDTO dto
    ) {
        try {
            GoalResponseDTO goal = goalService.createGoal(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(goal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Buscar objetivos por usuário",
            description = "Retorna todos os objetivos nutricionais de um usuário específico"
    )
    public ResponseEntity<List<GoalResponseDTO>> getGoalsByUser(
            @Parameter(description = "ID único do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId
    ) {
        List<GoalResponseDTO> goals = goalService.getGoalsByUser(userId);
        return ResponseEntity.ok(goals);
    }

    @GetMapping("/{goalId}")
    @Operation(
            summary = "Buscar objetivo por ID",
            description = "Retorna um objetivo nutricional específico pelo seu ID"
    )
    public ResponseEntity<GoalResponseDTO> getGoalById(
            @Parameter(description = "ID único do objetivo", required = true, example = "1")
            @PathVariable Long goalId
    ) {
        try {
            GoalResponseDTO goal = goalService.getGoalById(goalId);
            return ResponseEntity.ok(goal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{goalId}")
    @Operation(
            summary = "Atualizar objetivo existente",
            description = "Atualiza parcial ou totalmente um objetivo nutricional existente"
    )
    public ResponseEntity<GoalResponseDTO> updateGoal(
            @Parameter(description = "ID único do objetivo", required = true, example = "1")
            @PathVariable Long goalId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para atualização do objetivo nutricional (campos opcionais)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateGoalDTO.class))
            )
            @RequestBody UpdateGoalDTO dto
    ) {
        try {
            GoalResponseDTO updatedGoal = goalService.updateGoal(goalId, dto);
            return ResponseEntity.ok(updatedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{goalId}")
    @Operation(
            summary = "Deletar objetivo",
            description = "Remove permanentemente um objetivo nutricional do sistema"
    )
    public ResponseEntity<Void> deleteGoal(
            @Parameter(description = "ID único do objetivo", required = true, example = "1")
            @PathVariable Long goalId
    ) {
        try {
            goalService.deleteGoal(goalId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/formatted/{goalId}")
    @Operation(
            summary = "Buscar objetivo formatado",
            description = "Retorna as informações do objetivo nutricional formatadas em uma string legível para envio ao agente de IA"
    )
    public ResponseEntity<String> getGoalFormatted(
            @Parameter(description = "ID único do objetivo", required = true, example = "1")
            @PathVariable Long goalId
    ) {
        try {
            String formattedGoal = goalService.getGoalFormatted(goalId);
            return ResponseEntity.ok(formattedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/formatted/user/{userId}")
    @Operation(
            summary = "Buscar todos objetivos do usuário formatados",
            description = "Retorna todos os objetivos nutricionais do usuário formatados em uma string para envio ao agente de IA"
    )
    public ResponseEntity<String> getUserGoalsFormatted(
            @Parameter(description = "ID único do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID userId
    ) {
        String formattedGoals = goalService.getUserGoalsFormatted(userId);
        return ResponseEntity.ok(formattedGoals);
    }
}