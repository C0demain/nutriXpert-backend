package br.com.codemain.nutrixpertai.client;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "agentClient", url = "${clients.agent.url}")
public interface AgentClient {

}
