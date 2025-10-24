package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.food.CreateFoodDTO;
import br.com.codemain.nutrixpertai.dto.food.FoodResponseDTO;
import br.com.codemain.nutrixpertai.dto.food.UpdateFoodDTO;
import br.com.codemain.nutrixpertai.entity.Food;
import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.repository.FoodRepository;
import br.com.codemain.nutrixpertai.repository.MealRepository;
import br.com.codemain.nutrixpertai.service.IFoodService;
import br.com.codemain.nutrixpertai.service.mapper.FoodMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements IFoodService {

    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final FoodMapper foodMapper;

    public FoodServiceImpl(FoodRepository foodRepository, MealRepository mealRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.mealRepository = mealRepository;
        this.foodMapper = foodMapper;
    }

    @Transactional
    public FoodResponseDTO createFood(CreateFoodDTO dto) {
        try {
            Meal meal = mealRepository.findById(dto.mealId())
                    .orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada com ID: " + dto.mealId()));

            Food food = foodMapper.toEntity(dto);
            food.setMeal(meal);
            food = foodRepository.save(food);

            return foodMapper.toResponseDTO(food);

        } catch (EntityNotFoundException | AccessDeniedException e) {
            throw e;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao criar alimento", e) {
            };
        }
    }

    @Transactional(readOnly = true)
    public FoodResponseDTO getFoodById(Long foodId) {
        try {
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new EntityNotFoundException("Alimento não encontrado com ID: " + foodId));

            return foodMapper.toResponseDTO(food);

        } catch (EntityNotFoundException | AccessDeniedException e) {
            throw e;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar alimento", e) {
            };
        }
    }

    public List<FoodResponseDTO> getFoodsByMealId(Long mealId) {
        try {
            Meal meal = mealRepository.findById(mealId)
                    .orElseThrow(() -> new EntityNotFoundException("Refeição não encontrada com ID: " + mealId));

            return foodRepository.findByMealIdOrderByFoodNameAsc(mealId)
                    .stream()
                    .map(foodMapper::toResponseDTO)
                    .collect(Collectors.toList());

        } catch (EntityNotFoundException | AccessDeniedException e) {
            throw e;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar alimentos", e) {
            };
        }
    }

    public FoodResponseDTO updateFood(Long foodId, UpdateFoodDTO dto) {
        try {
            var food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new EntityNotFoundException("Alimento não encontrado com ID: " + foodId));
            food.setFoodName(dto.foodName());
            food.setCalories(dto.calories());
            food.setCarbohydrates(dto.carbohydrates());
            food.setFat(dto.fat());
            food.setProtein(dto.protein());
            food.setMeal(food.getMeal());
            food = foodRepository.save(food);

            return foodMapper.toResponseDTO(food);

        } catch (EntityNotFoundException | AccessDeniedException e) {
            throw e;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao atualizar alimento", e) {
            };
        }
    }

    public void deleteFood(Long foodId) {
        try {
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new EntityNotFoundException("Alimento não encontrado com ID: " + foodId));

            foodRepository.delete(food);

        } catch (EntityNotFoundException | AccessDeniedException e) {
            throw e;
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao deletar alimento", e) {
            };
        }
    }
}