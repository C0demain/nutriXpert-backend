package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionInfoResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionListItemDto;
import br.com.codemain.nutrixpertai.service.impl.AgentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(
            summary = "Buscar mensagens da sessão",
            description = "Retorna todos os detalhes e o histórico de mensagens de uma sessão específica do usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sessão encontrada e mensagens retornadas",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SessionInfoResponseDto.class) // O DTO de resposta
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sessão ou usuário não encontrado",
                    content = @Content
            )
    })
    @GetMapping("/sessions/{userId}/{sessionId}")
    public ResponseEntity<SessionInfoResponseDto> getSessionMessages(
            @Parameter(description = "ID do usuário", required = true, example = "1")
            @PathVariable String userId,

            @Parameter(description = "ID da sessão", required = true, example = "1")
            @PathVariable String sessionId
    ) {
        SessionInfoResponseDto response = agentService.getSessionMessages(userId, sessionId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Listar sessões do usuário",
            description = "Retorna uma lista de todas as sessões de um usuário específico, com a primeira mensagem."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de sessões retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SessionListItemDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content
            )
    })
    @GetMapping("/{userId}/list")
    public ResponseEntity<List<SessionListItemDto>> listUserSessions(
            @Parameter(description = "ID do usuário", required = true, example = "1")
            @PathVariable String userId
    ) {
        List<SessionListItemDto> response = agentService.listUserSessions(userId);
        return ResponseEntity.ok(response);
    }
}
