package br.com.codemain.nutrixpertai.dto.food;

public record FoodDTO(
        Long id,
        String foodName,
        Double calories,
        Long mealId,
        Double protein,
        Double carbohydrates,
        Double fat) {
}
