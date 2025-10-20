package br.com.codemain.nutrixpertai.client;

import br.com.codemain.nutrixpertai.client.dto.RunAgentRequestDto;
import br.com.codemain.nutrixpertai.client.dto.RunAgentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "agentClient",
        url = "${clients.agent.url}"
)
public interface AgentClient {

    @PostMapping("/run-agent")
    RunAgentResponseDto runAgent(@RequestBody RunAgentRequestDto request);

}
