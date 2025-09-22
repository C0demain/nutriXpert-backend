package br.com.codemain.nutrixpertai.dto.Auth;

import br.com.codemain.nutrixpertai.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDTO(

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    String name,

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    String email,

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String password,

    @NotNull(message = "Papel (role) é obrigatório")
    Role role     
) {}
