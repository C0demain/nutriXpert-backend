package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.GoalProgressDTO;
import br.com.codemain.nutrixpertai.dto.Goal.GoalResponseDTO;
import br.com.codemain.nutrixpertai.dto.Goal.UpdateGoalDTO;
import br.com.codemain.nutrixpertai.entity.Food;
import br.com.codemain.nutrixpertai.entity.Goal;
import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.enums.GoalType;
import br.com.codemain.nutrixpertai.repository.GoalRepository;
import br.com.codemain.nutrixpertai.repository.MealRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.utils.helper.NutrientSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class GoalServiceImpl {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealRepository mealRepository;

    @Transactional
    public GoalResponseDTO createGoal(CreateGoalDTO dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Goal goal = new Goal();
        goal.setDescription(dto.description());
        goal.setGoalType(dto.goalType());
        goal.setUser(user);
        goal.setTargetWeight(dto.targetWeight());
        goal.setTargetCalories(dto.targetCalories());
        goal.setTargetProtein(dto.targetProtein());
        goal.setTargetCarbs(dto.targetCarbs());
        goal.setTargetFats(dto.targetFats());
        goal.setFoodRestrictions(dto.foodRestrictions());
        goal.setStartDate(dto.startDate().atStartOfDay());
        goal.setEndDate(dto.endDate().atTime(23,59,59));

        System.out.println("Informações atribuidas");

        try {
            Goal savedGoal = goalRepository.save(goal);
            System.out.println("Objetivo criado!");
            return mapToResponseDTO(savedGoal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<GoalResponseDTO> getGoalsByUser(UUID userId) {
        return goalRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public GoalResponseDTO getGoalById(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrado"));

        return mapToResponseDTO(goal);
    }

    @Transactional
    public GoalResponseDTO updateGoal(Long goalId, UpdateGoalDTO dto) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrado"));

        if (dto.description() != null) {
            goal.setDescription(dto.description());
        }
        if (dto.goalType() != null) {
            goal.setGoalType(dto.goalType());
        }
        if (dto.targetWeight() != null) {
            goal.setTargetWeight(dto.targetWeight());
        }
        if (dto.targetCalories() != null) {
            goal.setTargetCalories(dto.targetCalories());
        }
        if (dto.targetProtein() != null) {
            goal.setTargetProtein(dto.targetProtein());
        }
        if (dto.targetCarbs() != null) {
            goal.setTargetCarbs(dto.targetCarbs());
        }
        if (dto.targetFats() != null) {
            goal.setTargetFats(dto.targetFats());
        }
        if (dto.foodRestrictions() != null) {
            goal.setFoodRestrictions(dto.foodRestrictions());
        }

        Goal updatedGoal = goalRepository.save(goal);
        return mapToResponseDTO(updatedGoal);
    }

    @Transactional
    public void deleteGoal(Long goalId) {
        if (!goalRepository.existsById(goalId)) {
            throw new RuntimeException("Objetivo não encontrado");
        }
        goalRepository.deleteById(goalId);
    }

    public GoalProgressDTO getGoalProgress(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrado"));

        User user = goal.getUser();
        var summary = getNutrientSummary(user.getId(), goal);

        return new GoalProgressDTO(
                goal.getId(),
                goal.getUser().getId(),
                goal.getDescription(),
                goal.getGoalType(),
                calculateWeightProgress(user.getWeight(), goal.getTargetWeight(), goal.getGoalType()),
                goal.getTargetWeight(),
                user.getWeight(),
                calculateProgress(summary.getCalories(), goal.getTargetCalories()),
                goal.getTargetCalories(),
                summary.getCalories(),
                calculateProgress(summary.getProteins(), goal.getTargetProtein()),
                goal.getTargetProtein(),
                summary.getProteins(),
                calculateProgress(summary.getCarbs(), goal.getTargetCarbs()),
                goal.getTargetCarbs(),
                summary.getCarbs(),
                calculateProgress(summary.getFats(), goal.getTargetFats()),
                goal.getTargetFats(),
                summary.getFats(),
                goal.getStartDate(),
                goal.getEndDate()
        );
    }

    public List<GoalProgressDTO> getUserGoalProgress(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        return user.getGoals().stream().map(goal -> {
            var summary = getNutrientSummary(user.getId(), goal);

            return new GoalProgressDTO(
                    goal.getId(),
                    goal.getUser().getId(),
                    goal.getDescription(),
                    goal.getGoalType(),
                    calculateWeightProgress(user.getWeight(), goal.getTargetWeight(), goal.getGoalType()),
                    goal.getTargetWeight(),
                    user.getWeight(),
                    calculateProgress(summary.getCalories(), goal.getTargetCalories()),
                    goal.getTargetCalories(),
                    summary.getCalories(),
                    calculateProgress(summary.getProteins(), goal.getTargetProtein()),
                    goal.getTargetProtein(),
                    summary.getProteins(),
                    calculateProgress(summary.getCarbs(), goal.getTargetCarbs()),
                    goal.getTargetCarbs(),
                    summary.getCarbs(),
                    calculateProgress(summary.getFats(), goal.getTargetFats()),
                    goal.getTargetFats(),
                    summary.getFats(),
                    goal.getStartDate(),
                    goal.getEndDate()
            );
        }).toList();
    }

    private Double calculateWeightProgress(Double currentWeight, Double targetWeight, GoalType goalType) {
        if (targetWeight == null || currentWeight == null) {
            return 0.0;
        }
        return Math.abs(currentWeight - targetWeight);
    }

    private Double calculateProgress(Number current, Number target) {
        if (target == null || target.doubleValue() == 0) {
            return 0.0;
        }
        if (current == null) {
            return 0.0;
        }
        return (current.doubleValue() / target.doubleValue()) * 100;
    }

    public String getGoalFormatted(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrado"));

        StringBuilder formatted = new StringBuilder();
        formatted.append("OBJETIVO NUTRICIONAL\n\n");

        formatted.append("Tipo: ").append(formatGoalType(goal.getGoalType())).append("\n");

        if (goal.getDescription() != null && !goal.getDescription().trim().isEmpty()) {
            formatted.append("Descricao: ").append(goal.getDescription()).append("\n");
        }

        if (goal.getTargetWeight() != null) {
            formatted.append("Meta de Peso: ").append(goal.getTargetWeight()).append(" kg\n");
        }

        if (goal.getTargetCalories() != 0) {
            formatted.append("Meta de Calorias: ").append(goal.getTargetCalories()).append(" kcal por dia\n");
        }

        if (goal.getTargetProtein() != null) {
            formatted.append("Meta de Proteinas: ").append(goal.getTargetProtein()).append(" g\n");
        }

        if (goal.getTargetCarbs() != null) {
            formatted.append("Meta de Carboidratos: ").append(goal.getTargetCarbs()).append(" g\n");
        }

        if (goal.getTargetFats() != null) {
            formatted.append("Meta de Gorduras: ").append(goal.getTargetFats()).append(" g\n");
        }

        if (goal.getFoodRestrictions() != null && !goal.getFoodRestrictions().trim().isEmpty()) {
            formatted.append("Restricoes Alimentares: ").append(goal.getFoodRestrictions()).append("\n");
        }

        return formatted.toString();
    }

    public String getUserGoalsFormatted(UUID userId) {
        List<Goal> goals = goalRepository.findByUserId(userId);

        if (goals.isEmpty()) {
            return "NENHUM OBJETIVO ENCONTRADO\n\nO usuario nao possui objetivos nutricionais cadastrados.";
        }

        StringBuilder formatted = new StringBuilder();
        formatted.append("OBJETIVOS NUTRICIONAIS DO USUARIO\n");
        formatted.append("Usuario ID: ").append(userId).append("\n");
        formatted.append("Total de objetivos: ").append(goals.size()).append("\n\n");

        for (int i = 0; i < goals.size(); i++) {
            Goal goal = goals.get(i);
            formatted.append("OBJETIVO ").append(i + 1).append("\n");
            formatted.append("ID: ").append(goal.getId()).append("\n");
            formatted.append("Tipo: ").append(formatGoalType(goal.getGoalType())).append("\n");

            if (goal.getDescription() != null && !goal.getDescription().trim().isEmpty()) {
                formatted.append("Descricao: ").append(goal.getDescription()).append("\n");
            }

            if (goal.getTargetWeight() != null) {
                formatted.append("Meta de Peso: ").append(goal.getTargetWeight()).append(" kg\n");
            }

            if (goal.getTargetCalories() != 0) {
                formatted.append("Meta de Calorias: ").append(goal.getTargetCalories()).append(" kcal por dia\n");
            }

            if (goal.getTargetProtein() != null) {
                formatted.append("Meta de Proteinas: ").append(goal.getTargetProtein()).append(" g\n");
            }

            if (goal.getTargetCarbs() != null) {
                formatted.append("Meta de Carboidratos: ").append(goal.getTargetCarbs()).append(" g\n");
            }

            if (goal.getTargetFats() != null) {
                formatted.append("Meta de Gorduras: ").append(goal.getTargetFats()).append(" g\n");
            }

            if (goal.getFoodRestrictions() != null && !goal.getFoodRestrictions().trim().isEmpty()) {
                formatted.append("Restricoes: ").append(goal.getFoodRestrictions()).append("\n");
            }

            if (i < goals.size() - 1) {
                formatted.append("\n");
            }
        }

        return formatted.toString();
    }

    private String formatGoalType(GoalType goalType) {
        return switch (goalType) {
            case WEIGHT_LOSS -> "Perda de Peso";
            case DIABETES_CONTROL -> "Controle de diabetes";
            case NUTRITIONAL_REEDUCATION -> "Reeducação alimentar";
            case PHYSICAL_MENTAL_PERFORMANCE -> "Performance física e mental";
            case WEIGHT_GAIN -> "Ganho de Peso";
            case MUSCLE_GAIN -> "Ganho de Massa Muscular";
            case FAT_LOSS -> "Perda de gordura";
            case MAINTENANCE -> "Manutenção do Peso";
        };
    }

    private GoalResponseDTO mapToResponseDTO(Goal goal) {
        return new GoalResponseDTO(
                goal.getId(),
                goal.getUser().getId(),
                goal.getDescription(),
                goal.getGoalType(),
                goal.getTargetWeight(),
                goal.getTargetCalories(),
                goal.getTargetProtein(),
                goal.getTargetCarbs(),
                goal.getTargetFats(),
                goal.getFoodRestrictions(),
                goal.getStartDate(),
                goal.getEndDate()
        );
    }

    private NutrientSummary getNutrientSummary(UUID userId, Goal goal) {
        List<Meal> meals = mealRepository.findByUserIdAndMealDateTimeBetween(userId, goal.getStartDate(), goal.getEndDate());
        int currentCalories = meals.stream()
                .flatMap(meal -> meal.getFoods().stream())
                .mapToInt(Food::getCalories)
                .sum();
        double currentProtein = meals.stream()
                .flatMap(meal -> meal.getFoods().stream())
                .mapToDouble(Food::getProtein)
                .sum();
        double currentCarbs = meals.stream()
                .flatMap(meal -> meal.getFoods().stream())
                .mapToDouble(Food::getCarbohydrates)
                .sum();
        double currentFats = meals.stream()
                .flatMap(meal -> meal.getFoods().stream())
                .mapToDouble(Food::getFat)
                .sum();
        return new NutrientSummary(currentCalories, currentProtein, currentCarbs, currentFats);
    }
}
