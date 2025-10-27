package br.com.codemain.nutrixpertai.client.dto.question;

public record RunAgentRequestDto(
        String user_id,
        String session_id,
        String question
) {
}
