package br.com.codemain.nutrixpertai.dto.Auth;

import br.com.codemain.nutrixpertai.enums.Role;

public record LoginResponseDTO(String id, String token, Role role) {
}
