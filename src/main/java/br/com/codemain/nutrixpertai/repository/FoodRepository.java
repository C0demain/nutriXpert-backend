package br.com.codemain.nutrixpertai.repository;

import br.com.codemain.nutrixpertai.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByMealId(Long mealId);

    List<Food> findByMealIdOrderByFoodNameAsc(Long mealId);

    boolean existsByIdAndMealId(Long id, Long mealId);
}
