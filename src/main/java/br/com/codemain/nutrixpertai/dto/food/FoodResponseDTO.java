package br.com.codemain.nutrixpertai.dto.food;

public record FoodResponseDTO(
        Long id,
        Long mealId,
        String foodName,
        Double calories,
        Double protein,
        Double carbohydrates,
        Double fat) {
}
