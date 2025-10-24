package br.com.codemain.nutrixpertai.service.mapper;

import br.com.codemain.nutrixpertai.dto.Meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.dto.Meal.MealResponseDTO;
import br.com.codemain.nutrixpertai.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealMapper {
    CreateMealDTO toCreateDTO(Meal meal);

    @Mapping(target = "userId", source = "meal.user.id")
    MealResponseDTO toResponseDTO(Meal meal);

    Meal toEntity(CreateMealDTO dto);

    Meal toEntity(MealResponseDTO dto);
}