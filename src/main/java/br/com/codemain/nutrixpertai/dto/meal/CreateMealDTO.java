package br.com.codemain.nutrixpertai.dto.meal;

import br.com.codemain.nutrixpertai.dto.food.FoodDTO;
import br.com.codemain.nutrixpertai.enums.MealType;

import java.time.LocalDateTime;
import java.util.List;

public record CreateMealDTO(
        String description,
        LocalDateTime mealDateTime,
        MealType type,
        List<FoodDTO> foods,
        LocalDateTime createdAt) {
}
