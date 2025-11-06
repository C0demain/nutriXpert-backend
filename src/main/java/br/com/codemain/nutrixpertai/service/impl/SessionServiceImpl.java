package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Session.MessageDTO;
import br.com.codemain.nutrixpertai.dto.Session.SessionStateDTO;
import br.com.codemain.nutrixpertai.dto.Session.SessionWithMessagesDTO;
import br.com.codemain.nutrixpertai.entity.Session;
import br.com.codemain.nutrixpertai.repository.SessionRepository;
import br.com.codemain.nutrixpertai.service.ISessionService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements ISessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Busca apenas as mensagens de uma sessão específica
     */
    public List<MessageDTO> getMessagesFromSession(String appName, String userId, String sessionId) {
        try {
            String messagesJson = sessionRepository.getMessagesFromState(appName, userId, sessionId);
            if (messagesJson != null) {
                return objectMapper.readValue(messagesJson, new TypeReference<List<MessageDTO>>() {
                });
            }
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar mensagens da sessão: " + e.getMessage(), e);
        }
    }

    /**
     * Busca o estado completo de uma sessão (answer + messages)
     */
    public SessionStateDTO getSessionState(String appName, String userId, String sessionId) {
        try {
            Optional<Session> session = sessionRepository.findByAppNameAndUserIdAndId(appName, userId, sessionId);
            if (session.isPresent()) {
                String stateJson = session.get().getState();
                return objectMapper.readValue(stateJson, SessionStateDTO.class);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar estado da sessão: " + e.getMessage(), e);
        }
    }

    /**
     * Busca todas as mensagens de um usuário em todas as suas sessões
     */
    public List<SessionWithMessagesDTO> getAllUserSessions(String appName, String userId) {
        try {
            List<String> sessionsJson = sessionRepository.getSessionsWithMessages(appName, userId);
            return sessionsJson.stream()
                    .map(json -> {
                        try {
                            return objectMapper.readValue(json, SessionWithMessagesDTO.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Erro ao processar sessão: " + e.getMessage(), e);
                        }
                    })
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar sessões do usuário: " + e.getMessage(), e);
        }
    }

    /**
     * Busca a última mensagem de uma sessão
     */
    public MessageDTO getLastMessage(String appName, String userId, String sessionId) {
        try {
            String messageJson = sessionRepository.getLastMessage(appName, userId, sessionId);
            if (messageJson != null) {
                return objectMapper.readValue(messageJson, MessageDTO.class);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar última mensagem: " + e.getMessage(), e);
        }
    }
}