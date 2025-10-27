package br.com.codemain.nutrixpertai.dto.User;

import br.com.codemain.nutrixpertai.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String name,

        @Email(message = "E-mail deve ser válido")
        String email,

        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
        String password,

        Role role
) {

}
