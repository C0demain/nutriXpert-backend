package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.Meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.dto.Meal.MealInfoDTO;
import br.com.codemain.nutrixpertai.dto.Meal.MealResponseDTO;
import br.com.codemain.nutrixpertai.enums.MealType;
import br.com.codemain.nutrixpertai.service.impl.MealServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/meals")
@Tag(name = "Refeições", description = "Endpoints para gerenciamento de refeições")
@SecurityRequirement(name = "Bearer Authentication")
public class MealController {

    @Autowired
    private MealServiceImpl mealService;

    @Operation(summary = "Criar nova refeição", description = "Cria uma nova refeição para o usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Refeição criada com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<?> createMeal(
            @Parameter(description = "Dados da refeição a ser criada", required = true)
            @Valid @RequestBody CreateMealDTO mealDTO,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam UUID userId) {
        try {
            MealResponseDTO created = mealService.createMeal(mealDTO, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Listar todas as refeições", description = "Retorna todas as refeições do usuário ordenadas por data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de refeições retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping
    public ResponseEntity<?> getAllMeals(
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam UUID userId) {
        try {
            List<MealResponseDTO> meals = mealService.getMealsByUserId(userId);
            return ResponseEntity.ok(meals);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Buscar refeição por ID", description = "Retorna os detalhes de uma refeição específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refeição encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado à refeição",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getMealById(
            @Parameter(description = "ID da refeição", required = true)
            @PathVariable Long id) {
        try {
            MealResponseDTO meal = mealService.getMealById(id);
            return ResponseEntity.ok(meal);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Refeição não encontrada"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(buildErrorResponse("Acesso negado"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Buscar refeições por período", description = "Retorna refeições dentro de um intervalo de datas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refeições encontradas com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MealResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetros de data inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/date-range")
    public ResponseEntity<?> getMealsByDateRange(
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam UUID userId,
            @Parameter(description = "Data inicial (formato: yyyy-MM-dd)", required = true)
            @RequestParam LocalDate startDate,
            @Parameter(description = "Data final (formato: yyyy-MM-dd)", required = true)
            @RequestParam LocalDate endDate) {
        try {
            List<MealResponseDTO> meals = mealService.getMealsByUserAndDateRange(userId, startDate, endDate);
            return ResponseEntity.ok(meals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Buscar refeições por tipo", description = "Retorna refeições filtradas por tipo (café da manhã, almoço, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refeições encontradas com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Tipo de refeição inválido",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/type")
    public ResponseEntity<?> getMealsByType(
            @Parameter(description = "Tipo da refeição", required = true)
            @RequestParam MealType type,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam UUID userId) {
        try {
            List<MealResponseDTO> meals = mealService.getMealsByUserAndType(userId, type);
            return ResponseEntity.ok(meals);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor: " + e));
        }
    }

    @Operation(summary = "Buscar informações de uma refeição por ID", description = "Retorna as informações de uma refeição específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refeição encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado à refeição",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/info/{id}")
    public ResponseEntity<?> getMealInfo(
            @Parameter(description = "ID da refeição", required = true)
            @PathVariable Long id) {
        try {
            MealInfoDTO meal = mealService.getMealInfo(id);
            return ResponseEntity.ok(meal);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Refeição não encontrada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Atualizar refeição", description = "Atualiza os dados de uma refeição existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refeição atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = MealResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado à refeição",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMeal(
            @Parameter(description = "ID da refeição", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados da refeição", required = true)
            @Valid @RequestBody CreateMealDTO mealDTO) {
        try {
            MealResponseDTO updated = mealService.updateMeal(id, mealDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Refeição não encontrada"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(buildErrorResponse("Acesso negado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(e.getMessage()));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    @Operation(summary = "Deletar refeição", description = "Remove uma refeição do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Refeição deletada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado à refeição",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMeal(
            @Parameter(description = "ID da refeição", required = true)
            @PathVariable Long id) {
        try {
            mealService.deleteMeal(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Refeição não encontrada"));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(buildErrorResponse("Acesso negado"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao acessar dados"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro interno do servidor"));
        }
    }

    private Map<String, Object> buildErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", message);
        return errorResponse;
    }
}