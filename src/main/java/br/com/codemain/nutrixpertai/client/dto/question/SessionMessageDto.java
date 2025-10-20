package br.com.codemain.nutrixpertai.client.dto.question;

public record SessionMessageDto(
        String id,
        String role,
        String text,
        String author,
        Double timestamp
) {
}
