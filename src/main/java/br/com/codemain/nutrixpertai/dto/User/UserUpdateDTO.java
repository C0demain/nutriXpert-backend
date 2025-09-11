package br.com.codemain.nutrixpertai.dto.User;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.codemain.nutrixpertai.enums.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateDTO {

    private String name;

    private String email;

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
