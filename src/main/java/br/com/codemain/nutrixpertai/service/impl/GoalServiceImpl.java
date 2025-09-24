package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.ResponseDTO;
import br.com.codemain.nutrixpertai.dto.Goal.UpdateGoalDTO;
import br.com.codemain.nutrixpertai.entity.Goal;
import br.com.codemain.nutrixpertai.entity.User;
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