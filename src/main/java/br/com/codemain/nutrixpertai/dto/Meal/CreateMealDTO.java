package br.com.codemain.nutrixpertai.dto.Meal;

import br.com.codemain.nutrixpertai.enums.MealType;

import java.time.LocalDateTime;

public record CreateMealDTO(
        String description,
        LocalDateTime mealDateTime,
        MealType type) {
}
