package br.com.codemain.nutrixpertai.client.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequestDto(
        String message_id,
        String user_id,

        @NotNull(message = "O campo 'nota' é obrigatório.")
        @Min(value = 0, message = "A nota deve ser no mínimo 0.")
        @Max(value = 5, message = "A nota deve ser no máximo 5.")
        Integer nota,

        Boolean atendeu_expectativas,
        String comentario
) {
}
