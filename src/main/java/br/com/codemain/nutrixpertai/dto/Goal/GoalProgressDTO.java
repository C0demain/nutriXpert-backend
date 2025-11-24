package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

import java.time.LocalDateTime;
import java.util.UUID;

public record GoalProgressDTO(
        Long id,
        UUID userId,
        String description,
        GoalType goalType,
        Double weightProgress,
        Double targetWeight,
        Double currentWeight,
        Double caloriesProgressPercentage,
        Integer targetCalories,
        Integer currentCalories,
        Double proteinProgressPercentage,
        Double targetProtein,
        Double currentProtein,
        Double carbsProgressPercentage,
        Double targetCarbs,
        Double currentCarbs,
        Double fatsProgressPercentage,
        Double targetFats,
        Double currentFats,
        LocalDateTime starDate,
        LocalDateTime endDate
) {
}
