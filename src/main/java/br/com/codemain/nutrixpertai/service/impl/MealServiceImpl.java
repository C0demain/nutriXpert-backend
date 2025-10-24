package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.dto.Meal.MealInfoDTO;
import br.com.codemain.nutrixpertai.dto.Meal.MealResponseDTO;
import br.com.codemain.nutrixpertai.entity.Food;
import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.enums.MealType;
import br.com.codemain.nutrixpertai.repository.MealRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.mapper.MealMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealMapper mealMapper;

    public MealResponseDTO createMeal(CreateMealDTO dto, UUID userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));

        Meal meal = new Meal();
        meal.setDescription(dto.description());
        meal.setMealDateTime(dto.mealDateTime());
        meal.setType(dto.type());
        meal.setUser(user);

        meal = mealRepository.save(meal);
        return mealMapper.toResponseDTO(meal);
    }

    public MealResponseDTO getMealById(Long mealId) {
        Meal meal = mealRepository
                .findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada com ID: " + mealId));

        return mealMapper.toResponseDTO(meal);
    }

    public List<MealResponseDTO> getMealsByUserId(UUID userId) {
        return mealRepository.findByUserIdOrderByMealDateTimeDesc(userId)
                .stream()
                .map(meal -> mealMapper.toResponseDTO(meal))
                .collect(Collectors.toList());
    }

    public List<MealResponseDTO> getMealsByUserAndDateRange(UUID userId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }


        return mealRepository.findByUserIdAndMealDateTimeBetween(userId, startDateTime, endDateTime)
                .stream()
                .map(meal -> mealMapper.toResponseDTO(meal))
                .collect(Collectors.toList());
    }

    public List<MealResponseDTO> getMealsByUserAndType(UUID userId, MealType type) {
        try {
            List<Meal> meals = mealRepository.findByUserIdAndType(userId, type);
            var mealsDTO = meals.stream()
                    .map(meal -> mealMapper.toResponseDTO(meal)).toList();
            System.out.println(mealsDTO);
            return mealsDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    public MealInfoDTO getMealInfo(Long mealId) {
        try {
            Meal meal = mealRepository.findById(mealId)
                    .orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada com ID: " + mealId));

            double totalCalories = 0.0;
            double totalProtein = 0.0;
            double totalCarbohydrates = 0.0;
            double totalFat = 0.0;
            int totalFoods = 0;

            for (Food food : meal.getFoods()) {
                totalCalories += food.getCalories();
                totalProtein += food.getProtein();
                totalCarbohydrates += food.getCarbohydrates();
                totalFat += food.getFat();
                totalFoods++;
            }

            return new MealInfoDTO(
                    meal.getId(),
                    meal.getDescription(),
                    meal.getType() != null ? meal.getType().toString() : null,
                    totalFoods,
                    totalCalories,
                    totalProtein,
                    totalCarbohydrates,
                    totalFat
            );

        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao calcular informações nutricionais", e) {
            };
        }
    }

    public MealResponseDTO updateMeal(Long mealId, CreateMealDTO dto) {
        Meal meal = mealRepository.findById(mealId).orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada"));

        meal.setDescription(dto.description());
        meal.setMealDateTime(dto.mealDateTime());
        meal.setType(dto.type());

        meal = mealRepository.save(meal);
        return mealMapper.toResponseDTO(meal);
    }

    public void deleteMeal(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada com ID: " + mealId));

        mealRepository.delete(meal);
    }
}
