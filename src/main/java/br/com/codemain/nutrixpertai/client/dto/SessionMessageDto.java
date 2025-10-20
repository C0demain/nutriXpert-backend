package br.com.codemain.nutrixpertai.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public record SessionMessageDto(
        String id,
        String role,
        String text,
        String author,
        Double timestamp
) {
}
