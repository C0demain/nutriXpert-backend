package br.com.codemain.nutrixpertai.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.codemain.nutrixpertai.dto.Report.WeeklyReportDTO;
import br.com.codemain.nutrixpertai.entity.Food;
import br.com.codemain.nutrixpertai.entity.Goal;
import br.com.codemain.nutrixpertai.entity.Meal;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.GoalRepository;
import br.com.codemain.nutrixpertai.repository.MealRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;

@Service
public class WeeklyReportServiceImpl {

    private final MealRepository mealRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public WeeklyReportServiceImpl(MealRepository mealRepository,
                                   GoalRepository goalRepository,
                                   UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public WeeklyReportDTO generateWeeklyReport(UUID userId, LocalDate weekStart) {
        LocalDate adjustedStart = weekStart.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = adjustedStart.plusDays(6);

        // ✅ Verificação suave - não lança exceção se usuário não existir
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return createEmptyReport(userId, adjustedStart, weekEnd);
        }
        User user = userOpt.get();

        // Busca dados da semana
        LocalDateTime startDateTime = adjustedStart.atStartOfDay();
        LocalDateTime endDateTime = weekEnd.atTime(23, 59, 59);
        List<Meal> weekMeals = mealRepository.findByUserIdAndMealDateTimeBetween(userId, startDateTime, endDateTime);

        // Busca dados da semana anterior para comparação
        LocalDate prevWeekStart = adjustedStart.minusWeeks(1);
        LocalDate prevWeekEnd = prevWeekStart.plusDays(6);
        List<Meal> prevWeekMeals = mealRepository.findByUserIdAndMealDateTimeBetween(
                userId,
                prevWeekStart.atStartOfDay(),
                prevWeekEnd.atTime(23, 59, 59));

        // Busca objetivos ativos
        List<Goal> activeGoals = goalRepository.findByUserId(userId).stream()
                .filter(goal -> goal.getStartDate() != null && goal.getEndDate() != null &&
                               !goal.getStartDate().isAfter(endDateTime) && !goal.getEndDate().isBefore(startDateTime))
                .collect(Collectors.toList());

        WeeklyReportDTO.NutrientSummaryDTO nutrientSummary =
                generateNutrientSummary(weekMeals);

        List<WeeklyReportDTO.GoalProgressSummaryDTO> goalsProgress =
                generateGoalsProgress(activeGoals, weekMeals, user);

        WeeklyReportDTO.MealStatisticsDTO mealStatistics =
                generateMealStatistics(weekMeals);

        WeeklyReportDTO.WeekComparisonDTO weekComparison =
                generateWeekComparison(weekMeals, prevWeekMeals);

        List<WeeklyReportDTO.DailySummaryDTO> dailySummaries =
                generateDailySummaries(weekMeals, adjustedStart, weekEnd, activeGoals);

        return new WeeklyReportDTO(
                adjustedStart,
                weekEnd,
                userId.toString(),
                nutrientSummary,
                goalsProgress,
                mealStatistics,
                weekComparison,
                dailySummaries
        );
    }

