package br.com.codemain.nutrixpertai.service;

import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionInfoResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionListItemDto;

import java.util.List;

public interface IAgentService {

    public RunAgentResponseDto executeAgent(RunAgentRequestDto requestDto);

    public FeedbackResponseDto executeFeedback(FeedbackRequestDto requestDto);

    public List<FeedbackResponseDto> getFeedbacksByConversation(String userId, String sessionId);

    public FeedbackResponseDto getFeedbackByConversationMessage(String userId, String sessionId, String messageId);

    public SessionInfoResponseDto getSessionMessages(String userId, String sessionId);

    public List<SessionListItemDto> listUserSessions(String userId);
}
