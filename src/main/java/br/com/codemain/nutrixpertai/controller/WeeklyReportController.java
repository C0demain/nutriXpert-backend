package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.Report.WeeklyReportDTO;
import br.com.codemain.nutrixpertai.service.impl.WeeklyReportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports/weekly")
@Tag(name = "Relatórios Semanais", description = "Endpoints para geração e consulta de relatórios semanais de progresso")
@SecurityRequirement(name = "Bearer Authentication")
public class WeeklyReportController {
    @Autowired
    private WeeklyReportServiceImpl weeklyReportService;

    @Operation(
            summary = "Obter relatório da semana atual",
            description = "Gera um relatório completo da semana atual (segunda a domingo) para o usuário especificado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = WeeklyReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentWeekReport(
            @Parameter(description = "ID do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam UUID userId) {
        try {
            WeeklyReportDTO report = weeklyReportService.getCurrentWeekReport(userId);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao gerar relatório: " + e.getMessage()));
        }
    }

    @Operation(
            summary = "Obter relatório de uma semana específica",
            description = "Gera um relatório completo para uma semana específica. " +
                    "A data fornecida será ajustada para a segunda-feira da semana correspondente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatório gerado com sucesso",
                    content = @Content(schema = @Schema(implementation = WeeklyReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Data inválida",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/specific")
    public ResponseEntity<?> getSpecificWeekReport(
            @Parameter(description = "ID do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam UUID userId,
            @Parameter(description = "Data de início da semana (formato: yyyy-MM-dd)", required = true, example = "2025-01-13")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {
        try {
            WeeklyReportDTO report = weeklyReportService.generateWeeklyReport(userId, weekStart);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao gerar relatório: " + e.getMessage()));
        }
    }

    @Operation(
            summary = "Obter relatórios mensais",
            description = "Gera relatórios de todas as semanas completas de um mês específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatórios gerados com sucesso",
                    content = @Content(schema = @Schema(implementation = WeeklyReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReports(
            @Parameter(description = "ID do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam UUID userId,
            @Parameter(description = "Ano", required = true, example = "2025")
            @RequestParam int year,
            @Parameter(description = "Mês (1-12)", required = true, example = "1")
            @RequestParam int month) {
        try {
            if (month < 1 || month > 12) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(buildErrorResponse("Mês deve estar entre 1 e 12"));
            }
            List<WeeklyReportDTO> reports = weeklyReportService.getMonthlyReports(userId, year, month);
            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao gerar relatórios: " + e.getMessage()));
        }
    }

    @Operation(
            summary = "Obter últimas N semanas",
            description = "Gera relatórios das últimas N semanas, incluindo a semana atual"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Relatórios gerados com sucesso",
                    content = @Content(schema = @Schema(implementation = WeeklyReportDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros inválidos",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/last")
    public ResponseEntity<?> getLastWeeksReports(
            @Parameter(description = "ID do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam UUID userId,
            @Parameter(description = "Número de semanas (máximo 12)", required = true, example = "4")
            @RequestParam int weeks) {
        try {
            if (weeks < 1 || weeks > 12) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(buildErrorResponse("Número de semanas deve estar entre 1 e 12"));
            }

            List<WeeklyReportDTO> reports = new java.util.ArrayList<>();
            LocalDate currentWeekStart = LocalDate.now().with(java.time.DayOfWeek.MONDAY);

            for (int i = 0; i < weeks; i++) {
                LocalDate weekStart = currentWeekStart.minusWeeks(i);
                reports.add(weeklyReportService.generateWeeklyReport(userId, weekStart));
            }

            return ResponseEntity.ok(reports);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao gerar relatórios: " + e.getMessage()));
        }
    }

    @Operation(
            summary = "Comparar duas semanas",
            description = "Gera um relatório comparativo entre duas semanas específicas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comparação gerada com sucesso",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datas inválidas",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = Map.class))
            )
    })
    @GetMapping("/compare")
    public ResponseEntity<?> compareWeeks(
            @Parameter(description = "ID do usuário", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
            @RequestParam UUID userId,
            @Parameter(description = "Data da primeira semana (formato: yyyy-MM-dd)", required = true, example = "2025-01-06")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate week1Start,
            @Parameter(description = "Data da segunda semana (formato: yyyy-MM-dd)", required = true, example = "2025-01-13")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate week2Start) {
        try {
            WeeklyReportDTO report1 = weeklyReportService.generateWeeklyReport(userId, week1Start);
            WeeklyReportDTO report2 = weeklyReportService.generateWeeklyReport(userId, week2Start);

            Map<String, Object> comparison = new HashMap<>();
            comparison.put("week1", report1);
            comparison.put("week2", report2);
            comparison.put("comparison", Map.of(
                    "caloriesDifference",
                    report2.nutrientSummary().totalCalories() - report1.nutrientSummary().totalCalories(),
                    "proteinDifference",
                    report2.nutrientSummary().totalProtein() - report1.nutrientSummary().totalProtein(),
                    "carbsDifference",
                    report2.nutrientSummary().totalCarbs() - report1.nutrientSummary().totalCarbs(),
                    "fatsDifference",
                    report2.nutrientSummary().totalFat() - report1.nutrientSummary().totalFat(),
                    "mealsDifference",
                    report2.nutrientSummary().totalMeals() - report1.nutrientSummary().totalMeals()
            ));

            return ResponseEntity.ok(comparison);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(buildErrorResponse("Usuário não encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrorResponse("Erro ao gerar comparação: " + e.getMessage()));
        }
    }

    private Map<String, Object> buildErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("message", message);
        return errorResponse;
    }
}