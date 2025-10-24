package br.com.codemain.nutrixpertai.dto.Meal;

import br.com.codemain.nutrixpertai.dto.food.CreateFoodDTO;
import br.com.codemain.nutrixpertai.enums.MealType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MealResponseDTO(
        Long id,
        String description,
        LocalDateTime mealDateTime,
        MealType type,
        List<CreateFoodDTO> foods,
        UUID userId,
        LocalDateTime createdAt) {
}