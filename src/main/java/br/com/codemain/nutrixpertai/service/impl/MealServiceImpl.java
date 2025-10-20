package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.enums.MealType;
import br.com.codemain.nutrixpertai.repository.MealRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.mapper.AnamneseMapper;
import br.com.codemain.nutrixpertai.service.mapper.MealMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MealServiceImpl {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    private MealMapper mealMapper;


    public CreateMealDTO createMeal(CreateMealDTO dto, UUID userId) {
        Meal meal = new Meal();
        meal.setDescription(dto.description());
        meal.setMealDateTime(dto.mealDateTime());
        meal.setType(dto.type());
        meal.setUser(userRepository.findById(userId).orElseThrow());

        meal = mealRepository.save(meal);
        return toDto(meal);
    }

    public CreateMealDTO getMealById(Long mealId, UUID userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        if (!meal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        return toDto(meal);
    }

    public List<CreateMealDTO> getMealsByUserId(UUID userId) {
        return mealRepository.findByUserIdOrderByMealDateTimeDesc(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CreateMealDTO> getMealsByUserAndDateRange(UUID userId, LocalDateTime startDate, LocalDateTime endDate) {
        return mealRepository.findByUserIdAndMealDateTimeBetween(userId, startDate, endDate)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<CreateMealDTO> getMealsByUserAndType(UUID userId, MealType type) {
        return mealRepository.findByUserIdAndType(userId, type)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CreateMealDTO updateMeal(Long mealId, CreateMealDTO dto, UUID userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        if (!meal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        meal.setDescription(dto.description());
        meal.setMealDateTime(dto.mealDateTime());
        meal.setType(dto.type());

        meal = mealRepository.save(meal);
        return toDto(meal);
    }

    public void deleteMeal(Long mealId, UUID userId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Refeição não encontrada"));

        if (!meal.getUser().getId().equals(userId)) {
            throw new RuntimeException("Acesso negado");
        }

        mealRepository.delete(meal);
    }

    CreateMealDTO toDto(Meal meal) {
        return mealMapper.toDto(meal);
    }
}
