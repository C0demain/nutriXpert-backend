package br.com.codemain.nutrixpertai.client.dto.session;

public record SessionMessageDto(
        String id,
        Double timestamp,
        String author,
        String role,
        String text
) {
}
