package br.com.codemain.nutrixpertai.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public record RunAgentRequestDto(
        String user_id,
        String session_id,
        String question
) {
}
