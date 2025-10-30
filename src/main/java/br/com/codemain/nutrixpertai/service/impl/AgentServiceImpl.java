package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.client.AgentClient;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionInfoResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionListItemDto;
import br.com.codemain.nutrixpertai.infra.exception.AgentServiceException;
import br.com.codemain.nutrixpertai.service.IAgentService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AgentServiceImpl implements IAgentService {

    private static final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final AgentClient agentClient;

    public AgentServiceImpl(AgentClient agentClient) {
        this.agentClient = agentClient;
    }

    @Override
    public RunAgentResponseDto executeAgent(RunAgentRequestDto requestDto) {
        log.info("Iniciando chamada ao agente para usuário: {}", requestDto.user_id());

        try {
            RunAgentResponseDto response = agentClient.runAgent(requestDto);
            log.info("Agente respondeu com sucesso para sessão: {}", requestDto.session_id());

            return response;

        } catch (FeignException e) {
            log.error("Erro ao chamar /run-agent. Status: {}. Body: {}", e.status(), e.contentUTF8(), e);

            throw new AgentServiceException("Não foi possível processar sua pergunta. O agente está indisponível.", e);
        }
    }

    @Override
    public FeedbackResponseDto executeFeedback(FeedbackRequestDto requestDto) {
        log.info("Registrando feedback para mensagem: {}", requestDto.message_id());
        try {
            return agentClient.createFeedback(requestDto);
        } catch (FeignException e) {
            log.error("Erro ao registrar feedback /feedback. Status: {}. Body: {}", e.status(), e.contentUTF8(), e);
            throw new AgentServiceException("Não foi possível registrar seu feedback.", e);
        }
    }

    @Override
    public List<FeedbackResponseDto> getFeedbacksByConversation(String userId, String sessionId) {
        log.info("Buscando feedbacks para usuário: {} e sessão: {}", userId, sessionId);
        try {
            return agentClient.getFeedbacksByConversation(userId, sessionId);

        } catch (FeignException.NotFound e) { // Trata 404 de forma específica
            log.warn("Sessão não encontrada. Usuário: {}, Sessão: {}", userId, sessionId, e);
            // Você pode lançar uma exceção específica de "não encontrado"
            throw new AgentServiceException("Sessão ou usuário não encontrado.", e);

        } catch (FeignException e) {
            log.error("Erro ao buscar /feedback/conversa. Status: {}. Body: {}", e.status(), e.contentUTF8(), e);
            throw new AgentServiceException("Não foi possível buscar os feedbacks da conversa.", e);
        }
    }

    @Override
    public SessionInfoResponseDto getSessionMessages(String userId, String sessionId) {
        log.info("Buscando mensagens para usuário: {} e sessão: {}", userId, sessionId);
        try {
            return agentClient.getSessionMessages(userId, sessionId);

        } catch (FeignException.NotFound e) { // Trata 404 de forma específica
            log.warn("Sessão não encontrada. Usuário: {}, Sessão: {}", userId, sessionId, e);
            // Você pode lançar uma exceção específica de "não encontrado"
            throw new AgentServiceException("Sessão ou usuário não encontrado.", e);

        } catch (FeignException e) {
            log.error("Erro ao buscar /sessions. Status: {}. Body: {}", e.status(), e.contentUTF8(), e);
            throw new AgentServiceException("Não foi possível buscar o histórico da sessão.", e);
        }
    }

    @Override
    public List<SessionListItemDto> listUserSessions(String userId) {
        log.info("Listando sessões para usuário: {}", userId);
        try {
            return agentClient.listUserSessions(userId);

        } catch (FeignException.NotFound e) {
            log.warn("Usuário não encontrado ao listar sessões: {}", userId, e);
            return Collections.emptyList();

        } catch (FeignException e) {
            log.error("Erro ao listar /list. Status: {}. Body: {}", e.status(), e.contentUTF8(), e);
            return Collections.emptyList();
        }
    }
}
