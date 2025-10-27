package br.com.codemain.nutrixpertai.service;

import br.com.codemain.nutrixpertai.dto.food.CreateFoodDTO;
import br.com.codemain.nutrixpertai.dto.food.FoodResponseDTO;
import br.com.codemain.nutrixpertai.dto.food.UpdateFoodDTO;

import java.util.List;

public interface IFoodService {
    FoodResponseDTO createFood(CreateFoodDTO dto);

    FoodResponseDTO getFoodById(Long foodId);

    List<FoodResponseDTO> getFoodsByMealId(Long mealId);

    FoodResponseDTO updateFood(Long foodId, UpdateFoodDTO dto);

    void deleteFood(Long foodId);
}
