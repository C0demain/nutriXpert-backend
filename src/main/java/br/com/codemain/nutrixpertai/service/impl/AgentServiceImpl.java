package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.client.AgentClient;
import br.com.codemain.nutrixpertai.client.dto.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.RunAgentResponseDto;
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
}
