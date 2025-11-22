package br.com.codemain.nutrixpertai.dto.Goal;

import br.com.codemain.nutrixpertai.enums.GoalType;

import java.time.LocalDateTime;
import java.util.UUID;

public record GoalResponseDTO(
        Long id,
        UUID userId,
        String description,
        GoalType goalType,
        Double targetWeight,
        int targetCalories,
        Double targetProtein,
        Double targetCarbs,
        Double targetFats,
        String foodRestrictions,
        LocalDateTime starDate,
        LocalDateTime endDate
) {
}