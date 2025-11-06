package br.com.codemain.nutrixpertai.dto.Session;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Mensagem individual da conversa")
public class MessageDTO {
    @Schema(description = "ID único da mensagem", example = "1bc81e14-b52d-417e-bea6-5b92ec7fe0a7")
    private String id;

    @Schema(description = "Papel na conversa", allowableValues = {"user", "assistant"}, example = "user")
    private String role;

    @Schema(description = "Conteúdo textual da mensagem", example = "Qual a melhor dieta para mim?")
    private String text;

    @Schema(description = "Autor da mensagem", allowableValues = {"user", "assistant"}, example = "user")
    private String author;

    @Schema(description = "Timestamp Unix da mensagem", example = "1759001834.611079")
    private Double timestamp;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
