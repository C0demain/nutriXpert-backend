package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

import java.util.UUID;

public record GoalResponseDTO(
        Long id,
        UUID userId,
        String description,
        GoalType goalType,
        Double targetWeight,
        int targetCalories,
        int currentCalories,
        Double targetProtein,
        Double currentProtein,
        Double targetCarbs,
        Double currentCarbs,
        Double targetFats,
        Double currentFats,
        String foodRestrictions
) {
}