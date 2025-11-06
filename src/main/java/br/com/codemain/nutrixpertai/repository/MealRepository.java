package br.com.codemain.nutrixpertai.repository;

import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdOrderByMealDateTimeDesc(UUID userId);
    List<Meal> findByUserIdAndMealDateTimeBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Meal> findByUserIdAndType(UUID userId, MealType type);
}