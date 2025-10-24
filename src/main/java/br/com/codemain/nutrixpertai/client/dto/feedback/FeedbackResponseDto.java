package br.com.codemain.nutrixpertai.client.dto.feedback;

public record FeedbackResponseDto(
        int id,
        String message_id,
        String user_id,
        Integer nota,
        Boolean atendeu_expectativas,
        String comentario
) {
}
