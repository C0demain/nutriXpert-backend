
package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.meal.CreateMealDTO;
import br.com.codemain.nutrixpertai.enums.MealType;
import br.com.codemain.nutrixpertai.service.impl.MealServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/meals")
public class MealController {
    private MealServiceImpl mealService;

    @PostMapping
    public ResponseEntity<CreateMealDTO> createMeal(
            @RequestBody CreateMealDTO mealDTO, UUID userId) {
        CreateMealDTO created = mealService.createMeal(mealDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateMealDTO> getMealById(
            @PathVariable Long id, UUID userId) {
        CreateMealDTO meal = mealService.getMealById(id, userId);
        return ResponseEntity.ok(meal);
    }

    @GetMapping
    public ResponseEntity<List<CreateMealDTO>> getAllMeals(UUID userId) {
        List<CreateMealDTO> meals = mealService.getMealsByUserId(userId);
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/filter/date-range")
    public ResponseEntity<List<CreateMealDTO>> getMealsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate, UUID userId) {
        List<CreateMealDTO> meals = mealService.getMealsByUserAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/filter/type")
    public ResponseEntity<List<CreateMealDTO>> getMealsByType(
            @RequestParam MealType type, UUID userId) {
        List<CreateMealDTO> meals = mealService.getMealsByUserAndType(userId, type);
        return ResponseEntity.ok(meals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateMealDTO> updateMeal(
            @PathVariable Long id,
            @RequestBody CreateMealDTO mealDTO, UUID userId) {
        CreateMealDTO updated = mealService.updateMeal(id, mealDTO, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(
            @PathVariable Long id, UUID userId) {
        mealService.deleteMeal(id, userId);
        return ResponseEntity.noContent().build();
    }
}