package br.com.codemain.nutrixpertai.client.dto.question;

import java.util.List;

public record RunAgentResponseDto(
        String user_id,
        String session_id,
        String answer,
        List<SessionMessageDto> history,
        String contextUsed
) {
}
