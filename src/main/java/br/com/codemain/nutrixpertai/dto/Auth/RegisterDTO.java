package br.com.codemain.nutrixpertai.dto.Auth;

import br.com.codemain.nutrixpertai.enums.Role;

public record RegisterDTO(String name, String email, String password, Role role) {

}