    private WeeklyReportDTO.NutrientSummaryDTO generateNutrientSummary(List<Meal> meals) {
        // ✅ Proteção contra lista vazia
        if (meals == null || meals.isEmpty()) {
            return new WeeklyReportDTO.NutrientSummaryDTO(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;
        int totalMeals = meals.size();

        for (Meal meal : meals) {
            if (meal.getFoods() != null) {
                for (Food food : meal.getFoods()) {
                    totalCalories += food.getCalories() != null ? food.getCalories() : 0;
                    totalProtein += food.getProtein() != null ? food.getProtein() : 0;
                    totalCarbs += food.getCarbohydrates() != null ? food.getCarbohydrates() : 0;
                    totalFat += food.getFat() != null ? food.getFat() : 0;
                }
            }
        }

        return new WeeklyReportDTO.NutrientSummaryDTO(
                totalCalories,
                totalCalories / 7.0,
                totalProtein,
                totalProtein / 7.0,
                totalCarbs,
                totalCarbs / 7.0,
                totalFat,
                totalFat / 7.0,
                totalMeals,
                totalMeals / 7.0
        );
    }

    private List<WeeklyReportDTO.GoalProgressSummaryDTO> generateGoalsProgress(
            List<Goal> goals, List<Meal> meals, User user) {

        // ✅ Proteção contra metas vazias
        if (goals == null || goals.isEmpty()) {
            return new ArrayList<>();
        }

        List<WeeklyReportDTO.GoalProgressSummaryDTO> progressList = new ArrayList<>();

        for (Goal goal : goals) {
            double totalCalories = 0;
            double totalProtein = 0;
            double totalCarbs = 0;
            double totalFat = 0;

            if (meals != null) {
                for (Meal meal : meals) {
                    if (meal.getFoods() != null) {
                        for (Food food : meal.getFoods()) {
                            totalCalories += food.getCalories() != null ? food.getCalories() : 0;
                            totalProtein += food.getProtein() != null ? food.getProtein() : 0;
                            totalCarbs += food.getCarbohydrates() != null ? food.getCarbohydrates() : 0;
                            totalFat += food.getFat() != null ? food.getFat() : 0;
                        }
                    }
                }
            }

            // Calcula progresso médio diário
            double avgDailyCalories = totalCalories / 7.0;
            double avgDailyProtein = totalProtein / 7.0;
            double avgDailyCarbs = totalCarbs / 7.0;
            double avgDailyFat = totalFat / 7.0;

            // Calcula percentuais de progresso
            double caloriesProgress = goal.getTargetCalories() > 0
                    ? (avgDailyCalories / goal.getTargetCalories()) * 100 : 0;
            double proteinProgress = goal.getTargetProtein() != null && goal.getTargetProtein() > 0
                    ? (avgDailyProtein / goal.getTargetProtein()) * 100 : 0;
            double carbsProgress = goal.getTargetCarbs() != null && goal.getTargetCarbs() > 0
                    ? (avgDailyCarbs / goal.getTargetCarbs()) * 100 : 0;
            double fatsProgress = goal.getTargetFats() != null && goal.getTargetFats() > 0
                    ? (avgDailyFat / goal.getTargetFats()) * 100 : 0;

            // Calcula progresso de peso
            double weightProgress = 0;
            if (goal.getTargetWeight() != null && user.getWeight() != null) {
                weightProgress = Math.abs(user.getWeight() - goal.getTargetWeight());
            }

            // Determina status
            double avgProgress = (caloriesProgress + proteinProgress + carbsProgress + fatsProgress) / 4.0;
            String status = avgProgress >= 90 && avgProgress <= 110 ? "on_track"
                    : avgProgress < 90 ? "behind" : "ahead";

            progressList.add(new WeeklyReportDTO.GoalProgressSummaryDTO(
                    goal.getId(),
                    goal.getDescription(),
                    goal.getGoalType() != null ? goal.getGoalType().toString() : "UNKNOWN",
                    caloriesProgress,
                    proteinProgress,
                    carbsProgress,
                    fatsProgress,
                    weightProgress,
                    status
            ));
        }

        return progressList;
    }

    private WeeklyReportDTO.MealStatisticsDTO generateMealStatistics(List<Meal> meals) {
        // ✅ Proteção contra lista vazia
        if (meals == null || meals.isEmpty()) {
            return new WeeklyReportDTO.MealStatisticsDTO(new HashMap<>(), 0, new ArrayList<>());
        }

        Map<String, Integer> mealsByType = new HashMap<>();
        Map<LocalDate, Set<String>> dailyMealTypes = new HashMap<>();

        for (Meal meal : meals) {
            if (meal.getType() == null) continue;
            String type = meal.getType().toString();
            mealsByType.put(type, mealsByType.getOrDefault(type, 0) + 1);

            if (meal.getMealDateTime() != null) {
                LocalDate date = meal.getMealDateTime().toLocalDate();
                dailyMealTypes.computeIfAbsent(date, k -> new HashSet<>()).add(type);
            }
        }

        List<String> mostSkippedMealTypes = mealsByType.entrySet().stream()
                .filter(e -> e.getValue() < 7)
                .sorted(Map.Entry.comparingByValue())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return new WeeklyReportDTO.MealStatisticsDTO(
                mealsByType,
                meals.size(),
                mostSkippedMealTypes
        );
    }

    private WeeklyReportDTO.WeekComparisonDTO generateWeekComparison(List<Meal> currentWeek, List<Meal> previousWeek) {
        // ✅ Proteção contra listas vazias
        if (currentWeek == null) currentWeek = new ArrayList<>();
        if (previousWeek == null) previousWeek = new ArrayList<>();

        double currentCalories = calculateTotalNutrient(currentWeek, "calories");
        double previousCalories = calculateTotalNutrient(previousWeek, "calories");

        double currentProtein = calculateTotalNutrient(currentWeek, "protein");
        double previousProtein = calculateTotalNutrient(previousWeek, "protein");

        double currentCarbs = calculateTotalNutrient(currentWeek, "carbs");
        double previousCarbs = calculateTotalNutrient(previousWeek, "carbs");

        double currentFat = calculateTotalNutrient(currentWeek, "fat");
        double previousFat = calculateTotalNutrient(previousWeek, "fat");

        int mealsDifference = currentWeek.size() - previousWeek.size();

        // Determina tendência
        double avgDifference = (
                (currentCalories - previousCalories) + (currentProtein - previousProtein) + (currentCarbs - previousCarbs) + (currentFat - previousFat)
        ) / 4.0;

        String trend = Math.abs(avgDifference) < 50 ? "stable"
                : avgDifference > 0 ? "improving" : "declining";

        return new WeeklyReportDTO.WeekComparisonDTO(
                currentCalories - previousCalories,
                currentProtein - previousProtein,
                currentCarbs - previousCarbs,
                currentFat - previousFat,
                mealsDifference,
                trend
        );
    }

    private double calculateTotalNutrient(List<Meal> meals, String nutrient) {
        if (meals == null || meals.isEmpty()) return 0.0;
        
        return meals.stream()
                .filter(meal -> meal.getFoods() != null)
                .flatMap(meal -> meal.getFoods().stream())
                .mapToDouble(food -> switch (nutrient) {
                    case "calories" -> food.getCalories() != null ? food.getCalories() : 0.0;
                    case "protein" -> food.getProtein() != null ? food.getProtein() : 0.0;
                    case "carbs" -> food.getCarbohydrates() != null ? food.getCarbohydrates() : 0.0;
                    case "fat" -> food.getFat() != null ? food.getFat() : 0.0;
                    default -> 0.0;
                })
                .sum();
    }

    private List<WeeklyReportDTO.DailySummaryDTO> generateDailySummaries(
            List<Meal> meals, LocalDate weekStart, LocalDate weekEnd, List<Goal> goals) {

        if (meals == null) meals = new ArrayList<>();
        if (goals == null) goals = new ArrayList<>();

        List<WeeklyReportDTO.DailySummaryDTO> summaries = new ArrayList<>();

        for (LocalDate date = weekStart; !date.isAfter(weekEnd); date = date.plusDays(1)) {
            LocalDate currentDate = date;
            List<Meal> dayMeals = meals.stream()
                    .filter(m -> m.getMealDateTime() != null && m.getMealDateTime().toLocalDate().equals(currentDate))
                    .toList();

            double totalCalories = 0;
            double totalProtein = 0;
            double totalCarbs = 0;
            double totalFat = 0;

            for (Meal meal : dayMeals) {
                if (meal.getFoods() != null) {
                    for (Food food : meal.getFoods()) {
                        totalCalories += food.getCalories() != null ? food.getCalories() : 0;
                        totalProtein += food.getProtein() != null ? food.getProtein() : 0;
                        totalCarbs += food.getCarbohydrates() != null ? food.getCarbohydrates() : 0;
                        totalFat += food.getFat() != null ? food.getFat() : 0;
                    }
                }
            }

            List<String> mealTypes = dayMeals.stream()
                    .filter(m -> m.getType() != null)
                    .map(m -> m.getType().toString())
                    .distinct()
                    .collect(Collectors.toList());

            boolean goalsMet = checkIfGoalsMet(goals, totalCalories, totalProtein, totalCarbs, totalFat);

            summaries.add(new WeeklyReportDTO.DailySummaryDTO(
                    date,
                    dayMeals.size(),
                    totalCalories,
                    totalProtein,
                    totalCarbs,
                    totalFat,
                    goalsMet,
                    mealTypes
            ));
        }

        return summaries;
    }

    private boolean checkIfGoalsMet(List<Goal> goals, double calories,
                                    double protein, double carbs, double fat) {
        if (goals == null || goals.isEmpty()) return false;

        for (Goal goal : goals) {
            boolean caloriesMet = Math.abs(calories - goal.getTargetCalories())
                    <= goal.getTargetCalories() * 0.1;
            boolean proteinMet = goal.getTargetProtein() == null ||
                    Math.abs(protein - goal.getTargetProtein()) <= goal.getTargetProtein() * 0.1;
            boolean carbsMet = goal.getTargetCarbs() == null ||
                    Math.abs(carbs - goal.getTargetCarbs()) <= goal.getTargetCarbs() * 0.1;
            boolean fatsMet = goal.getTargetFats() == null ||
                    Math.abs(fat - goal.getTargetFats()) <= goal.getTargetFats() * 0.1;

            if (caloriesMet && proteinMet && carbsMet && fatsMet) {
                return true;
            }
        }

        return false;
    }

    /**
     * Cria um relatório vazio para usuários sem dados ou não encontrados
     */
    private WeeklyReportDTO createEmptyReport(UUID userId, LocalDate weekStart, LocalDate weekEnd) {
        WeeklyReportDTO.NutrientSummaryDTO emptyNutrients = 
            new WeeklyReportDTO.NutrientSummaryDTO(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        
        WeeklyReportDTO.MealStatisticsDTO emptyStats = 
            new WeeklyReportDTO.MealStatisticsDTO(new HashMap<>(), 0, new ArrayList<>());
        
        WeeklyReportDTO.WeekComparisonDTO emptyComparison = 
            new WeeklyReportDTO.WeekComparisonDTO(0, 0, 0, 0, 0, "stable");
        
        // Gera dias vazios para a semana
        List<WeeklyReportDTO.DailySummaryDTO> dailySummaries = new ArrayList<>();
        for (LocalDate date = weekStart; !date.isAfter(weekEnd); date = date.plusDays(1)) {
            dailySummaries.add(new WeeklyReportDTO.DailySummaryDTO(
                date, 0, 0, 0, 0, 0, false, new ArrayList<>()
            ));
        }
        
        return new WeeklyReportDTO(
            weekStart,
            weekEnd,
            userId.toString(),
            emptyNutrients,
            new ArrayList<>(), // sem metas
            emptyStats,
            emptyComparison,
            dailySummaries
        );
    }

    @Transactional(readOnly = true)
    public WeeklyReportDTO getCurrentWeekReport(UUID userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        return generateWeeklyReport(userId, weekStart);
    }

    @Transactional(readOnly = true)
    public List<WeeklyReportDTO> getMonthlyReports(UUID userId, int year, int month) {
        List<WeeklyReportDTO> reports = new ArrayList<>();
        LocalDate monthStart = LocalDate.of(year, month, 1);
        LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

        LocalDate weekStart = monthStart.with(DayOfWeek.MONDAY);
        if (weekStart.isBefore(monthStart)) {
            weekStart = weekStart.plusWeeks(1);
        }

        while (!weekStart.isAfter(monthEnd)) {
            reports.add(generateWeeklyReport(userId, weekStart));
            weekStart = weekStart.plusWeeks(1);
        }

        return reports;
    }
}