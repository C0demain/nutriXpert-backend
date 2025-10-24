package br.com.codemain.nutrixpertai.dto.Meal;

public record MealInfoDTO(
        Long mealId,
        String mealDescription,
        String mealType,
        Integer totalFoods,
        Double totalCalories,
        Double totalProtein,
        Double totalCarbohydrates,
        Double totalFat
) {
}