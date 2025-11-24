package br.com.codemain.nutrixpertai.dto.Report;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record WeeklyReportDTO(
        LocalDate weekStart,
        LocalDate weekEnd,
        String userId,
        NutrientSummaryDTO nutrientSummary,
        List<GoalProgressSummaryDTO> goalsProgress,
        MealStatisticsDTO mealStatistics,
        WeekComparisonDTO weekComparison,
        List<DailySummaryDTO> dailySummaries
) {

    public record NutrientSummaryDTO(
            double totalCalories,
            double avgCaloriesPerDay,
            double totalProtein,
            double avgProteinPerDay,
            double totalCarbs,
            double avgCarbsPerDay,
            double totalFat,
            double avgFatPerDay,
            int totalMeals,
            double avgMealsPerDay
    ) {
    }

    public record GoalProgressSummaryDTO(
            Long goalId,
            String goalDescription,
            String goalType,
            double caloriesProgress,
            double proteinProgress,
            double carbsProgress,
            double fatsProgress,
            double weightProgress,
            String status // "on_track", "behind", "ahead"
    ) {
    }

    public record MealStatisticsDTO(
            Map<String, Integer> mealsByType, // BREAKFAST: 7, LUNCH: 7, etc.
            int totalMeals,
            List<String> mostSkippedMealTypes
    ) {
    }

    public record WeekComparisonDTO(
            double caloriesDifference,
            double proteinDifference,
            double carbsDifference,
            double fatsDifference,
            int mealsDifference,
            String trend // "improving", "declining", "stable"
    ) {
    }

    public record DailySummaryDTO(
            LocalDate date,
            int totalMeals,
            double totalCalories,
            double totalProtein,
            double totalCarbs,
            double totalFat,
            boolean goalsMet,
            List<String> mealTypes
    ) {
    }
}