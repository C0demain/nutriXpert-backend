package br.com.codemain.nutrixpertai.client;

import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionInfoResponseDto;
import br.com.codemain.nutrixpertai.client.dto.session.SessionListItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "agentClient",
        url = "${clients.agent.url}"
)
public interface AgentClient {

    @PostMapping("/run-agent")
    RunAgentResponseDto runAgent(@RequestBody RunAgentRequestDto request);

    @PostMapping("/feedback")
    FeedbackResponseDto createFeedback(@RequestBody FeedbackRequestDto feedbackRequest);

    @GetMapping("/feedback/conversa/{user_id}/{session_id}")
    List<FeedbackResponseDto> getFeedbacksByConversation(
            @PathVariable("user_id") String userId,
            @PathVariable("session_id") String sessionId
    );

    @GetMapping("/feedback/conversa/{user_id}/{session_id}/{message_id}")
    FeedbackResponseDto getFeedbackByConversationMessage(
            @PathVariable("user_id") String userId,
            @PathVariable("session_id") String sessionId,
            @PathVariable("message_id") String messageId
    );

    @GetMapping("/sessions/{user_id}/{session_id}")
    SessionInfoResponseDto getSessionMessages(
            @PathVariable("user_id") String userId,
            @PathVariable("session_id") String sessionId
    );

    @GetMapping("/{user_id}/list")
    List<SessionListItemDto> listUserSessions(@PathVariable("user_id") String userId);

}
