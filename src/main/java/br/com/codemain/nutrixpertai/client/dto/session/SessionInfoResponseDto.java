package br.com.codemain.nutrixpertai.client.dto.session;

import java.util.List;
import java.util.Map;

public record SessionInfoResponseDto(
        String app_name,
        String user_id,
        String session_id,
        String create_time,
        String update_time,

        Map<String, Object> state,

        List<SessionMessageDto> messages,

        List<Object> events
) {
}
