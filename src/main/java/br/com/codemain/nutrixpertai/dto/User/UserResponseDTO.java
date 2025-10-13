package br.com.codemain.nutrixpertai.dto.User;

import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.enums.Role;

public class UserResponseDTO {

    private UUID id;

    private String name;

    private String email;

    private Role role;

    private String height;

    private String weight;

    private AnamneseResponseDTO anamnese;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public AnamneseResponseDTO getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(AnamneseResponseDTO anamnese) {
        this.anamnese = anamnese;
    }


}
