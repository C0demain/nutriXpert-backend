package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.client.AgentClient;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackRequestDto;
import br.com.codemain.nutrixpertai.client.dto.feedback.FeedbackResponseDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.question.RunAgentResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl {


    private final AgentClient agentClient;

    public AgentServiceImpl(AgentClient agentClient) {
        this.agentClient = agentClient;
    }

    public RunAgentResponseDto executeAgent(RunAgentRequestDto requestDto) {
        return agentClient.runAgent(requestDto);
    }

    public FeedbackResponseDto executeFeedback(FeedbackRequestDto requestDto) {
        return agentClient.createFeedback(requestDto);
    }
}
