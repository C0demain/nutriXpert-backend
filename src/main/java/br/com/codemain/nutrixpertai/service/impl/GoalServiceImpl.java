package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.ResponseDTO;
import br.com.codemain.nutrixpertai.dto.Goal.UpdateGoalDTO;
import br.com.codemain.nutrixpertai.entity.Goal;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.enums.GoalType;
import br.com.codemain.nutrixpertai.repository.GoalRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GoalServiceImpl {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseDTO createGoal(CreateGoalDTO dto) {
        User user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Goal goal = new Goal();
        goal.setDescription(dto.description());
        goal.setGoalType(dto.goalType());
        goal.setUser(user);
        goal.setTargetWeight(dto.targetWeight());
        goal.setTargetCalories(dto.targetCalories());
        goal.setFoodRestrictions(dto.foodRestrictions());

        Goal savedGoal = goalRepository.save(goal);

        return mapToResponseDTO(savedGoal);
    }

    public List<ResponseDTO> getGoalsByUser(UUID userId) {
        return goalRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public ResponseDTO getGoalById(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrado"));

        return mapToResponseDTO(goal);
    }

    @Transactional
    public ResponseDTO updateGoal(Long goalId, UpdateGoalDTO dto) {
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

        if (goal.getTargetCalories() != 0){
            formatted.append("Meta de Calorias: ").append(goal.getTargetCalories()).append(" kcal por dia\n");
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

            if (goal.getTargetCalories() != 0){
                formatted.append("Meta de Calorias: ").append(goal.getTargetCalories()).append(" kcal por dia\n");
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
            case WEIGHT_GAIN -> "Ganho de Peso";
            case MUSCLE_GAIN -> "Ganho de Massa Muscular";
            case FAT_LOSS -> "Perda de gordura";
            case MAINTENANCE -> "Manutenção do Peso";
        };
    }

    private ResponseDTO mapToResponseDTO(Goal goal) {
        return new ResponseDTO(
                goal.getId(),
                goal.getUser().getId(),
                goal.getDescription(),
                goal.getGoalType(),
                goal.getTargetWeight(),
                goal.getTargetCalories(),
                goal.getFoodRestrictions()
        );
    }
}