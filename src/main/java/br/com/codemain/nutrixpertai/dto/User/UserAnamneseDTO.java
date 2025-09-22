package br.com.codemain.nutrixpertai.dto.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserAnamneseDTO {
    
    @NotNull(message = "Altura é obrigatória")
    private String height;

    @NotNull(message = "Peso é obrigatório")
    private String weight;

    @Size(max = 500, message = "Hábitos deve ter no máximo 500 caracteres")
    private String habits;

    @Size(max = 500, message = "Doenças deve ter no máximo 500 caracteres")
    private String illnesses;

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

    public String getHabits() {
        return habits;
    }

    public void setHabits(String habits) {
        this.habits = habits;
    }

    public String getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(String illnesses) {
        this.illnesses = illnesses;
    }
}
