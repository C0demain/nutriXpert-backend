package br.com.codemain.nutrixpertai.service.mapper;

import br.com.codemain.nutrixpertai.dto.Meal.MealResponseDTO;
import br.com.codemain.nutrixpertai.dto.food.CreateFoodDTO;
import br.com.codemain.nutrixpertai.dto.food.FoodResponseDTO;
import br.com.codemain.nutrixpertai.dto.food.UpdateFoodDTO;
import br.com.codemain.nutrixpertai.entity.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    CreateFoodDTO toCreateDTO(Food food);

    @Mapping(target = "mealId", source = "food.meal.id")
    FoodResponseDTO toResponseDTO(Food food);

    UpdateFoodDTO toUpdateDTO(Food food);

    Food toEntity(CreateFoodDTO dto);

    Food toEntity(MealResponseDTO dto);

    Food toEntity(UpdateFoodDTO dto);
}