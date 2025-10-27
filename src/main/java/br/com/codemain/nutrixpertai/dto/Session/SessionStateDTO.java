package br.com.codemain.nutrixpertai.dto.Session;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Estado completo de uma sessão incluindo resposta e mensagens")
public class SessionStateDTO {

    @Schema(description = "Resposta final gerada pelo assistente", example = "Resumo: Para uma dieta saudável...")
    private String answer;

    @Schema(description = "Lista de mensagens da conversa")
    private List<MessageDTO> messages;

    // Getters and Setters
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
