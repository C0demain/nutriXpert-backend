package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import br.com.codemain.nutrixpertai.service.impl.AgentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Agente IA", description = "Endpoints para interagir com o agente de IA")
@RestController
@CrossOrigin
@RequestMapping("/api/agent")
@SecurityRequirement(name = "Bearer Authentication")
public class AgentController {


    private final AgentServiceImpl agentService;

    public AgentController(AgentServiceImpl agentServiceImpl) {
        this.agentService = agentServiceImpl;
    }

    @Operation(
            summary = "Executar o agente de IA",
            description = "Envia uma pergunta do usuário para o agente de IA processar e retorna a resposta."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resposta do agente processada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RunAgentResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (ex: campos faltando)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Serviço do agente (Python) indisponível ou demorou demais",
                    content = @Content
            )
    })
    @PostMapping("/run")
    public ResponseEntity<RunAgentResponseDto> runAgent(@RequestBody RunAgentRequestDto requestDto) {
        RunAgentResponseDto response = agentService.executeAgent(requestDto);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Criar um novo feedback",
            description = "Cria um novo feedback do usuário para uma resposta específica do agente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Feedback registrado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FeedbackResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Erro de validação (ex: campos obrigatórios faltando)",
                    content = @Content
            )
    })
    @PostMapping("/feedback")
    public ResponseEntity<FeedbackResponseDto> feedback(@RequestBody @Valid FeedbackRequestDto feedbackRequestDto) {
        FeedbackResponseDto response = agentService.executeFeedback(feedbackRequestDto);
        return ResponseEntity.ok(response);
    }
}
