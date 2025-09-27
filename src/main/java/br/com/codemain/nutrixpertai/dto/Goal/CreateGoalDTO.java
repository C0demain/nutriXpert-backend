package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

import java.util.UUID;

public record CreateGoalDTO(
        UUID userId,
        String description,
        GoalType goalType,
        Double targetWeight,
        int targetCalories,
        String foodRestrictions
) {
}
