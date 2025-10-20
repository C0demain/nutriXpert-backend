package br.com.codemain.nutrixpertai.service.mapper;


import br.com.codemain.nutrixpertai.dto.meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.entity.Meal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealMapper {
    CreateMealDTO toDto(Meal meal);

    Meal toEntity(CreateMealDTO dto);
}
