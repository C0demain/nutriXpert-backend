package br.com.codemain.nutrixpertai.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public record RunAgentResponseDto(
        String user_id,
        String session_id,
        String answer,
        List<SessionMessageDto> history,
        String contextUsed
) {
}
