package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UpdateGoalDTO(
        String description,
        GoalType goalType,
        Double targetWeight,
        Integer targetCalories,
        Double targetProtein,
        Double targetCarbs,
        Double targetFats,
        String foodRestrictions,
        LocalDate startDate,
        LocalDate endDate
) {
}