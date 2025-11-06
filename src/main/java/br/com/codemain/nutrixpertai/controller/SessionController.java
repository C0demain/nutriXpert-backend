package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.Session.MessageDTO;
import br.com.codemain.nutrixpertai.dto.Session.SessionStateDTO;
import br.com.codemain.nutrixpertai.dto.Session.SessionWithMessagesDTO;
import br.com.codemain.nutrixpertai.entity.Session;
import br.com.codemain.nutrixpertai.repository.SessionRepository;
import br.com.codemain.nutrixpertai.service.impl.SessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Session", description = "Gerenciamento de sessões e mensagens")
@SecurityRequirement(name = "Bearer Authentication")
public class SessionController {

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Operation(
            summary = "Buscar mensagens de uma sessão",
            description = "Retorna todas as mensagens de uma sessão específica extraídas do campo JSON 'state'"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mensagens encontradas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getMessages(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam String userId,
            @Parameter(description = "ID da sessão", required = true, example = "1")
            @RequestParam String sessionId) {

        List<MessageDTO> messages = sessionService.getMessagesFromSession(appName, userId, sessionId);
        return ResponseEntity.ok(messages);
    }

    @Operation(
            summary = "Buscar estado completo da sessão",
            description = "Retorna o estado completo de uma sessão incluindo 'answer' e 'messages'"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado da sessão encontrado",
                    content = @Content(schema = @Schema(implementation = SessionStateDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada")
    })
    @GetMapping("/state")
    public ResponseEntity<SessionStateDTO> getSessionState(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam String userId,
            @Parameter(description = "ID da sessão", required = true)
            @RequestParam String sessionId) {

        SessionStateDTO state = sessionService.getSessionState(appName, userId, sessionId);
        if (state != null) {
            return ResponseEntity.ok(state);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Buscar todas as sessões de um usuário",
            description = "Retorna todas as sessões de um usuário específico com suas mensagens"
    )
    @GetMapping("/user/all")
    public ResponseEntity<List<SessionWithMessagesDTO>> getAllUserSessions(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam String userId) {

        List<SessionWithMessagesDTO> sessions = sessionService.getAllUserSessions(appName, userId);
        return ResponseEntity.ok(sessions);
    }

    @Operation(
            summary = "Buscar última mensagem",
            description = "Retorna a última mensagem de uma sessão específica"
    )
    @GetMapping("/last-message")
    public ResponseEntity<MessageDTO> getLastMessage(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam String userId,
            @Parameter(description = "ID da sessão", required = true)
            @RequestParam String sessionId) {

        MessageDTO message = sessionService.getLastMessage(appName, userId, sessionId);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Listar sessões do usuário",
            description = "Retorna todas as sessões de um usuário (sem as mensagens detalhadas)"
    )
    @GetMapping("/list")
    public ResponseEntity<List<Session>> getUserSessions(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName,
            @Parameter(description = "ID do usuário", required = true)
            @RequestParam String userId) {

        List<Session> sessions = sessionRepository.findByAppNameAndUserId(appName, userId);
        return ResponseEntity.ok(sessions);
    }

    @Operation(
            summary = "Listar sessões da aplicação",
            description = "Retorna todas as sessões de uma aplicação específica"
    )
    @GetMapping("/app")
    public ResponseEntity<List<Session>> getAppSessions(
            @Parameter(description = "Nome da aplicação", required = true, example = "nutriXpert")
            @RequestParam String appName) {

        List<Session> sessions = sessionRepository.findByAppName(appName);
        return ResponseEntity.ok(sessions);
    }
}