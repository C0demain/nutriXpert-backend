package br.com.codemain.nutrixpertai.dto.User;

import br.com.codemain.nutrixpertai.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Email(message = "E-mail deve ser válido")
    private String email;

    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;

    private Role role;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
