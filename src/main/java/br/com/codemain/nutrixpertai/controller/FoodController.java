package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.food.CreateFoodDTO;
import br.com.codemain.nutrixpertai.dto.food.FoodResponseDTO;
import br.com.codemain.nutrixpertai.dto.food.UpdateFoodDTO;
import br.com.codemain.nutrixpertai.service.impl.FoodServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/foods")
@Tag(name = "Alimentos", description = "Endpoints para gerenciamento de alimentos das refeições")
@SecurityRequirement(name = "Bearer Authentication")
public class FoodController {

    private final FoodServiceImpl foodService;

    public FoodController(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    @Operation(summary = "Adicionar alimento à refeição", description = "Adiciona um novo alimento a uma refeição existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alimento adicionado com sucesso",
                    content = @Content(schema = @Schema(implementation = FoodResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado à refeição",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<?> createFood(
            @Parameter(description = "Dados do alimento a ser adicionado", required = true)
            @Valid @RequestBody CreateFoodDTO dto) {
        try {
            FoodResponseDTO created = foodService.createFood(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse(e.getMessage()));
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

    @Operation(summary = "Buscar alimento por ID", description = "Retorna os detalhes de um alimento específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alimento encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = FoodResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(
            @Parameter(description = "ID do alimento", required = true)
            @PathVariable Long id) {
        try {
            FoodResponseDTO food = foodService.getFoodById(id);
            return ResponseEntity.ok(food);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Alimento não encontrado"));
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

    @Operation(summary = "Listar alimentos de uma refeição", description = "Retorna todos os alimentos de uma refeição específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alimentos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = FoodResponseDTO.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Refeição não encontrada",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/meal/{mealId}")
    public ResponseEntity<?> getFoodsByMealId(
            @Parameter(description = "ID da refeição", required = true)
            @PathVariable Long mealId) {
        try {
            List<FoodResponseDTO> foods = foodService.getFoodsByMealId(mealId);
            return ResponseEntity.ok(foods);
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

    @Operation(summary = "Atualizar alimento", description = "Atualiza os dados de um alimento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = FoodResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(
            @Parameter(description = "ID do alimento", required = true)
            @PathVariable Long id,
            @Parameter(description = "Novos dados do alimento", required = true)
            @Valid @RequestBody UpdateFoodDTO dto) {
        try {
            FoodResponseDTO updated = foodService.updateFood(id, dto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Alimento não encontrado"));
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

    @Operation(summary = "Deletar alimento", description = "Remove um alimento de uma refeição")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alimento deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(
            @Parameter(description = "ID do alimento", required = true)
            @PathVariable Long id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Alimento não encontrado"));
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