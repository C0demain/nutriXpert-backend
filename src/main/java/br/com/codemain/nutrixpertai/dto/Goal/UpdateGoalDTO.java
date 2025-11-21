package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

public record UpdateGoalDTO(
        String description,
        GoalType goalType,
        Double targetWeight,
        Integer targetCalories,
        Integer currentCalories,
        Double targetProtein,
        Double currentProtein,
        Double targetCarbs,
        Double currentCarbs,
        Double targetFats,
        Double currentFats,
        String foodRestrictions
) {
}