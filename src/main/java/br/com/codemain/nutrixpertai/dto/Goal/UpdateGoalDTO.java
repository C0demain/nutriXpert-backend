package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

public record UpdateGoalDTO(
        String description,
        GoalType goalType,
        Double targetWeight,
        Integer targetCalories,
        String foodRestrictions
) {
}