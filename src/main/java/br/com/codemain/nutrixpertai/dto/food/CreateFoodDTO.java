package br.com.codemain.nutrixpertai.dto.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateFoodDTO(
        @NotNull(message = "ID da refeição é obrigatório")
        Long mealId,

        @NotBlank(message = "Nome do alimento é obrigatório")
        String foodName,

        @NotNull(message = "Calorias são obrigatórias")
        @Positive(message = "Calorias devem ser positivas")
        Double calories,

        @NotNull(message = "Proteínas são obrigatórias")
        @Positive(message = "Proteínas devem ser positivas")
        Double protein,

        @NotNull(message = "Carboidratos são obrigatórios")
        @Positive(message = "Carboidratos devem ser positivos")
        Double carbohydrates,

        @NotNull(message = "Gorduras são obrigatórias")
        @Positive(message = "Gorduras devem ser positivas")
        Double fat
) {
}
